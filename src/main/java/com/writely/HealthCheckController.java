package com.writely;

import com.writely.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
@Tag(name = "health-check")
public class HealthCheckController {

    @GetMapping
    public BaseResponse<Void> healthCheck() {
        return BaseResponse.success();
    }
}
