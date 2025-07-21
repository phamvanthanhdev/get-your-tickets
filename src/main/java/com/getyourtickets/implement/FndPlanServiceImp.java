package com.getyourtickets.implement;

import com.getyourtickets.dto.FndPlanResponse;
import com.getyourtickets.mapper.FndPlanMapper;
import com.getyourtickets.model.FndPlan;
import com.getyourtickets.service.FndPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FndPlanServiceImp implements FndPlanService {
    @Autowired
    private FndPlanMapper fndPlanMapper;

    @Override
    public List<FndPlan> getAllFndPlans() {
        return fndPlanMapper.getAllFndPlans();
    }

    @Override
    public List<FndPlanResponse> getListFndPlanResponse() {
        List<FndPlan> fndPlans = this.getAllFndPlans();
        return fndPlans.stream().map(p-> FndPlanResponse.from(p)).collect(Collectors.toList());
    }


}
