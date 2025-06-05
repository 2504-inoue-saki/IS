package com.example.IS.config;

import com.example.IS.filter.AdminFilter;
import com.example.IS.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class FilterConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LoginFilter());
        //ログイン情報が必要なURL
        bean.addUrlPatterns("/");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter() {
        FilterRegistrationBean<AdminFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AdminFilter());
        //管理者権限が必要なURL
        bean.addUrlPatterns("/userAdmin");
        bean.addUrlPatterns("/signup");
        bean.addUrlPatterns("/userEdit");
        bean.setOrder(2);
        return bean;
    }
}
