package com.yuhan.booking.service;

import com.yuhan.car.entity.Car;
import com.yuhan.rent.entity.AvailableCars;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author yuhan
 * @date 02.06.2021 - 14:29
 * @purpose
 */
@FeignClient(value = "rent-service", url = "localhost:8182")
public interface RentService {
    /***
     * 4.获取有关汽车及其在特定办公室中的可用性的信息。[G]GET /办公室/ {officeUid} /汽车/ {carUid}
     * 手动寻找匹配carUid
     * @param officeUid 前端传入officeUid
     * @param carUid 前端传入carUid
     * @return 通过按前端传入officeUid和carUid在所有的availableCars中查找，得到AvailableCars
     */
    @RequestMapping(value = "/office/{officeUid}/cars/{carUid}", method = RequestMethod.GET)
    public AvailableCars findByCarUid(@PathVariable int officeUid, @PathVariable int carUid);

    /***
     * 12.从办公室删除车辆。[A] [M] [G]
     * @param officeUid DELETE /offices/{officeUid}/car/{carUid}
     * @param carUid
     */
    @RequestMapping(value = {"/office/{officeUid}/car/{carUid}"}, method = RequestMethod.DELETE)
    public void deleteCarFromOffice(@PathVariable("officeUid") int officeUid, @PathVariable("carUid") int carUid);

    /**
     * 11.在办公室添加新车。[A] [M] [G]
     *
     * @param availableCar body: { registration_number, available }
     * @param officeUid  POST /offices/{officeUid}/car/{carUid}
     * @param carUid
     */
    @RequestMapping(value = {"/office/{officeUid}/car/{carUid}"}, method = RequestMethod.POST)
    public String addCarToOffice(@Valid @RequestBody AvailableCars availableCar, @PathVariable int officeUid, @PathVariable int carUid);

    /**
     * 更新可用时间
     * @param availableCars
     */
    @RequestMapping(value = {"/office/availableCar/update"},method = RequestMethod.POST)
    public void updateAvailableCars(@Valid @RequestBody AvailableCars availableCars);
}
