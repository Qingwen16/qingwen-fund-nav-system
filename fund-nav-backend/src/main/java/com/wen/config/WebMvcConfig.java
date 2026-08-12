package com.wen.config;

import com.wen.interceptor.RequestIdInterceptor;
import com.wen.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类
 * @author jwruan
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    private final RequestIdInterceptor requestIdInterceptor;

    /**
     * 拦截所有接口，排除登录接口
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/fund/valuation/auth/login");
        registry.addInterceptor(requestIdInterceptor)
                .addPathPatterns("/**");
    }
}
