package com.wen.controller;

import cn.hutool.core.util.StrUtil;
import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.dto.FundHoldingDto;
import com.wen.model.dto.FundWatchlistDto;
import com.wen.model.vo.HoldingResponse;
import com.wen.service.RelationService;
import com.wen.utils.UserInfoContext;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基金关系管理 Controller
 *
 * @author jwruan
 * @date 2026-08-04
 */
@RestController
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;

    @GetMapping("/queryWatchlistList")
    public Response<List<FundWatchlistDto>> queryWatchlistList() {
        Long userId = UserInfoContext.getUserId();
        List<FundWatchlistDto> responses = relationService.queryWatchlistList(userId);
        return Response.success(responses);
    }

    @GetMapping("/queryHoldingList")
    public Response<List<HoldingResponse>> queryHoldingList() {
        Long userId = UserInfoContext.getUserId();
        List<HoldingResponse> responses = relationService.queryHoldingList(userId);
        return Response.success(responses);
    }

    @PostMapping("/insertWatchlistFund")
    public Response<?> insertWatchlistFund(@RequestBody FundWatchlistDto request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        request.setUserId(UserInfoContext.getUserId());
        relationService.insertWatchlistFund(request);
        return Response.success();
    }

    @PostMapping("/insertHoldingFund")
    public Response<?> insertHoldingFund(@RequestBody FundHoldingDto request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        request.setUserId(UserInfoContext.getUserId());
        relationService.insertHoldingFund(request);
        return Response.success();
    }

    @GetMapping("/deleteWatchlistFund")
    public Response<?> deleteWatchlistFund(@Param("code") String code) {
        if (StrUtil.isEmpty(code)) {
            throw new BusinessException("输入参数存在空值");
        }
        Long userId = UserInfoContext.getUserId();
        relationService.deleteWatchlistFund(userId, code);
        return Response.success();
    }

    @GetMapping("/deleteHoldingFund")
    public Response<?> deleteHoldingFund(@Param("accountId") Long accountId,
                                         @Param("code") String code) {
        if (accountId == null || StrUtil.isEmpty(code)) {
            throw new BusinessException("输入参数存在空值");
        }
        Long userId = UserInfoContext.getUserId();
        relationService.deleteHoldingFund(userId, accountId, code);
        return Response.success();
    }

}
