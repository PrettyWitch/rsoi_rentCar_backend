package com.yuhan.rent.service;

import com.yuhan.car.entity.Car;
import com.yuhan.rent.entity.AvailableCars;
import com.yuhan.rent.entity.Office;
import com.yuhan.rent.repository.AvailableCarRepository;
import com.yuhan.rent.response.OfficeCarResponse;
import com.yuhan.rent.web.OfficeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author yuhan
 * @date 09.05.2021 - 14:02
 * @purpose
 */
@Service
public class AvailableCarsServiceImpl implements AvailableCarsService {
    @Autowired
    AvailableCarRepository availableCarRepository;

    @Qualifier("com.yuhan.rent.service.CarService")
    @Autowired
    CarService carService;

    @Autowired
    OfficeService officeService;

    private static final Logger logger = LoggerFactory.getLogger(AvailableCarsServiceImpl.class);

    @Override
    public List<AvailableCars> findAllAvailableCars() {
        return availableCarRepository.findAll();
    }

    @Override
    public AvailableCars findByRegistrationNumber(int RegistrationNumber) {
        return availableCarRepository.findByRegistrationNumber(RegistrationNumber)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with uid %s not found", RegistrationNumber)));
    }

    /**
     * @param availableCars （传入carUid->car 和officeUid）的availableCars构造器
     * @return 自动填充registrationNumber和AvailabilitySchedules
     */
    @Override
    public String CreateAvailableCar(AvailableCars availableCars) {
        //registrationNumber
        int registrationNumber = (int) (Math.random() * 100);
        availableCars.setRegistrationNumber(registrationNumber);
        logger.info("registrationNumber:{}", registrationNumber);
        //AvailabilitySchedules
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String beginDate = sf.format(date);
        c.setTime(date);
        c.add(Calendar.MONTH, 2);
        String endDate = sf.format(c.getTime());
        availableCars.setAvailabilitySchedules(beginDate + ":" + endDate);
        logger.info("beginDate:{} : endDate:{}", beginDate, endDate);
        //save
        logger.info("availableCars:{}", availableCars);
        availableCarRepository.save(availableCars);
        if (availableCars.getRegistrationNumber() > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    @Override
    public void deleteAvailableCar(int officeUid, int carUid) {
        List<AvailableCars> availableCars = availableCarRepository.findByOfficeUid(officeUid);
        for (AvailableCars cars : availableCars) {
            if (cars.getCar().getCarUid() == carUid) {
                availableCarRepository.delete(cars);
            }
        }
    }


    @Transactional
    @Override
    public void updateAvailableCar(int RegistrationNumber, String availabilitySchedules) {
        availableCarRepository.updateAvailableCars(RegistrationNumber, availabilitySchedules);
    }

    @Override
    public void saveAvailableCar(AvailableCars availableCar) {
        availableCarRepository.save(availableCar);
    }

    @Override
    public List<OfficeCarResponse> findByOfficeUid(int officeUid) {
        List<OfficeCarResponse> officeCarResponses = new ArrayList<>();
        List<AvailableCars> availableCars = availableCarRepository.findByOfficeUid(officeUid);
        Office office = officeService.findById(officeUid);
        for (AvailableCars cars : availableCars) {
            officeCarResponses.add(new OfficeCarResponse(office, cars));
        }
        return officeCarResponses;
    }

    @Override
    public List<OfficeCarResponse> findByOfficeUidCarUid(int officeUid, int carUid) {
        List<OfficeCarResponse> officeCarResponses = new ArrayList<>();
        List<AvailableCars> availableCars = availableCarRepository.findByOfficeUid(officeUid);
        Office office = officeService.findById(officeUid);
        AvailableCars car = new AvailableCars();
        for (AvailableCars cars : availableCars) {
            if (cars.getCar().getCarUid() == carUid) {
                car = cars;
            }
        }
        officeCarResponses.add(new OfficeCarResponse(office, car));
        return officeCarResponses;
    }

    @Override
    public List<OfficeCarResponse> findByCarUid(int carUid) {
        List<OfficeCarResponse> officeCarResponses = new ArrayList<>();
        Car car = carService.findByCarUid(carUid);
        List<AvailableCars> availableCars = availableCarRepository.findByCar(car);
        for (AvailableCars cars : availableCars){
            Office office = officeService.findById(cars.getOfficeUid());
            officeCarResponses.add(new OfficeCarResponse(office, cars));
        }
        return officeCarResponses;
    }


//    /***
//     * 5.获取有关汽车及其在所有办公室的可用性的信息。[G]GET /办公室/汽车/ {carUid}
//     * @param carUid 前端传入carUid，
//     * @return
//     */
//    @Override
//    public List<AvailableCars> findByCarUid(int carUid) {
//        logger.info("carUid:{}",carUid);
////        Car car = carService.findByCarUid(carUid);
//
//        List<AvailableCars> availableCars = availableCarRepository.findAll();
//        List<AvailableCars> availableCars2 = new ArrayList<>();
//        for (AvailableCars car : availableCars) {
//            if (car.getCar().getCarUid() == carUid) {
//                availableCars2.add(car);
//            }
//        }
//        return availableCars2;
////        List<AvailableCars> cars = availableCarRepository.findByCar(car);
////        logger.info("cars:{}", cars);
////        return cars;
//
//    }


}
