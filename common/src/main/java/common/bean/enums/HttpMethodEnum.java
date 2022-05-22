package common.bean.enums;


import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum HttpMethodEnum {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete"),
    ;

    @Getter
    private String method;

    HttpMethodEnum(String method) {
        this.method = method;
    }

    public static HttpMethodEnum getMethod(String method) {
        for (HttpMethodEnum httpMethodEnum: HttpMethodEnum.values()) {
            if (StringUtils.equalsIgnoreCase(method, httpMethodEnum.getMethod())) {
                return httpMethodEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return method;
    }
}
