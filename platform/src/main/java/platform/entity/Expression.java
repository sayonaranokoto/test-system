package platform.entity;

import lombok.Data;

@Data
public class Expression {
    private String condition;
    private String path;
    private String value;
}
