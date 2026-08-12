package com.wen.controller;

import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.vo.PositionRequest;
import com.wen.service.PositionService;
import com.wen.utils.UserInfoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 持仓操作 Controller
 *
 * @author jwruan
 * @date 2026-08-04
 */
@RestController
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @PostMapping("/updatePosition")
    public Response<?> updatePosition(@RequestBody PositionRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        request.setUserId(UserInfoContext.getUserId());
        positionService.updatePosition(request);
        return Response.success();
    }

    @PostMapping("/increasePosition")
    public Response<?> increasePosition(@RequestBody PositionRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        request.setUserId(UserInfoContext.getUserId());
        positionService.increasePosition(request);
        return Response.success();
    }

    @PostMapping("/decreasePosition")
    public Response<?> decreasePosition(@RequestBody PositionRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        request.setUserId(UserInfoContext.getUserId());
        positionService.decreasePosition(request);
        return Response.success();
    }

}
