package platform.entity.validation;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author void
 */
@Data
public class HttpValidation extends BaseValidation {
    private List<String> codes;
    private Map<String, String> headers;
}
