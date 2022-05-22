package common.bean.response;

import lombok.Data;

@Data
public abstract class BaseResponse<T> {
    private T body;
}
