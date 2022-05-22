package common.bean.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum ProtocolEnum {
    HTTP("http"),
    ;

    @Getter
    private String protocol;

    ProtocolEnum(String protocol) {
        this.protocol = protocol;
    }

    public static ProtocolEnum getByProtocol(String protocol) {
        for (ProtocolEnum protocolEnum: ProtocolEnum.values()) {
            if (StringUtils.equalsIgnoreCase(protocolEnum.getProtocol(), protocol)) {
                return protocolEnum;
            }
        }
        return HTTP;
    }
}
