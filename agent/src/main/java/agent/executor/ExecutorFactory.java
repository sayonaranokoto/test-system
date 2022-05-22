package agent.executor;

import common.bean.enums.ProtocolEnum;
import common.bean.exception.ExecuteException;
import common.bean.exception.ExecuteExceptionEnum;
import common.bean.request.BaseRequest;
import common.bean.request.HttpRequest;
import common.bean.response.BaseResponse;
import common.bean.response.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExecutorFactory {
    @Autowired
    private HttpExecutor httpExecutor;

    public HttpResponse execute(HttpRequest httpRequest) throws ExecuteException {
        return httpExecutor.execute(httpRequest);
    }
}
