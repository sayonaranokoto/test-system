package common.bean.response;

import lombok.Data;

import java.util.Map;

@Data
public class HttpResponse extends BaseResponse<String> {
    private int code;
    private Map<String, String> header;
}
