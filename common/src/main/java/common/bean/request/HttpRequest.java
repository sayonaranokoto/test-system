package common.bean.request;

import lombok.Data;

import java.util.Map;

@Data
public class HttpRequest extends BaseRequest {
    private String domain;
    private String context;
    private String path;
    private String method;
    private Map<String, String> headers;
    private Map<String, String> parameters;
    private String body;
}
