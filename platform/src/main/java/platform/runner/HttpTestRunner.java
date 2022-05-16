package platform.runner;

import common.bean.request.HttpRequest;
import platform.entity.response.HttpResponse;
import platform.entity.validation.HttpValidation;

import java.util.Map;

public class HttpTestRunner extends TestRunner<HttpRequest, HttpResponse, HttpValidation> {

    @Override
    protected HttpRequest buildReplacedRequest(HttpRequest request, Map<String, String> variables) {
        return null;
    }

    @Override
    protected HttpValidation buildReplacedValidation(HttpValidation validation, Map<String, String> variables) {
        return null;
    }

    @Override
    protected HttpResponse execRequest(HttpRequest request) {
        return null;
    }
}
