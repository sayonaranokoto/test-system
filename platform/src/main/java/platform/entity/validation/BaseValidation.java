package platform.entity.validation;

import platform.entity.Expression;
import lombok.Data;

import java.util.List;

@Data
public class BaseValidation {
    private String type;
    private List<Expression> expressions;
}
