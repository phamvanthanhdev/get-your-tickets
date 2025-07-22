package com.getyourtickets.controller;

import com.getyourtickets.dto.ApiResponse;
import com.getyourtickets.dto.FndPlanRequest;
import com.getyourtickets.dto.FndPlanResponse;
import com.getyourtickets.mapper.FndPlanMapper;
import com.getyourtickets.service.CommonService;
import com.getyourtickets.service.FndPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fnd-plan")
public class FndPlanController {
    @Autowired
    private FndPlanService fndPlanService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllFndPlans() {
        try {
            List<FndPlanResponse> responses = fndPlanService.getListFndPlanResponse();

            return new ResponseEntity<>(ApiResponse.builder()
                    .code(1000)
                    .message("success")
                    .result(responses)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Fnd Plans: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse> createFndPlan(@RequestBody FndPlanRequest request) {
        FndPlanResponse response = fndPlanService.insertFndPlan(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(1000)
                .message("success")
                .result(response)
                .build(), HttpStatus.CREATED);
    }


}
