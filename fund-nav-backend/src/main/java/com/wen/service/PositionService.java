package com.wen.service;

import com.wen.model.vo.PositionRequest;

/**
 * @author : rjw
 * @date : 2026-03-23
 */
public interface PositionService {

    /**
     * 修改持仓
     */
    void updatePosition(PositionRequest request);

    /**
     * 增加持仓
     */
    void increasePosition(PositionRequest request);

    /**
     * 减少持仓
     */
    void decreasePosition(PositionRequest request);
}
