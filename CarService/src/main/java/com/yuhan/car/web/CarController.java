package com.yuhan.car.web;

import com.yuhan.car.entity.Car;
import com.yuhan.car.exception.ErrorResponse;
import com.yuhan.car.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cars")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    CarService carService;


    @GetMapping("/findall")
    public List<Car> findAll() {
        return carService.findAll();
    }

    @Operation(summary = "Car info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car info"),
            @ApiResponse(responseCode = "404", description = "Car not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{carUid}")
    public Car findById(@PathVariable int carUid) {
        return carService.findById(carUid);
    }

    @Operation(summary = "Create car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create car"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping(value = "/create")
    public String createCar(@Valid @RequestBody Car car) {
        return carService.createCar(car);
    }

    @Operation(summary = "Delete car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete car"),
            @ApiResponse(responseCode = "404", description = "Car not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{carUid}")
    public void deleteCar(@PathVariable int carUid) {
        carService.deleteCar(carUid);
    }

    @Operation(summary = "Update car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Update car"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Car not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "External request failed", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/update")
    public String update(@Valid @RequestBody Car car) {
        return carService.update(car);
    }

}