package com.wen.model.dto;

import lombok.Data;

/**
 * 天天基金实时估值返回数据
 *
 * @author jwruan
 * @date 2026-08-04
 */
@Data
public class FundNavDTO {

    /**
     * 基金代码
     */
    private String fundcode;

    /**
     * 基金名称
     */
    private String name;

    /**
     * 净值日期 (yyyy-MM-dd)
     */
    private String jzrq;

    /**
     * 单位净值（昨日确认）
     */
    private String dwjz;

    /**
     * 估算净值（实时，交易时段有效）
     */
    private String gsz;

    /**
     * 估算涨幅百分比
     */
    private String gszzl;

    /**
     * 估值时间 (yyyy-MM-dd HH:mm:ss)
     */
    private String gztime;

}
