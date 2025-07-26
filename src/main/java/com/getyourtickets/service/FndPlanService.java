package com.getyourtickets.service;

import com.getyourtickets.dto.fndplan.FndPlanRequest;
import com.getyourtickets.dto.fndplan.FndPlanResponse;
import com.getyourtickets.model.FndPlan;

import java.util.List;

public interface FndPlanService {
    List<FndPlan> getAllFndPlans();
    List<FndPlanResponse> getListFndPlanResponse();
    FndPlanResponse insertFndPlan(FndPlanRequest request);
    FndPlanResponse getFndPlanByCode(String code) throws Exception;
}
