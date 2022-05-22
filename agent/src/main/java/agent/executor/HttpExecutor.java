package agent.executor;


import common.bean.enums.ProtocolEnum;
import common.bean.exception.ExecuteException;
import common.bean.request.HttpRequest;
import common.bean.response.HttpResponse;
import common.bean.util.HttpUtils;
import org.springframework.stereotype.Service;

@Service
public class HttpExecutor extends AbstractExecutor<HttpRequest, HttpResponse> {
    @Override
    public ProtocolEnum getProtocol() {
        return ProtocolEnum.HTTP;
    }

    @Override
    public HttpResponse execute(HttpRequest httpRequest) throws ExecuteException {
        return HttpUtils.execute(httpRequest);
    }
}
