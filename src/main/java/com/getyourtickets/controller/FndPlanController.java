package com.getyourtickets.controller;

import com.getyourtickets.dto.ApiResponse;
import com.getyourtickets.dto.FndPlanResponse;
import com.getyourtickets.mapper.FndPlanMapper;
import com.getyourtickets.service.CommonService;
import com.getyourtickets.service.FndPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

            return new ResponseEntity<>(CommonService.buildResponse(1000, "Success", responses), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Fnd Plans: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFndPlan() {


        return new ResponseEntity<>("Fnd Plan created successfully", HttpStatus.CREATED);
    }


}
