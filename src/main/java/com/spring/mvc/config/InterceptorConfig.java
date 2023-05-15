package com.spring.mvc.config;


import com.spring.mvc.interceptor.AfterLoginInterceptor;
import com.spring.mvc.interceptor.AutoLoginInterceptor;
import com.spring.mvc.interceptor.BoardInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//다양한 인터셉터 관련 설정을 등록하는 클래스
@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final BoardInterceptor boardInterceptor;

    private final AfterLoginInterceptor afterLoginInterceptor;

    private final AutoLoginInterceptor autoLoginInterceptor;

    // 인터셉터 만들고 config에 설정 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //게시판 인터셉터 설정
        registry.addInterceptor(boardInterceptor)
                .addPathPatterns("/board/*") // 어떤 경로에서 인터셉터를 실행할 것인가
                .excludePathPatterns("/board/list", "/board/detail")   // 인터셉터 실행 안 함
                ;

        //로그인 후처리 인터셉터 설정
        registry.addInterceptor(afterLoginInterceptor)
                .addPathPatterns("/members/sign-in","/members/sign-up");

        // 자동로그인 인터셉터 설정
        registry.addInterceptor(autoLoginInterceptor)
                .addPathPatterns("/**"); // 모든 경로에 접속했을 때 해당 인터셉터 돌아가게 처리
    }

}
