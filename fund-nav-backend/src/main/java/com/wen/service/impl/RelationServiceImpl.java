package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.common.exception.BusinessException;
import com.wen.mapper.AccountInfoMapper;
import com.wen.mapper.FundHoldingMapper;
import com.wen.mapper.FundInfoMapper;
import com.wen.mapper.FundWatchlistMapper;
import com.wen.model.dto.FundHoldingDto;
import com.wen.model.dto.FundWatchlistDto;
import com.wen.model.entity.AccountInfo;
import com.wen.model.entity.FundHolding;
import com.wen.model.entity.FundInfo;
import com.wen.model.entity.FundWatchlist;
import com.wen.model.vo.HoldingResponse;
import com.wen.model.dto.FundNavDTO;
import com.wen.service.FundInfoService;
import com.wen.service.FundNavService;
import com.wen.service.RelationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : rjw
 * @date : 2026-03-31
 */
@Service
@Slf4j
@AllArgsConstructor
public class RelationServiceImpl implements RelationService {

    private final FundInfoService fundInfoService;

    private final FundNavService fundNavService;

    private final FundInfoMapper fundInfoMapper;

    private final AccountInfoMapper accountInfoMapper;

    private final FundHoldingMapper fundHoldingMapper;

    private final FundWatchlistMapper fundWatchlistMapper;

    /**
     * 获取用户自选基金
     */
    @Override
    public List<FundWatchlistDto> queryWatchlistList(long userId) {
        // 1. 获取用户自选基金
        LambdaQueryWrapper<FundWatchlist> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundWatchlist::getUserId, userId);
        List<FundWatchlist> watchlist = fundWatchlistMapper.selectList(wrapper);
        // 2. 提取基金代码
        Set<String> codeSet = watchlist.stream().map(FundWatchlist::getCode).collect(Collectors.toSet());
        // 3. 获取用户自选基金对应的基金信息
        List<FundInfo> fundList = fundInfoMapper.selectList(new LambdaQueryWrapper<FundInfo>()
                .in(FundInfo::getCode, codeSet));
        // 4. 构建基金信息
        List<FundWatchlistDto> responseList = buildWatchlistFunds(watchlist, fundList);
        log.info("获取用户自选基金: 基金数量: [{}]", responseList.size());
        return responseList;
    }

    private List<FundWatchlistDto> buildWatchlistFunds(List<FundWatchlist> watchlist, List<FundInfo> fundList) {
        List<FundWatchlistDto> responseList = new ArrayList<>();
        for (FundWatchlist item : watchlist) {
            for (FundInfo fundInfo : fundList) {
                if (Objects.equals(fundInfo.getCode(), item.getCode())) {
                    FundWatchlistDto resp = new FundWatchlistDto();
                    resp.setUserId(item.getUserId());
                    resp.setName(fundInfo.getName());
                    resp.setCode(fundInfo.getCode());
                    resp.setType(fundInfo.getType());
                    resp.setCompany(fundInfo.getCompany());
                    resp.setSection(fundInfo.getSection());
                    responseList.add(resp);
                }
            }
        }
        return responseList;
    }

    @Override
    public List<HoldingResponse> queryHoldingList(long userId) {
        // 1. 获取用户所有账户
        LambdaQueryWrapper<AccountInfo> accountWrapper = new LambdaQueryWrapper<>();
        accountWrapper.eq(AccountInfo::getUserId, userId);
        List<AccountInfo> accountList = accountInfoMapper.selectList(accountWrapper);
        // 2. 获取每个账户的持有基金
        List<HoldingResponse> responses = new ArrayList<>();
        for (AccountInfo accountInfo : accountList) {
            HoldingResponse holdingResponse = new HoldingResponse();
            List<FundHoldingDto> holdingList = getAccountTotalHoldingFunds(accountInfo);
            // 3. 注入净值数据
            enrichWithNav(holdingList);
            holdingResponse.setAccountInfo(accountInfo);
            holdingResponse.setHoldingList(holdingList);
            responses.add(holdingResponse);
        }
        log.info("获取用户持有基金: 基金信息: [{}]", responses);
        return responses;
    }

    private List<FundHoldingDto> getAccountTotalHoldingFunds(AccountInfo accountInfo) {
        LambdaQueryWrapper<FundHolding> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundHolding::getUserId, accountInfo.getUserId());
        wrapper.eq(FundHolding::getAccountId, accountInfo.getId());
        List<FundHolding> holdings = fundHoldingMapper.selectList(wrapper);
        // 2. 获取用户持有基金的代码
        Set<String> codeSet = holdings.stream().map(FundHolding::getCode).collect(Collectors.toSet());
        // 3. 获取用户持有基金对应的基金信息
        List<FundInfo> fundList = fundInfoMapper.selectList(new LambdaQueryWrapper<FundInfo>()
                .in(FundInfo::getCode, codeSet));
        // 4. 构建基金信息
        return buildHoldingFunds(holdings, fundList);
    }

    private List<FundHoldingDto> buildHoldingFunds(List<FundHolding> holdings, List<FundInfo> fundList) {
        List<FundHoldingDto> responseList = new ArrayList<>();
        for (FundHolding item : holdings) {
            for (FundInfo fundInfo : fundList) {
                if (Objects.equals(fundInfo.getCode(), item.getCode())) {
                    FundHoldingDto resp = new FundHoldingDto();
                    resp.setUserId(item.getUserId());
                    resp.setAccountId(item.getAccountId());
                    resp.setName(fundInfo.getName());
                    resp.setCode(fundInfo.getCode());
                    resp.setType(fundInfo.getType());
                    resp.setCompany(fundInfo.getCompany());
                    resp.setSection(fundInfo.getSection());
                    resp.setUnits(item.getUnits());
                    responseList.add(resp);
                }
            }
        }
        return responseList;
    }

    @Override
    public boolean existsWatchlistFund(long userId, String code) {
        LambdaQueryWrapper<FundWatchlist> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundWatchlist::getUserId, userId);
        wrapper.eq(FundWatchlist::getCode, code);
        Long count = fundWatchlistMapper.selectCount(wrapper);
        return count != null && count > 0;
    }

    @Override
    public boolean existsHoldingFund(long userId, long accountId, String code) {
        LambdaQueryWrapper<FundHolding> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundHolding::getUserId, userId);
        wrapper.eq(FundHolding::getAccountId, accountId);
        wrapper.eq(FundHolding::getCode, code);
        Long count = fundHoldingMapper.selectCount(wrapper);
        return count != null && count > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertWatchlistFund(FundWatchlistDto request) {
        if (request.getUserId() == null || request.getCode() == null) {
            log.error("InsertHoldingFund：关键参数存在空值, [{}]", request);
            throw new BusinessException("AddHoldingFund：关键参数存在空值");
        }
        log.info("新增自选基金：[{}]", request);
        // 1. 基金入库
        if (!fundInfoService.existsFundInfo(request.getCode())) {
            fundInfoService.insertFundInfo(request);
        }
        // 2. 写入用户基金关系表
        if (existsWatchlistFund(request.getUserId(), request.getCode())) {
            log.info("新增自选基金：用户自选基金关系已存在：[{}]", request);
            return;
        }
        // 3. 构建用户自选基金关系
        FundWatchlist fundWatchlist = new FundWatchlist();
        fundWatchlist.setUserId(request.getUserId());
        fundWatchlist.setCode(request.getCode());
        fundWatchlist.setCreateTime(System.currentTimeMillis());
        fundWatchlistMapper.insert(fundWatchlist);
        log.info("新增自选基金：新增用户基金关系：[{}]", fundWatchlist);
    }

    /**
     * 添加持有基金
     */
    @Override
    public void insertHoldingFund(FundHoldingDto request) {
        if (request.getUserId() == null || request.getAccountId() == null ||
                request.getCode() == null || request.getUnits() == null) {
            log.error("InsertHoldingFund：关键参数存在空值, [{}]", request);
            throw new BusinessException("AddHoldingFund：关键参数存在空值");
        }
        log.info("新增持有基金：[{}]", request);
        // 1. 基金入库
        if (!fundInfoService.existsFundInfo(request.getCode())) {
            fundInfoService.insertFundInfo(request);
        }
        // 2. 写入用户基金关系表
        if (existsHoldingFund(request.getUserId(), request.getAccountId(), request.getCode())) {
            log.info("新增持有基金：用户自持有基金关系已存在：[{}]", request);
            return;
        }
        // 3. 构建用户持有基金关系
        FundHolding fundHolding = new FundHolding();
        fundHolding.setUserId(request.getUserId());
        fundHolding.setAccountId(request.getAccountId());
        fundHolding.setUnits(request.getUnits());
        fundHolding.setCode(request.getCode());
        fundHolding.setUpdateTime(System.currentTimeMillis());
        fundHolding.setCreateTime(System.currentTimeMillis());
        fundHoldingMapper.insert(fundHolding);
        log.info("新增自选基金：新增用户基金关系：[{}]", fundHolding);
    }

    @Override
    public void deleteWatchlistFund(long userId, String code) {
        LambdaQueryWrapper<FundWatchlist> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundWatchlist::getUserId, userId);
        wrapper.eq(FundWatchlist::getCode, code);
        fundWatchlistMapper.delete(wrapper);
        log.info("DeleteWatchlistFund: 删除自选基金成功: [{}] [{}]", userId, code);
    }

    @Override
    public void deleteHoldingFund(long userId, long accountId, String code) {
        LambdaQueryWrapper<FundHolding> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundHolding::getUserId, userId);
        wrapper.eq(FundHolding::getAccountId, accountId);
        wrapper.eq(FundHolding::getCode, code);
        fundHoldingMapper.delete(wrapper);
        log.info("DeleteHoldingFund: 删除持有基金成功: [{}] [{}] [{}]", userId, accountId, code);
    }

    /**
     * 为持仓列表注入净值、市值、涨跌幅
     */
    private void enrichWithNav(List<FundHoldingDto> holdingList) {
        if (holdingList == null || holdingList.isEmpty()) {
            return;
        }
        for (FundHoldingDto holding : holdingList) {
            try {
                FundNavDTO nav = fundNavService.getFundNav(holding.getCode());
                if (nav == null) {
                    continue;
                }
                holding.setNavDate(nav.getJzrq());
                holding.setChangePercent(parseBigDecimal(nav.getGszzl()));
                // 净值：优先使用实时估值，回退到单位净值
                BigDecimal navValue = resolveNav(nav);
                holding.setNav(navValue);
                // 市值 = 份额 × 净值
                if (navValue != null && holding.getUnits() != null) {
                    holding.setMarketValue(holding.getUnits().multiply(navValue)
                            .setScale(2, RoundingMode.HALF_UP));
                }
            } catch (Exception e) {
                log.warn("enrich_nav_failed code={}", holding.getCode(), e);
            }
        }
    }

    private BigDecimal resolveNav(FundNavDTO nav) {
        String gsz = nav.getGsz();
        if (gsz != null && !gsz.isEmpty() && !"0.00".equals(gsz)) {
            return parseBigDecimal(gsz);
        }
        return parseBigDecimal(nav.getDwjz());
    }

    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}