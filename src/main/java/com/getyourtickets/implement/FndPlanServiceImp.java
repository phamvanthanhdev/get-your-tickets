package com.getyourtickets.implement;

import com.getyourtickets.mapper.FndPlanMapper;
import com.getyourtickets.model.FndPlan;
import com.getyourtickets.service.FndPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FndPlanServiceImp implements FndPlanService {
    @Autowired
    private FndPlanMapper fndPlanMapper;

    @Override
    public List<FndPlan> getAllFndPlans() {
        return fndPlanMapper.getAllFndPlans();
    }
}
