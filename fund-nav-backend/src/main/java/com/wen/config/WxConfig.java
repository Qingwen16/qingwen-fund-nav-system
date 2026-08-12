package com.wen.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxConfig {

    /**
     * 小程序 AppID
     */
    private String appId;

    /**
     * 小程序 AppSecret
     */
    private String appSecret;

}
