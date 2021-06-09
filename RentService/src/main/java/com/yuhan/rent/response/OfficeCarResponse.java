package com.yuhan.rent.response;

import com.yuhan.rent.entity.AvailableCars;
import com.yuhan.rent.entity.Office;
import com.yuhan.rent.entity.Status;
import lombok.Data;

/**
 * @author yuhan
 * @date 01.06.2021 - 20:18
 * @purpose
 */
@Data
public class OfficeCarResponse {
//    private int id;
    private Office office;
    private AvailableCars availableCars;
//    private String AvailabilitySchedules;

    public OfficeCarResponse(Office office, AvailableCars availableCars) {
//        this.id = id;
        this.office = office;
        this.availableCars = availableCars;
//        AvailabilitySchedules = availabilitySchedules;
    }

    public OfficeCarResponse() {
    }
}
