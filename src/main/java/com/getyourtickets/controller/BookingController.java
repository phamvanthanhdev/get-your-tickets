package com.getyourtickets.controller;

import com.getyourtickets.implement.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/booking")
@Slf4j
public class BookingController {
    @Autowired
    private ConcurrentHashMap<String, Long> outOrderNos;
    @Autowired
    private BookingService bookingService;
    @PostMapping("/create")
    public String createBasicBooking(@RequestParam String outOrderNo) {
        System.out.println("Creating booking with outOrderNo: " + outOrderNo);

            bookingService.demonstrateRaceCondition(outOrderNo);

        return "Booking API is working!";
    }
}
