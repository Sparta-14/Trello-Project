package com.spata14.trelloproject.config;

import com.spata14.trelloproject.config.filter.AuthFilter;
import com.spata14.trelloproject.config.interceptor.AdminInterceptor;
import com.spata14.trelloproject.user.repository.UserRepository;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    /**
     * 로그인 필터 등록
     */
    @Bean
    public FilterRegistrationBean loginFilter(UserRepository userRepository) {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter(userRepository));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    /**
     * 로그아웃 필터 등록
     */
    @Bean
    public FilterRegistrationBean logoutFilter(UserRepository userRepository) {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthFilter(userRepository));
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    private static final String[] ADMIN_REQUIRED_PATH_PATTERNS = {"/admin/**"};
    //private static final String[] USER_REQUIRED_PATH_PATTERNS = {"/user/**"};

    private final AdminInterceptor adminInterceptor;
    //private final UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns(ADMIN_REQUIRED_PATH_PATTERNS)
                .order(Ordered.HIGHEST_PRECEDENCE);

//        registry.addInterceptor(userInterceptor)
//                .addPathPatterns(USER_REQUIRED_PATH_PATTERNS)
//                .order(Ordered.HIGHEST_PRECEDENCE + 1);
    }
}
