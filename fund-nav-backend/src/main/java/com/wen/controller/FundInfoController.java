package com.wen.controller;

import cn.hutool.core.util.StrUtil;
import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.entity.FundInfo;
import com.wen.service.FundInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

/**
 * @author : rjw
 * @date : 2026-03-31
 */
@RestController
@RequiredArgsConstructor
public class FundInfoController {

    private final FundInfoService fundInfoService;

    /**
     * 查询基金信息
     *
     * @param code 基金代码
     * @return 用户信息
     */
    @GetMapping("/queryFundInfo")
    public Response<FundInfo> queryFundInfo(@Param("code") String code) {
        if (StrUtil.isEmpty(code)) {
            throw new BusinessException("输入参数不能为空");
        }
        FundInfo responses = fundInfoService.queryFundInfo(code);
        return Response.success(responses);
    }

}
