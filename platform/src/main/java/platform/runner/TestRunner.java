package platform.runner;

import lombok.val;
import org.springframework.util.CollectionUtils;
import common.bean.request.BaseRequest;
import platform.entity.Expression;
import platform.entity.response.BaseResponse;
import platform.entity.validation.BaseValidation;
import platform.enums.ResponseDataTypeEnum;
import platform.exception.ValidateException;
import platform.utils.ValidateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TestRunner<Req extends BaseRequest, Resp extends BaseResponse, V extends BaseValidation> {
    private Map<String, String> variables = new HashMap<>();
    private Req request;
    private Req replacedRequest;
    private V validation;
    private V replacedValidation;
    private Resp response;
    private boolean success = false;

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
        before();
        replacedRequest = buildReplacedRequest(request, variables);
        replacedValidation = buildReplacedValidation(validation, variables);
        response = execRequest(replacedRequest);
        success = validate();
        after();
    }

    protected void before() {

    }

    protected abstract Req buildReplacedRequest(Req request, Map<String, String> variables);

    protected abstract V buildReplacedValidation(V validation, Map<String, String> variables);

    protected abstract Resp execRequest(Req request);

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
        } catch (ValidateException ex) {
            return false;
        }
        return true;
    }

}
