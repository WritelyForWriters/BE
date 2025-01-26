package com.writely;

import com.writely.common.enums.exception.ParameterException;
import com.writely.common.exception.BaseException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
@Tag(name = "health-check")
public class HealthCheckController {

    @GetMapping
    public void healthCheck() {
    }

    @GetMapping("/error")
    public void error(@RequestParam String id) {
        throw new BaseException(ParameterException.PARAMETER_INVALID);
    }
}
