package platform.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.commons.lang3.StringUtils;
import platform.entity.Expression;
import platform.enums.ResponseDataTypeEnum;
import platform.enums.ValidateConditionEnum;
import platform.exception.ValidateException;
import platform.exception.ValidateExceptionEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
    public static <T> void validate(String type, T responseBody, Expression expression) throws ValidateException {
        ResponseDataTypeEnum dataTypeEnum = ResponseDataTypeEnum.getByType(type);
        switch (dataTypeEnum) {
            case STRING:
                ValidateUtils.validateStringData(responseBody.toString(), expression);
                break;
            case JSON:
                ValidateUtils.validateJsonData(responseBody.toString(), expression);
                break;
            case MAP:
                ValidateUtils.validateMapData((Map)responseBody, expression);
                break;
            default:
                break;
        }
    }

    public static void validateJsonData(String json, Expression expression) throws ValidateException {
        if (StringUtils.isBlank(expression.getPath())) {
            throw new ValidateException(ValidateExceptionEnum.EXPRESSION_PARAM_ERROR);
        }
        String value = JSONPath.read(json, "$." + expression.getPath(), String.class);
        validateStringData(value, expression);
    }

    public static void validateStringData(String str, Expression expression) throws ValidateException {
        if (StringUtils.isBlank(expression.getCondition())) {
            throw new ValidateException(ValidateExceptionEnum.EXPRESSION_PARAM_ERROR);
        }
        ValidateConditionEnum conditionEnum = ValidateConditionEnum.getByCondition(expression.getCondition());
        if (conditionEnum == null) {
            throw new ValidateException(ValidateExceptionEnum.EXPRESSION_PARAM_ERROR);
        }
        if(StringUtils.isBlank(expression.getValue())) {
            throw new ValidateException(ValidateExceptionEnum.EXPRESSION_PARAM_ERROR);
        }
        boolean valid = true;
        switch (conditionEnum) {
            case EQUALS:
                valid = isEquals(str, expression.getValue());
                break;
            case CONTAINS:
                valid = isContains(str, expression.getValue());
                break;
            case REGEX:
                valid = isRegex(str, expression.getValue());
                break;
            default:
                break;
        }
        if (!valid) {
            throw new ValidateException(ValidateExceptionEnum.EXPRESSION_PARAM_ERROR);
        }
    }

    public static void validateMapData(Map map, Expression expression) throws ValidateException {
        String json = JSONObject.toJSONString(map);
        validateJsonData(json, expression);
    }

    private static boolean isEquals(String value, String expressionValue) {
        return StringUtils.equalsIgnoreCase(value, expressionValue);
    }

    private static boolean isContains(String value, String expressionValue) {
        return value.contains(expressionValue);
    }

    private static boolean isRegex(String value, String expressionValue) throws ValidateException {
        try {
            Pattern pattern = Pattern.compile(expressionValue);
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        } catch (Exception ex) {
            throw new ValidateException(ValidateExceptionEnum.EXPRESSION_PARAM_ERROR);
        }
    }

    public static void main(String[] args) {
        String str = "{\n" +
                "    \"code\": 200,\n" +
                "    \"data\": {\n" +
                "        \"key\": \"k1\",\n" +
                "        \"list\": [\n" +
                "            \"123\",\n" +
                "            \"aaa\"\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        String value = JSONPath.read(str, ".data.key", String.class);

    }
}
