package platform.runner;

import lombok.val;
import org.springframework.util.CollectionUtils;
import common.bean.request.BaseRequest;
import platform.entity.response.BaseResponse;
import platform.entity.validation.BaseValidation;

import java.util.HashMap;
import java.util.Map;

public abstract class TestRunner<R extends BaseRequest, V extends BaseValidation> {
    private Map<String, String> variables = new HashMap<>();
    private R request;
    private V validation;
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
        R realRequest = buildReplacedRequest();
        V realValidation = buildReplacedValidation();
        val response = execRequest();
        success = validate();
        after();
    }

    protected void before() {

    }

    protected abstract R buildReplacedRequest();

    protected abstract V buildReplacedValidation();

    protected abstract <Resp extends BaseResponse> Resp execRequest();

    protected void after() {

    }

    /**
     * 校验成功返回true
     * @return
     */
    protected boolean validate() {

        return true;
    }



}
