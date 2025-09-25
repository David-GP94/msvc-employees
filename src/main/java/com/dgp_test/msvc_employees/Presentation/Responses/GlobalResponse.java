package com.dgp_test.msvc_employees.Presentation.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GlobalResponse<T> {
    private Boolean Success;
    private String Message;
    private T Data;
}
