package common.bean.util;

import common.bean.enums.HttpMethodEnum;
import common.bean.exception.ExecuteException;
import common.bean.exception.ExecuteExceptionEnum;
import common.bean.request.HttpRequest;
import common.bean.response.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    public static HttpResponse execute(HttpRequest httpRequest) throws ExecuteException {
        HttpResponse httpResponse = new HttpResponse();
        HttpUriRequest uriRequest = buildUriRequest(httpRequest);
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = client.execute(uriRequest);
            httpResponse.setCode(response.getStatusLine().getStatusCode());
            String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.toString());
            response.close();
            httpResponse.setBody(body);
            if (response.getAllHeaders() != null) {
                Map<String, String> map = new HashMap<>();
                for (Header header: response.getAllHeaders()) {
                    map.put(header.getName(), header.getValue());
                }
                httpResponse.setHeader(map);
            }
            return httpResponse;
        } catch (Exception ex) {
            throw new ExecuteException(ExecuteExceptionEnum.REQUEST_ERROR);
        }
    }

    private static HttpUriRequest buildUriRequest(HttpRequest httpRequest) throws ExecuteException {
        String domain = httpRequest.getDomain();
        StringBuilder url = new StringBuilder();
        if (domain.endsWith("/")) {
            url.append(domain, 0, domain.length() - 1);
        } else {
            url.append(domain);
        }

        String context = httpRequest.getContext();
        if (StringUtils.isNotBlank(context)) {
            if (context.startsWith("/")) {
                context = context.substring(1);
            }
            if (context.endsWith(context)) {
                context = context.substring(1, context.length() - 1);
            }
            url.append("/").append(context);
        }

        String path = httpRequest.getPath();
        if (StringUtils.isNotBlank(path)) {
            if (path.startsWith("/")) {
                path = path.substring(1, path.length() - 1);
            }
            url.append("/").append(path);
        }

        Map<String, String> parameters = httpRequest.getParameters();
        if (!CollectionUtils.isEmpty(parameters)) {
            boolean first = true;
            for (Map.Entry<String, String> entry: parameters.entrySet()) {
                if (first) {
                    first = false;
                    url.append("?");
                } else {
                    url.append("&");
                }
                try {
                    String keyEncoded = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString());
                    String valueEncoded = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
                    url.append(keyEncoded).append("=").append(valueEncoded);
                } catch (Exception ex) {
                    throw new ExecuteException(ExecuteExceptionEnum.EXECUTE_PARAM_ERROR);
                }
            }
        }

        HttpMethodEnum methodEnum = HttpMethodEnum.getMethod(httpRequest.getMethod());
        if (methodEnum == null) {
            throw new ExecuteException(ExecuteExceptionEnum.EXECUTE_PARAM_ERROR);
        }

        HttpRequestBase requestBase = null;
        switch (methodEnum) {
            case GET:
                requestBase = new HttpGet(url.toString());
                break;
            case POST:
                HttpEntity postEntity = buildRequestEntity(httpRequest.getBody());
                HttpPost httpPost = new HttpPost(url.toString());
                httpPost.setEntity(postEntity);
                requestBase = httpPost;
                break;
            case PUT:
                HttpEntity putEntity = buildRequestEntity(httpRequest.getBody());
                HttpPut httpPut = new HttpPut(url.toString());
                httpPut.setEntity(putEntity);
                requestBase = httpPut;
                break;
            case DELETE:
                requestBase = new HttpDelete(url.toString());
                break;
            default:
                throw new ExecuteException(ExecuteExceptionEnum.EXECUTE_PARAM_ERROR);
        }

        RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
        requestBase.setConfig(requestConfig);

        if (!CollectionUtils.isEmpty(httpRequest.getHeaders())) {
            for (Map.Entry<String, String> entry: httpRequest.getHeaders().entrySet()) {
                requestBase.setHeader(entry.getKey(), entry.getValue());
            }
        }

        return requestBase;
    }

    private static HttpEntity buildRequestEntity(String body) throws ExecuteException {
        try {
            return new ByteArrayEntity(body.getBytes(StandardCharsets.UTF_8.toString()));
        } catch (Exception ex) {
            throw new ExecuteException(ExecuteExceptionEnum.EXECUTE_PARAM_ERROR);
        }
    }
}
