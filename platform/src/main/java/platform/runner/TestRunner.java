package platform.runner;

import com.alibaba.fastjson.JSONObject;
import common.bean.enums.HttpMethodEnum;
import common.bean.enums.ProtocolEnum;
import common.bean.exception.ExecuteException;
import common.bean.exception.ExecuteExceptionEnum;
import common.bean.request.HttpRequest;
import common.bean.response.HttpResponse;
import common.bean.util.HttpUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import common.bean.request.BaseRequest;
import platform.entity.Expression;
import common.bean.response.BaseResponse;
import platform.entity.validation.BaseValidation;
import common.bean.exception.TestException;
import common.bean.exception.TestExceptionEnum;
import platform.utils.ValidateUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TestRunner<Req extends BaseRequest, Resp extends BaseResponse, V extends BaseValidation> {
    private Map<String, String> variables = new HashMap<>();

    @Getter
    @Setter
    private Req request;

    @Getter
    private Req replacedRequest;

    @Getter
    @Setter
    private V validation;

    @Getter
    private V replacedValidation;

    @Getter
    private Resp response;

    @Getter
    @Setter
    private String agentAdress = "http://localhost";

    @Getter
    @Setter
    private long agentPort = 8080L;

    private String path = "exec";

    private boolean success = false;

    protected static final String format = "\\{\\{%s\\}\\}";
    protected static final Pattern pattern = Pattern.compile("\\{\\{([a-zA-Z]+[a-zA-Z0-9\\._\\-]*)\\}\\}");

    protected Map<String, String> getVariables() {
        return variables;
    }

    protected Map<String, String> addVariable(String key, String value) {
        variables.put(key, value);
        return variables;
    }

    protected Map<String, String> addVariables(Map<String, String> map) {
        if (!CollectionUtils.isEmpty(map)) {
            variables.putAll(map);
        }
        return variables;
    }

    public void run() {
        try {
            before();
            replacedRequest = buildReplacedRequest(request, variables);
            replacedValidation = buildReplacedValidation(validation, variables);
            ProtocolEnum protocolEnum = getProtocol();
            response = execRequest(protocolEnum.getProtocol(), replacedRequest);
            success = validate();
            after();
        } catch (TestException ex) {

        } catch (Exception ex) {

        }

    }

    public abstract ProtocolEnum getProtocol();

    protected void before() {

    }

    protected Req buildReplacedRequest(Req request, Map<String, String> variables) throws TestException {
        String json = JSONObject.toJSONString(request);
        String relacedJson = replace(json, variables);
        List<String> missingConfigs = findMissingConfigs(relacedJson, variables);
        if (!CollectionUtils.isEmpty(missingConfigs)) {
            throw new TestException(TestExceptionEnum.MISSING_CONFIG);
        }
        Class clz = request.getClass();
        return JSONObject.parseObject(relacedJson, (Type) clz);
    }

    protected V buildReplacedValidation(V validation, Map<String, String> variables) throws TestException {
        String json = JSONObject.toJSONString(validation);
        String relacedJson = replace(json, variables);
        List<String> missingConfigs = findMissingConfigs(relacedJson, variables);
        if (!CollectionUtils.isEmpty(missingConfigs)) {
            throw new TestException(TestExceptionEnum.MISSING_CONFIG);
        }
        Class clz = validation.getClass();
        return JSONObject.parseObject(relacedJson, (Type) clz);
    }

    protected Resp execRequest(String protocol, Req request) throws ExecuteException {
        HttpRequest httpRequest = new HttpRequest();
        String domain = agentAdress + ":" + agentPort + "/" + path + "/" + protocol;
        httpRequest.setDomain(domain);
        httpRequest.setMethod(HttpMethodEnum.POST.toString());
        httpRequest.setBody(JSONObject.toJSONString(request));
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        httpRequest.setHeaders(headers);
        HttpResponse httpResponse = HttpUtils.execute(httpRequest);
        if (httpResponse == null || StringUtils.isBlank(httpResponse.getBody())) {
            throw new ExecuteException(ExecuteExceptionEnum.REQUEST_ERROR);
        }
        return toResp(httpResponse.getBody());
    }

    protected abstract Resp toResp(String str);

    protected void after() {

    }

    /**
     * 校验成功返回true
     * @return
     */
    protected boolean validate() {
        if (CollectionUtils.isEmpty(validation.getExpressions())) {
            return true;
        }
        if (response == null || response.getBody() == null) {
            return false;
        }
        try {
            for (Expression expression: validation.getExpressions()) {
                ValidateUtils.validate(validation.getType(), response.getBody(), expression);
            }
        } catch (TestException ex) {
            return false;
        }
        return true;
    }

    protected static String replace(String json, Map<String, String> map) {
        String temp = json;
        for (Map.Entry<String, String> entry: map.entrySet()){
            temp = temp.replaceAll(String.format(format, entry.getKey()), entry.getValue());
        }
        return temp;
    }

    protected static List<String> findMissingConfigs(String json, Map<String, String> map) {
        List<String> missingConfigs = new ArrayList<>();
        Matcher matcher = pattern.matcher(json);
        while (matcher.find()) {
            String temp = matcher.group(1);
            if (!map.containsKey(temp)) {
                missingConfigs.add(temp);
            }
        }
        return missingConfigs;
    }

    public static void main(String[] args) {
        String str = "{{a123}}{{abc}}";
        Map<String, String> map = new HashMap<>();
        map.put("a123", "111");
        List list = findMissingConfigs(str, map);
    }

}
