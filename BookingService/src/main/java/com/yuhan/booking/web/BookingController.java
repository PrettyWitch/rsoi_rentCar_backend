package com.yuhan.booking.web;


import com.yuhan.booking.entity.Booking;
import com.yuhan.booking.request.BookingRequest;
import com.yuhan.booking.service.BookingService;
import com.yuhan.car.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author yuhan
 * @date 09.05.2021 - 15:28
 * @purpose
 */
@RestController
@RequestMapping("/booking")
@CrossOrigin
public class BookingController {

    @Autowired
    BookingService bookingService;

    @GetMapping("/findall")
    public List<Booking> findall(){
        return bookingService.findAll();
    }

    /**
     * 6.预订汽车。[S] [M] [G]
     * POST /booking
     * @param bookingRequest body: { car_uid, taken_from_office, return_to_office,
     * booking_period, payment_data: { ... } }
     * @return Booking
     */
    @Operation(summary = "Make booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Make booking"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
//            @ApiResponse(responseCode = "409", description = "Item not available", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("")
    public Booking makeBooking(@Valid @RequestBody BookingRequest bookingRequest, HttpServletRequest httpServletRequest){
        return bookingService.bookingCar(bookingRequest,httpServletRequest);
    }

    /**
     * 7.取消预订。[S] [M] [G]
     * @param bookingUid DELETE /booking/{bookingUid}
     */
    @Operation(summary = "Delete booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete booking"),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/{bookingUid}")
    public void deleteBooking(@PathVariable int bookingUid){
        bookingService.deleteBooking(bookingUid);
    }

    @PostMapping("/save")
    public Booking saveBooking(@Valid @RequestBody Booking booking){
        return bookingService.save(booking);
    }

    /**
     * 8.完成您的预订。[S] [M] [G]
     * @param bookingUid PATCH /booking/{bookingUid}/finish
     */
    @Operation(summary = "Finish booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Finish booking"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
//            @ApiResponse(responseCode = "409", description = "Item not available", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PatchMapping("/{bookingUid}/finish")
    public void finishBooking(@PathVariable int bookingUid){
        bookingService.finishBooking(bookingUid);
    }

    /**
     * 13.按型号查看汽车预订的统计信息。[A] [G]
     * @param car GET /reports/booking-by-models
     * @return
     */
    @Operation(summary = "Find booking by model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find booking by model"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
//            @ApiResponse(responseCode = "409", description = "Item not available", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("booking-by-models")
    public List<Booking> byModel(@RequestParam String model){
        return bookingService.byModel(model);
    }

    /**
     * 14.按办公室查看预订统计信息。[A] [G]
     * @param officeUid GET /reports/booking-by-offices
     * @return
     */
    @Operation(summary = "Find booking by office")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find booking by model"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
//            @ApiResponse(responseCode = "409", description = "Item not available", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/booking-by-offices")
    public List<Booking> byOffice(@RequestParam int officeUid){
        return bookingService.byOffices(officeUid);
    }
}
