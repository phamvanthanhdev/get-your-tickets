package com.getyourtickets.implement;

import com.getyourtickets.dto.FndPlanRequest;
import com.getyourtickets.dto.FndPlanResponse;
import com.getyourtickets.exception.ErrorEnum;
import com.getyourtickets.exception.GytException;
import com.getyourtickets.mapper.FndPlanMapper;
import com.getyourtickets.model.FndPlan;
import com.getyourtickets.service.FndPlanService;
import com.getyourtickets.utils.FndPlanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
        return fndPlans.stream().map(FndPlanResponse::from).collect(Collectors.toList());
    }

    @Override
    public FndPlanResponse insertFndPlan(FndPlanRequest request) {
        Map<String, Object> params = request.toMap();
        String planCodeHighest = fndPlanMapper.getFndPlanCodeHighest();
        params.put("code", FndPlanUtils.generateFndPlanCode(planCodeHighest));

        fndPlanMapper.insertFndPlan(params);
        return FndPlanResponse.builder()
                .planCode((String) params.get("code"))
                .build();
    }

    @Override
    public FndPlanResponse getFndPlanByCode(String code) throws Exception {
        FndPlan fndPlan = fndPlanMapper.getFndPlanByCode(code);
        if (fndPlan == null) {
            throw new GytException(ErrorEnum.FND_PLAN_NOT_FOUND);
        }
        return FndPlanResponse.from(fndPlan);

    }

}
