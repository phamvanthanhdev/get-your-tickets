package com.getyourtickets.mapper;

import com.getyourtickets.model.FndPlan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FndPlanMapper {
    List<FndPlan> getAllFndPlans();
    String getFndPlanCodeHighest();
    void insertFndPlan(Map<String, Object> params);
    FndPlan getFndPlanByCode(String code);
}
