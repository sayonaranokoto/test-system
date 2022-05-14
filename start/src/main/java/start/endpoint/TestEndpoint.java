package start.endpoint;

import common.bean.base.BizException;
import common.bean.base.Result;
import common.bean.base.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestEndpoint {
    @GetMapping
    public String get() {
        return "get";
    }

    @GetMapping("/1")
    public Result err() {
        throw new BizException(StatusCode.UNKNOWN);
    }
}
