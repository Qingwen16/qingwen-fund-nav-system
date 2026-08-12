package com.wen.model.vo;

import com.wen.model.dto.FundHoldingDto;
import com.wen.model.entity.AccountInfo;
import lombok.Data;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-04-01
 */
@Data
public class HoldingResponse {

    private AccountInfo accountInfo;

    private List<FundHoldingDto> holdingList;

}
