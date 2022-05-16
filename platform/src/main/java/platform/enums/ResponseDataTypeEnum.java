package platform.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum ResponseDataTypeEnum {
    STRING("string"),
    JSON("json"),
    MAP("map"),
    ;

    private String type;

    ResponseDataTypeEnum(String type) {
        this.type = type;
    }

    public static ResponseDataTypeEnum getByType(String type) {
        for(ResponseDataTypeEnum typeEnum: ResponseDataTypeEnum.values()) {
            if (StringUtils.equalsIgnoreCase(type, typeEnum.getType())) {
                return typeEnum;
            }
        }
        return STRING;
    }
}
