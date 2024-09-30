package com.example.securitydemo.config.auth;

import com.example.securitydemo.model.enumclass.MemberRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
// @EnableWebSecurity(debug = true)
@EnableWebSecurity()
public class SecurityConfig {
    // private static final String[] AUTH_WHITELIST = {
    //        "/api/**", "/swagger*/**", "/api-docs", "/v3/api-docs/**", "/api-docs/**",
    //        "/check", "/join", "/h2-console/**", "/css/**", "/img/**", "/js/**", "/"
    // };
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
            .requestMatchers(
                new AntPathRequestMatcher("/api/**"), // 개발 중이어서 api test를 위해 열음. api/memeber/join는 회원 가입을 위해 열어두어야 할듯
                new AntPathRequestMatcher("/api-docs"),
                new AntPathRequestMatcher("/api-docs/**"),
                new AntPathRequestMatcher("/v3/api-docs/**"),
                new AntPathRequestMatcher("/swagger*/**"),
                new AntPathRequestMatcher("/swagger-resources/**"),
                new AntPathRequestMatcher("/check"),
                new AntPathRequestMatcher("/join"),
                new AntPathRequestMatcher("/h2-console/**"), // H2 Embeded DB
                new AntPathRequestMatcher("/css/**"),
                new AntPathRequestMatcher("/img/**"),
                new AntPathRequestMatcher("/js/**"),
                new AntPathRequestMatcher("/"))
            .permitAll()

            // 롤에 따른 권한 설정 어케해..
            /*.requestMatchers(
                new AntPathRequestMatcher("/admin/**"),
                new AntPathRequestMatcher("/admin"))
            .hasRole(MemberRole.ADMIN.toString())*/

            .anyRequest().authenticated())
            .csrf((csrf) -> csrf
                .ignoringRequestMatchers(
                    new AntPathRequestMatcher("/api/**"), // API 테스트용 (method:post 동작하려면 해줘야함)
                    new AntPathRequestMatcher("/api-docs"),
                    new AntPathRequestMatcher("/api-docs/**"),
                    new AntPathRequestMatcher("/v3/api-docs/**"),
                    new AntPathRequestMatcher("/login"), // 로그인 후 리다이렉션 풀어주는 용도 와이? (method:post 동작하려면 해줘야함
                    new AntPathRequestMatcher("/logout"), // 로그아웃 후 리다이렉션 풀어주는 용도 와이? (method:post 동작하려면 해줘야함
                    new AntPathRequestMatcher("/check"),
                    new AntPathRequestMatcher("/swagger*/**"),
                    new AntPathRequestMatcher("/swagger-resources/**"),
                    new AntPathRequestMatcher("/h2-console/**")// H2 Embeded DB
            ).disable()) // disable로 post method 사용 가능하도록 함. 개발 모드에서만 사용할 것
            .headers((headers) -> headers.addHeaderWriter(
                    new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
            .formLogin((formLogin) -> formLogin
                .loginPage("/login")
                .usernameParameter("account")
                .passwordParameter("password")
                // .loginProcessingUrl("/login")
                // .failureUrl("/error")
                .defaultSuccessUrl("/")
                .permitAll())
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll());

        return http.build();
    }
}