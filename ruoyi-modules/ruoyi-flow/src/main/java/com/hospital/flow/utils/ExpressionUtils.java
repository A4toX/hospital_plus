package com.hospital.flow.utils;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lgx
 */
public class ExpressionUtils {

    public static boolean getBooleanValue(String expressionStr, Map<String, Object> variables) {
        return (Boolean) getValue(expressionStr, variables);
    }

    public static Object getValue(String expressionStr, Map<String, Object> variables) {
        ExpressionParser parser = new SpelExpressionParser();
        try {
            Expression expression = parser.parseExpression(expressionStr);
            EvaluationContext evaluationContext = new StandardEvaluationContext();
            for (Map.Entry<String, Object> entity : variables.entrySet()) {
                evaluationContext.setVariable(entity.getKey(), entity.getValue());
            }
            return expression.getValue(evaluationContext);
        } catch (Exception e) {
            throw new RuntimeException("解析表达式失败");
        }
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("a", 6);
        map.put("b", 2);
        boolean booleanValue = ExpressionUtils.getBooleanValue("#a > 4 && #b > 1", map);
        System.out.println(booleanValue);
    }
}
