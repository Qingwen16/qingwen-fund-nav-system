package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wen.common.exception.BusinessException;
import com.wen.mapper.FundHoldingMapper;
import com.wen.model.vo.PositionRequest;
import com.wen.model.entity.FundHolding;
import com.wen.service.PositionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author : rjw
 * @date : 2026-03-23
 */
@Service
@Slf4j
@AllArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final FundHoldingMapper fundHoldingMapper;

    @Override
    public void updatePosition(PositionRequest request) {
        // 1. 参数校验
        checkPositionParams(request);
        // 2.修改参数
        updateFundHolding(request, request.getUnits());
        log.info("已成功改变持仓: 信息 [{}]", request);
    }

    @Override
    public void increasePosition(PositionRequest request) {
        // 1. 参数校验
        checkPositionParams(request);
        // 2. 查询当前持仓
        FundHolding fundHolding = queryFundHolding(request);
        // 3. 校验持仓是否存在
        if (fundHolding == null) {
            throw new BusinessException("持仓记录不存在");
        }
        // 4. 计算新份额
        BigDecimal newShares = fundHolding.getUnits().add(request.getUnits());
        // 5. 更新持仓份额
        updateFundHolding(request, newShares);
        log.info("已成功改变持仓: 信息 [{}], 更新份额 [{}]", request, newShares);
    }

    @Override
    public void decreasePosition(PositionRequest request) {
        // 1. 参数校验
        checkPositionParams(request);
        // 2. 查询当前持仓
        FundHolding fundHolding = queryFundHolding(request);
        // 3. 校验持仓是否存在
        if (fundHolding == null) {
            throw new BusinessException("持仓记录不存在");
        }
        // 4. 计算新份额
        BigDecimal newShares = fundHolding.getUnits().subtract(request.getUnits());
        if (newShares.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("减仓份额超过当前持仓，操作失败");
        }
        // 5. 更新持仓份额
        updateFundHolding(request, newShares);
        log.info("已成功改变持仓: 信息 [{}], 更新份额 [{}]", request, newShares);
    }

    private void checkPositionParams(PositionRequest request) {
        if (request.getUserId() == null) {
            throw new BusinessException("[Position]: 参数的用户ID不能为空");
        }
        if (request.getAccountId() == null) {
            throw new BusinessException("[Position]: 参数的账户ID不能为空");
        }
        if (request.getCode() == null) {
            throw new BusinessException("[Position]: 参数的基金代码不能为空");
        }
        if (request.getUnits() == null) {
            throw new BusinessException("[Position]: 参数的份额参数不能为空");
        }
    }

    private FundHolding queryFundHolding(PositionRequest request) {
        LambdaQueryWrapper<FundHolding> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FundHolding::getUserId, request.getUserId());
        queryWrapper.eq(FundHolding::getAccountId, request.getAccountId());
        queryWrapper.eq(FundHolding::getCode, request.getCode());
        return fundHoldingMapper.selectOne(queryWrapper);
    }

    private void updateFundHolding(PositionRequest request, BigDecimal newShares) {
        LambdaUpdateWrapper<FundHolding> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(FundHolding::getUserId, request.getUserId());
        updateWrapper.eq(FundHolding::getAccountId, request.getAccountId());
        updateWrapper.eq(FundHolding::getCode, request.getCode());
        updateWrapper.set(FundHolding::getUnits, newShares);
        fundHoldingMapper.update(null, updateWrapper);
    }
}
