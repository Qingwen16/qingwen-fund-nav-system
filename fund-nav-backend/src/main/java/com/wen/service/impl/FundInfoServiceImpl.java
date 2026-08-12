package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.mapper.FundInfoMapper;
import com.wen.model.dto.FundHoldingDto;
import com.wen.model.dto.FundWatchlistDto;
import com.wen.model.entity.FundInfo;
import com.wen.service.FundInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FundInfoServiceImpl implements FundInfoService {

    private final FundInfoMapper fundInfoMapper;

    @Override
    public FundInfo queryFundInfo(String code) {
        LambdaQueryWrapper<FundInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundInfo::getCode, code);
        FundInfo fundInfo = fundInfoMapper.selectOne(wrapper);
        log.info("QueryFundInfo: 查询到基金信息: [{}]", fundInfo);
        return fundInfo;
    }

    @Override
    public boolean existsFundInfo(String code) {
        LambdaQueryWrapper<FundInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundInfo::getCode, code);
        Long count = fundInfoMapper.selectCount(wrapper);
        return count != null && count > 0;
    }

    @Override
    public void insertFundInfo(FundWatchlistDto request) {
        FundInfo fundInfo = new FundInfo();
        fundInfo.setName(request.getName());
        fundInfo.setCode(request.getCode());
        fundInfo.setType(request.getType());
        fundInfo.setCompany(request.getCompany());
        fundInfo.setSection(request.getSection());
        fundInfo.setCreateTime(System.currentTimeMillis());
        fundInfoMapper.insert(fundInfo);
        log.info("新增基金：FundInfoDto：[{}]", request);
    }

    @Override
    public void insertFundInfo(FundHoldingDto request) {
        FundInfo fundInfo = new FundInfo();
        fundInfo.setName(request.getName());
        fundInfo.setCode(request.getCode());
        fundInfo.setType(request.getType());
        fundInfo.setCompany(request.getCompany());
        fundInfo.setSection(request.getSection());
        fundInfo.setCreateTime(System.currentTimeMillis());
        fundInfoMapper.insert(fundInfo);
        log.info("新增基金：FundInfoDto：[{}]", request);
    }

    @Override
    public void deleteFundInfo(String code) {
        LambdaQueryWrapper<FundInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundInfo::getCode, code);
        fundInfoMapper.delete(wrapper);
        log.info("删除基金：code：[{}]", code);
    }

}
