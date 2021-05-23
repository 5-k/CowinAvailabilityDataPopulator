package com.prateek.cowinAvailibility.dto.cowinResponse;

import java.util.List;
import lombok.Data;

@Data
public class CowinResponse {
    private List<CowinResponseCenter> centers;

    @Override
    public String toString() {
        return "CowinResponse [centers=" + centers + "]";
    }

    public List<CowinResponseCenter> getCenters() {
        return centers;
    }

    public void setCenters(List<CowinResponseCenter> centers) {
        this.centers = centers;
    }

}
