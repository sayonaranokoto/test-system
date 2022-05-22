package platform.runner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import common.bean.base.Result;
import common.bean.enums.ProtocolEnum;
import common.bean.request.HttpRequest;
import common.bean.response.HttpResponse;
import platform.entity.validation.HttpValidation;


public class HttpTestRunner extends TestRunner<HttpRequest, HttpResponse, HttpValidation> {
    @Override
    public ProtocolEnum getProtocol() {
        return ProtocolEnum.HTTP;
    }

    @Override
    protected HttpResponse toResp(String str) {
        Result<HttpResponse> result = JSON.parseObject(str, new TypeReference<Result<HttpResponse>>(){});
        return result.getData();
    }
}
