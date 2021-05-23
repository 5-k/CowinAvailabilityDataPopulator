package com.prateek.cowinAvailibility.service;

import java.net.InetAddress;

import javax.annotation.PostConstruct;

import com.microsoft.applicationinsights.TelemetryClient;
import com.prateek.cowinAvailibility.dto.cowinResponse.CowinResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CommonService {

    private static final TelemetryClient telemetryClient = new TelemetryClient();

    @Autowired
    private Environment environment;

    @Autowired
    private ExternalService externalService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void postconstruct() {
        logHostName();
    }

    public CowinResponse getData(Integer districtOrPincode, boolean isPinCodeSearch) {
        CowinResponse res = (isPinCodeSearch) ? checkByPinCode(districtOrPincode)
                : checkAvlByDistrict(districtOrPincode);
        return res;
    }

    public CowinResponse checkAvlByDistrict(int districtId) {
        try {
            return externalService.getData(districtId, false);
        } catch (Exception e) {
            log.error("Exception fetching response by district for district id " + districtId, e);
            return null;
        }

    }

    public CowinResponse checkByPinCode(int pincode) {
        try {
            return externalService.getData(pincode, true);
        } catch (Exception e) {
            log.error("Exception fetching response by pincode for pincode id " + pincode, e);
            return null;
        }
    }

    private void logHostName() {
        try {
            String d = environment.getProperty("server.port");
            String x1 = InetAddress.getLocalHost().getHostAddress();
            String x2 = InetAddress.getLocalHost().getHostName();
            String x3 = InetAddress.getLoopbackAddress().getHostAddress();
            String x4 = InetAddress.getLoopbackAddress().getHostName();

            log.info("LocalHost Address: " + x1 + " Local Host Name: " + x2 + " Port: " + d);
            log.info("LoopBack Address: " + x3 + " and Loopback Host Name: " + x4);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
