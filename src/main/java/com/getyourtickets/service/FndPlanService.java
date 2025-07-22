package com.getyourtickets.service;

import com.getyourtickets.dto.FndPlanRequest;
import com.getyourtickets.dto.FndPlanResponse;
import com.getyourtickets.model.FndPlan;

import java.util.List;

public interface FndPlanService {
    List<FndPlan> getAllFndPlans();

    List<FndPlanResponse> getListFndPlanResponse();

    FndPlanResponse insertFndPlan(FndPlanRequest request);
}
