package com.prateek.cowinAvailibility.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prateek.cowinAvailibility.configuration.ConfigValueProperties;
import com.prateek.cowinAvailibility.dto.cowinResponse.CowinResponse;
import com.prateek.cowinAvailibility.entity.Alerts;
import com.prateek.cowinAvailibility.repo.AlertRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DistrctwiseDataLoader {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AlertRepo alertRepo;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ConfigValueProperties configValueProperties;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper mapper = new ObjectMapper();

    private List<Alerts> alertList;
    private ValueOperations<String, String> cache;

    private int lastoffset;
    private Date lastRun;

    @PostConstruct
    public void postConstruct() {
        this.cache = this.redisTemplate.opsForValue();
        this.lastoffset = -1;
    }

    @Scheduled(cron = "${app.cronExp}")
    public void loadDistrictContinously() {
        log.info("District: Run Job To populate Cache Started:");
        int offset;
        int j = 0;
        if (this.lastoffset == -1) {
            offset = 0;
        } else {
            offset = lastoffset;
        }
        log.info("District: Last Offset Value= " + lastoffset + " and current offset is " + offset);
        if (null == alertList) {
            log.info("District: Loading Alert , currently null");
            alertList = getListOfDistinctDistrictIdAlerts(alertRepo.findAll());
        }

        int i = offset;
        for (; i < alertList.size(); i++) {
            try {
                Alerts alt = alertList.get(i);
                int key = alt.getDistrictId();
                CowinResponse res = commonService.getData(key, false);

                log.info("District: Ran for Alertwith district : " + alt.getDistrictId());

                if (null == res) {
                    log.warn("District: Max Limit reached. Try Later");
                    break;
                }
                log.info("District: Ran successful for alert " + alt.getId() + " Current count : " + j
                        + " and total count: " + i);
                cache.set(String.valueOf(key), mapper.writeValueAsString(res),
                        configValueProperties.getCacheExpiryInSeconds(), TimeUnit.SECONDS);
                this.lastoffset = i;
                j++;
            } catch (Exception e) {
                log.error(e.getLocalizedMessage(), e);
            }
        }

        if (i >= alertList.size()) {
            this.lastoffset = -1;
            this.alertList = null;
            this.lastRun = null;
        }

        log.info("District: Cached Item count: " + i + " and current run count: " + j);
        log.info("District: Run Job To populate Cache Finished: ");

        if (j > 0) {
            lastRun = new Date();
        } else if (null != lastRun) {
            log.info("Time diff since last run in millis: " + (lastRun.getTime() - new Date().getTime()));
        }

    }

    private List<Alerts> getListOfDistinctDistrictIdAlerts(List<Alerts> alertList) {
        Set<Integer> distinctDistictId = new HashSet<>();
        List<Alerts> alerts = new ArrayList<>();

        for (Alerts alt : alertList) {
            if (!alt.isPinCodeSearch() && !distinctDistictId.contains(alt.getDistrictId())) {
                distinctDistictId.add(alt.getDistrictId());
                alerts.add(alt);
            }
        }
        log.info("District: Filtered request: Input " + alertList.size() + " and returned: " + alerts.size());
        return alerts;
    }

}
