package com.getyourtickets.controller;

import com.getyourtickets.mapper.FndPlanMapper;
import com.getyourtickets.service.FndPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fnd-plan")
public class FndPlanController {
    @Autowired
    private FndPlanService fndPlanService;

    @RequestMapping("/getAll")
    public Object getAllFndPlans() {
        try {
            return fndPlanService.getAllFndPlans();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Fnd Plans: " + e.getMessage());
//            return "Error fetching Fnd Plans: " + e.getMessage();
        }
    }
}
