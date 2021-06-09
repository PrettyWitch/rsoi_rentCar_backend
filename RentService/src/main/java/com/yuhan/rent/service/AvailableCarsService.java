package com.yuhan.rent.service;

import com.yuhan.rent.entity.AvailableCars;
import com.yuhan.rent.response.OfficeCarResponse;

import java.util.List;
import java.util.Optional;

/**
 * @author yuhan
 * @date 09.05.2021 - 13:38
 * @purpose
 */
public interface AvailableCarsService {

    public List<AvailableCars> findAllAvailableCars();

    public AvailableCars findByRegistrationNumber(int RegistrationNumber);

    public String CreateAvailableCar(AvailableCars availableCars);

    public void deleteAvailableCar(int officeUid, int carUid) ;

    //更新AvailableCar
    public void updateAvailableCar(int RegistrationNumber, String availabilitySchedules);

    public void saveAvailableCar(AvailableCars availableCar);

    public List<OfficeCarResponse> findByOfficeUid(int officeUid);

    public List<OfficeCarResponse> findByOfficeUidCarUid(int officeUid, int carUid);

    public List<OfficeCarResponse> findByCarUid(int carUid);
//    //5.获取有关汽车及其在所有办公室的可用性的信息。[G]GET /办公室/汽车/ {carUid}
//    public List<AvailableCars> findByCarUid(int carUid);
}
