package com.getyourtickets.controller;

import com.getyourtickets.dto.ApiResponse;
import com.getyourtickets.dto.fndplan.FndPlanRequest;
import com.getyourtickets.dto.fndplan.FndPlanResponse;
import com.getyourtickets.service.FndPlanService;
import com.getyourtickets.utils.Constants;
import jakarta.validation.Valid;
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
                    .code(Constants.API_SUCCESS_CODE)
                    .message(Constants.API_SUCCESS_MSG)
                    .result(responses)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Fnd Plans: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse> createFndPlan(@RequestBody @Valid FndPlanRequest request) {
        FndPlanResponse response = fndPlanService.insertFndPlan(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(Constants.API_SUCCESS_CODE)
                .message(Constants.API_SUCCESS_MSG)
                .result(response)
                .build(), HttpStatus.CREATED);
    }
    
    @GetMapping("/get/{code}")
    public ResponseEntity<ApiResponse> getFndPlanById(@PathVariable String code) throws Exception {
        FndPlanResponse response = fndPlanService.getFndPlanByCode(code);

        return new ResponseEntity<>(ApiResponse.builder()
                .code(Constants.API_SUCCESS_CODE)
                .message(Constants.API_SUCCESS_MSG)
                .result(response)
                .build(), HttpStatus.OK);
    }
}
