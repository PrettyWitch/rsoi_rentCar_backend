package com.yuhan.booking.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yuhan.booking.Util.JwtUtils;
import com.yuhan.booking.entity.Booking;
import com.yuhan.booking.entity.BookingStatus;
import com.yuhan.booking.exceptions.NotFoundException;
import com.yuhan.booking.repository.BookingRepository;
import com.yuhan.booking.request.BookingRequest;
import com.yuhan.car.entity.Car;
import com.yuhan.payment.entity.Payment;
import com.yuhan.payment.entity.PaymentStatus;
import com.yuhan.rent.entity.AvailableCars;
//import com.yuhan.rent.entity.OfficeCars;
//import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.yuhan.rent.entity.Status.Available;

/**
 * @author yuhan
 * @date 09.05.2021 - 15:27
 * @purpose
 */
@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Qualifier("com.yuhan.booking.service.RentService")
    @Autowired
    RentService rentService;

    @Qualifier("com.yuhan.booking.service.PaymentService")
    @Autowired
    PaymentService paymentService;

    @Autowired
    CarService carService;

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking findByBookingUid(int bookingUid) {
        return bookingRepository.findByBookingUid(bookingUid);
    }

    @Override
    @Transactional
    public Booking bookingCar(BookingRequest bookingRequest, HttpServletRequest httpServletRequest) {
        //payment process
        Payment payment_data = bookingRequest.getPayment_data();
        Payment payment = paymentService.newPayment(payment_data);
        logger.info("newPayment:{}", payment);
        //拿到数据传入booking
        int taken_from_office = bookingRequest.getTaken_from_office();
        int return_to_office = bookingRequest.getReturn_to_office();
        int car_uid = bookingRequest.getCar_uid();
        Car car = carService.findByCarUid(car_uid);
        //save booking
        Booking booking = new Booking();
        int bookingUid = (int) (Math.random() * 100);
        booking.setBookingUid(bookingUid);
        //先把UserUid写死
        String token = httpServletRequest.getHeader("header");
        logger.info(token);
        String uid=JwtUtils.getUsername(token);
        booking.setUserUid(Integer.parseInt(uid));
       // booking.setUserUid(1);
        booking.setCar(car);
        booking.setBookingPeriod(bookingRequest.getBooking_period());
        booking.setBookingStatus(BookingStatus.NEW);
        booking.setPaymentUid(payment.getPaymentUid());
        booking.setTakeFromOffice(taken_from_office);
        booking.setReturnToOffice(return_to_office);
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Transactional
    @Override
    public void deleteBooking(int bookingUid) {
        Booking booking = bookingRepository.findByBookingUid(bookingUid);
        //update payment -> CANCELED
        Payment payment = new Payment(booking.getPaymentUid(), PaymentStatus.CANCELED);
        paymentService.update(payment);

        //update Booking -> CANCELED
        bookingRepository.updateBooking(bookingUid,BookingStatus.CANCELED);
    }

    /**
     * 更改availableCar的可用日期
     *
     * @param bookingUid
     */
    @Override
    public String dealTime(int bookingUid) {
        Booking booking = bookingRepository.findByBookingUid(bookingUid);
        //1。检查booking_period是否在可用日期内
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String booking_period = booking.getBookingPeriod();
        String[] period = booking_period.split(":");
        Date startTime = null;
        try {
            startTime = format1.parse(period[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endTime = null;
        try {
            endTime = format1.parse(period[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format1.format(endTime);

        int taken_from_office = booking.getTakeFromOffice();
        int car_uid = booking.getCar().getCarUid();

        AvailableCars rentCar = rentService.findByCarUid(taken_from_office, car_uid);
        logger.info("rentCar:{}", rentCar);
        String availablePeriod = rentCar.getAvailabilitySchedules();
        String[] periods = availablePeriod.split(",");

        //创建2维数组
        Date[][] periods2 = new Date[periods.length][2];
        for (int i = 0; i < periods.length; i++) {
            String[] split = periods[i].split(":");
            for (int j = 0; j < split.length; j++) {
                Date time = null;
                try {
                    time = format1.parse(split[j]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                periods2[i][j] = time;
            }
        }
        //遍历数组 找到对应的区间
        int index = -1;
        for (int i = 0; i < periods2.length; i++) {
            //如果参数 Date 等于此 Date，则返回值 0；如果此 Date 在 Date 参数之前，则返回小于 0 的值；如果此 Date 在 Date 参数之后，则返回大于 0 的值。
            int i1 = startTime.compareTo(periods2[i][0]);
            int i2 = endTime.compareTo(periods2[i][1]);
            if (i1 >= 0 && i2 <= 0) {
                //找到了合适的区间
                index = i;
            }
        }

        Date[] newPeriod1 = new Date[2];
        Date[] newPeriod2 = new Date[2];
        if (index == -1) {
            throw new NotFoundException(String.format("no available period for $s", booking_period));
//            return "no available period";
        } else {
            logger.info("available period:{}", (Object) periods2[index]);
            logger.info("startTime: {},endTime:{}", startTime, endTime);
            //新产生2个区间
            Calendar c = Calendar.getInstance();
            int compareTo1 = startTime.compareTo(periods2[index][0]);
            int compareTo2 = endTime.compareTo(periods2[index][1]);
            logger.info("compareTo1:{},compareTo2:{}", compareTo1, compareTo2);

            if (compareTo1 > 0) {
                c.setTime(startTime);
                c.add(Calendar.DAY_OF_MONTH, -1);
                newPeriod1 = new Date[]{periods2[index][0], c.getTime()};
                logger.info("newPeriod1:{}", newPeriod1);
            }
            if (compareTo2 < 0) {
                c.setTime(endTime);
                c.add(Calendar.DAY_OF_MONTH, 1);
                newPeriod2 = new Date[]{c.getTime(), periods2[index][1]};
                logger.info("newPeriod2:{}", newPeriod2);
            }
            //删除原来的period
            Date[][] newArr = new Date[periods2.length - 1][2];
            int j = 0;
            //for循环将未删除的元素按原保存顺序保存到新数组
            for (int i = 0; i < periods2.length; i++) {
                if (index != i) {
                    //index不等于i持续赋值
                    newArr[j] = periods2[i];
                    //newArr[]向后移动一个元素
                    j++;
                }
            }
            logger.info("newArr:{}", newArr);

            int i1 = compareTo1 > 0 ? 1 : 0;
            int i2 = compareTo2 < 0 ? 1 : 0;
            Date[][] newArr2 = new Date[newArr.length + i1 + i2][2];
            if (i1 == 1 && i2 == 0) {
                //newPeriod1加入periods2数组中
                for (int i = 0; i < newArr2.length; i++) {
                    if (i < index) {
                        newArr2[i] = newArr[i];
                    } else if (i == index) {
                        newArr2[i] = newPeriod1;
                    } else {
                        if (newArr.length >= 1) {
                            newArr2[i] = newArr[i - 1];
                        }
                    }
                }
            } else if (i1 == 0 && i2 == 1) {
                //newPeriod2加入periods2数组中
                for (int i = 0; i < newArr2.length; i++) {
                    if (i < index) {
                        newArr2[i] = newArr[i];
                    } else if (i == index) {
                        newArr2[i] = newPeriod2;
                    } else {
                        if (newArr.length >= 1) {
                            newArr2[i] = newArr[i - 1];
                        }
                    }
                }
            } else if (i2 == 1 && i1 == 1) {
                for (int i = 0; i < newArr2.length; i++) {
                    if (i < index) {
                        newArr2[i] = newArr[i];
                    } else if (i == index) {
                        newArr2[i] = newPeriod1;
                        newArr2[i + 1] = newPeriod2;
                    } else {
                        if (newArr.length >= 1) {
                            newArr2[i + 1] = newArr[i - 1];
                        }
                    }
                }
            } else {
                System.arraycopy(newArr, 0, newArr2, 0, newArr2.length);
            }
            //newArr2:数组->String
            String[][] newArrString = new String[newArr2.length][2];
            for (int i = 0; i < newArr2.length; i++) {
                for (int k = 0; k < newArr2[i].length; k++) {
                    newArrString[i][k] = format1.format(newArr2[i][k]);
                }
            }
            StringBuilder availablePeriod2 = new StringBuilder();
            for (int i = 0; i < newArrString.length; i++) {
                if (i < newArrString.length - 1) {
                    availablePeriod2.append(newArrString[i][0]).append(":").append(newArrString[i][1]).append(",");
                } else {
                    availablePeriod2.append(newArrString[i][0]).append(":").append(newArrString[i][1]);
                }

            }
            return availablePeriod2.toString();
        }
    }

    @Transactional
    @Override
    public void finishBooking(int bookingUid) {
        Booking booking = bookingRepository.findByBookingUid(bookingUid);
        int car_uid = booking.getCar().getCarUid();
        int taken_from_office = booking.getTakeFromOffice();
        int return_to_office = booking.getReturnToOffice();

        AvailableCars availableCar = rentService.findByCarUid(taken_from_office, car_uid);
        logger.info("new availableCars:{}", availableCar);
        //新可用时间
        String availabilitySchedules = dealTime(bookingUid);
        logger.info("new availabilitySchedules:{}", availabilitySchedules);
        int registrationNumber = availableCar.getRegistrationNumber();
        logger.info("registrationNumber:{}", registrationNumber);
        //update available period
        AvailableCars availableCar2 = new AvailableCars(registrationNumber, availabilitySchedules);
        rentService.updateAvailableCars(availableCar2);

        //从Office中删除car
        rentService.deleteCarFromOffice(taken_from_office, car_uid);
        logger.info("deleteCarFromOffice");
        //在新的Office中添加car
        //如果available period == null，不可用
        AvailableCars availableCars = new AvailableCars(registrationNumber, availabilitySchedules);
        rentService.addCarToOffice(availableCars, return_to_office, car_uid);
        logger.info("addCarToOffice");

        //update payment -> PAID
        Payment payment = new Payment(booking.getPaymentUid(), PaymentStatus.PAID);
        paymentService.update(payment);

        //update booking -> FINISHED
        bookingRepository.updateBooking(bookingUid,BookingStatus.FINISHED);
    }

    @HystrixCommand
    @Override
    public List<Booking> byModel(String model) {
        List<Booking> bookings = new ArrayList<>();
        List<Booking> all = bookingRepository.findAll();
        for (Booking booking:all){
            if (booking.getCar().getModel().equals(model)){
                bookings.add(booking);
            }
        }
        return bookings;
    }

    @HystrixCommand
    @Override
    public List<Booking> byOffices(int officeUid) {
        return bookingRepository.byOffices(officeUid);
    }
}
