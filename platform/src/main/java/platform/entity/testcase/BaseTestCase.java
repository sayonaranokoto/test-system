package platform.entity.testcase;

import common.bean.request.BaseRequest;
import platform.entity.validation.BaseValidation;
import lombok.Data;

/**
 * @author void
 */
@Data
public abstract class BaseTestCase<R extends BaseRequest,V extends BaseValidation> {
    private R request;
    private V validation;
}
