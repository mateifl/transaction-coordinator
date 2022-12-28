package ro.zizicu.transaction.coordinator.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RestError {
	private final String errorClass;
    private final String message;
    private final Integer code;
}
