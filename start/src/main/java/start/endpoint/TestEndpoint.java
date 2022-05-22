package start.endpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.bean.base.BizException;
import common.bean.base.Result;
import common.bean.base.StatusCode;
import common.bean.request.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.entity.validation.HttpValidation;
import platform.runner.HttpTestRunner;
import platform.runner.TestRunner;

@RestController
@RequestMapping("/test")
public class TestEndpoint {

    @GetMapping
    public Result<String> get() {
        return Result.of("get");
    }

    @GetMapping("/1")
    public Result err() {
        throw new BizException(StatusCode.UNKNOWN);
    }

    @GetMapping("/2")
    public Result test() {
        TestRunner runner = new HttpTestRunner();
        runner.setRequest(httpRequest);
        runner.setValidation(httpValidation);
        runner.run();
        return Result.of();
    }

    private static String req = "{\n" +
            "    \"context\": \"\",\n" +
            "    \"domain\": \"http://localhost:8080\",\n" +
            "    \"headers\": {\n" +
            "        \"h1\": \"123\",\n" +
            "        \"h2\": \"abc\"\n" +
            "    },\n" +
            "    \"method\": \"GET\",\n" +
            "    \"parameters\": {\n" +
            "        \"offset\": \"1\",\n" +
            "        \"limit\": \"10\"\n" +
            "    },\n" +
            "    \"path\": \"test\"\n" +
            "}";

    private static String val = "{\n" +
            "    \"codes\": [\n" +
            "        200,201\n" +
            "    ],\n" +
            "    \"headers\": {\n" +
            "        \"h1\": \"123\",\n" +
            "        \"h2\": \"abc\"\n" +
            "    },\n" +
            "    \"type\": \"json\",\n" +
            "    \"expressions\": [\n" +
            "        {\n" +
            "            \"condition\": \"contains\",\n" +
            "            \"path\": \"data\",\n" +
            "            \"value\": \"ge\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private static HttpRequest httpRequest = JSON.parseObject(req, HttpRequest.class);
    private static HttpValidation httpValidation = JSONObject.parseObject(val, HttpValidation.class);
}
