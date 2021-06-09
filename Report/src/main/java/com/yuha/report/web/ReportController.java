package com.yuha.report.web;

import com.yuha.report.service.BookingService;
import com.yuhan.booking.entity.Booking;
import com.yuhan.car.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yuhan
 * @date 06.06.2021 - 15:21
 * @purpose
 */
@RestController
@RequestMapping("/reports")
public class ReportController {

    @Qualifier("com.yuha.report.service.BookingService")
    @Autowired
    BookingService bookingService;

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Operation(summary = "Find booking by model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find booking by model"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = com.yuhan.car.exception.ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = {@Content(schema = @Schema(implementation = com.yuhan.car.exception.ErrorResponse.class))}),
//            @ApiResponse(responseCode = "409", description = "Item not available", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("booking-by-models")
    public List<Booking> byModel(@RequestParam String model) {
        logger.info("report booking by models:{}", model);
        return bookingService.byModel(model);
    }


    @Operation(summary = "Find booking by office")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find booking by model"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = com.yuhan.car.exception.ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = {@Content(schema = @Schema(implementation = com.yuhan.car.exception.ErrorResponse.class))}),
//            @ApiResponse(responseCode = "409", description = "Item not available", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = com.yuhan.car.exception.ErrorResponse.class))})
    })
    @GetMapping("booking-by-offices")
    public List<Booking> byOffice(@RequestParam int officeUid) {
        logger.info("report booking by office:{}", officeUid);
        return bookingService.byOffice(officeUid);
    }


}