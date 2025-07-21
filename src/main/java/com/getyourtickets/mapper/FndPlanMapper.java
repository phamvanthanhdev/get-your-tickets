package com.getyourtickets.mapper;

import com.getyourtickets.model.FndPlan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FndPlanMapper {
    List<FndPlan> getAllFndPlans();
}
