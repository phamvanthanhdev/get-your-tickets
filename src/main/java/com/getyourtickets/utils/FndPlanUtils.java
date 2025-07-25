package com.getyourtickets.utils;

public class FndPlanUtils {
    public static String generateFndPlanCode(String planCodeHighest) {
        if (planCodeHighest == null || planCodeHighest.isEmpty()) {
            return "FNDPL0001";
        }
        int nextCode = Integer.parseInt(planCodeHighest.replace(Constants.FND_PLAN_CODE_PREFIX, "")) + 1;
        return String.format("FNDPL%04d", nextCode);
    }
}
