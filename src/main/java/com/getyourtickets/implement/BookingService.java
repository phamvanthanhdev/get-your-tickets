package com.getyourtickets.implement;

import com.getyourtickets.dto.ApiResponse;
import com.getyourtickets.dto.booking.BookingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class BookingService {
    @Autowired
    private ConcurrentHashMap<String, Long> outOrderNos;
    private static final long EXPIRATION_TIME_MS = 30 * 1000L; // 30 seconds

    public ApiResponse createBookingBasic(BookingRequest request) {
        String outOrderNo = request.getOutOrderNo();

        cleanExpiredOrders();
        Long expirationTime = System.currentTimeMillis() + EXPIRATION_TIME_MS;
        Long existing = outOrderNos.putIfAbsent(outOrderNo, expirationTime);
        if (existing != null) {
            log.info("outOrderNo {} already exists in map", outOrderNo);
            return ApiResponse.builder().code(400).message("OutOrderNo đã tồn tại: " + outOrderNo).build();
        }

        try {
            // Simulate processing time
            Thread.sleep(5000);
            return ApiResponse.builder().code(200).message("Booking created successfully").build();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ApiResponse.builder().code(500).message("Process bị interrupted").build();
        } finally {
            if (outOrderNos.containsKey(outOrderNo)) {
                log.info("Removing outOrderNo: {}", outOrderNo);
            }
            outOrderNos.remove(outOrderNo);
        }
    }

    private void cleanExpiredOrders() {
        long currentTime = System.currentTimeMillis();
        Iterator<ConcurrentHashMap.Entry<String, Long>> iterator = outOrderNos.entrySet().iterator();
        while (iterator.hasNext()) {
            ConcurrentHashMap.Entry<String, Long> entry = iterator.next();
            if (currentTime > entry.getValue()) {
                log.info("Removing expired outOrderNo: {}", entry.getKey());
                iterator.remove();
            }
        }
    }

    public void demonstrateRaceCondition(String testOrderNo) {
        // Simulate multiple concurrent requests
        for (int i = 0; i < 5; i++) {
            final int threadNum = i;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

                new Thread(() -> {
                    try {
                        BookingRequest request = new BookingRequest();
                        request.setOutOrderNo(testOrderNo);

                        ApiResponse response = createBookingBasic(request);
                        log.info("Thread " + threadNum + " result: " +
                                (response.getCode() == 200 ? "SUCCESS" : response.getMessage()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        /*if (outOrderNos.containsKey(testOrderNo)) {
                            log.info("Thread " + threadNum + "Removing outOrderNo: {}", testOrderNo);
                        }
                        outOrderNos.remove(testOrderNo);*/
                    }
                }, "BookingThread-" + i).start();

        }
    }
}
