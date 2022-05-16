package platform.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum ValidateConditionEnum {
    EQUALS("equals"),
    CONTAINS("contains"),
    REGEX("regex"),
    ;

    @Getter
    private String condition;

    ValidateConditionEnum(String condition) {
        this.condition = condition;
    }

    public static ValidateConditionEnum getByCondition(String condition) {
        for (ValidateConditionEnum conditionEnum: ValidateConditionEnum.values()) {
            if (StringUtils.equalsIgnoreCase(conditionEnum.getCondition(), condition)) {
                return conditionEnum;
            }
        }
        return null;
    }
}
