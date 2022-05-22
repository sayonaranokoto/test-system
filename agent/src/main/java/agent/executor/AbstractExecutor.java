package agent.executor;

import common.bean.enums.ProtocolEnum;
import common.bean.exception.ExecuteException;
import common.bean.request.BaseRequest;
import common.bean.response.BaseResponse;

public abstract class AbstractExecutor<Req extends BaseRequest, Resp extends BaseResponse> {
    public abstract ProtocolEnum getProtocol();

    public abstract Resp execute(Req req) throws ExecuteException;
}
