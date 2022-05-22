package agent.controller;

import agent.executor.ExecutorFactory;
import common.bean.base.Result;
import common.bean.exception.ExecuteException;
import common.bean.exception.ExecuteExceptionEnum;
import common.bean.request.HttpRequest;
import common.bean.response.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("exec")
public class AgentController {
    @Autowired
    private ExecutorFactory executorFactory;

    @PostConstruct
    public void init() {
        String a = "1";
        String b = a + "1";
    }

    @PostMapping("http")
    public Result<HttpResponse> execHttpCase(@RequestBody HttpRequest httpRequest) {
        try {
            HttpResponse httpResponse = executorFactory.execute(httpRequest);
            return Result.of(httpResponse);
        } catch (ExecuteException ex) {
            return Result.fail(ex.getExceptionEnum());
        } catch (Exception ex) {
            return Result.fail(ExecuteExceptionEnum.UNKNOWN);
        }
    }
}
