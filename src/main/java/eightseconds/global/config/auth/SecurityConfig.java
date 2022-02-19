package eightseconds.global.config.auth;

import eightseconds.global.jwt.JwtAccessDeniedHandler;
import eightseconds.global.jwt.JwtAuthenticationEntryPoint;
import eightseconds.global.jwt.JwtSecurityConfig;
import eightseconds.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler successHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("victor")
                .password("{noop}oladipo")
                .roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resource/**", "/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/websocket").permitAll()
                .antMatchers("/websocket/websocket").permitAll()
                .antMatchers("/hup/**").permitAll()
                .antMatchers("/oauth2/authorization/google").permitAll()
                .antMatchers("/token/expired").permitAll()
                .antMatchers("/googleOAuth/tokenVerify").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/image/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/email/send").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/email/verify").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/notices").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/notices/{noticeId}").permitAll()
                .antMatchers("/api/v1/files/**").permitAll()
                .antMatchers("/api/v1/users/login").permitAll()
                .antMatchers("/api/v1/users/reissue").permitAll()
                .antMatchers("/api/v1/users/logout").permitAll()
                .antMatchers("/api/v1/users/google-login").permitAll()
                .antMatchers("/api/v1/users/kakao-login").permitAll()
                .antMatchers("/api/v1/users/naver-login").permitAll()
                .antMatchers("/api/v1/users/check/**").permitAll()
                .antMatchers("/api/v1/priceSuggestions/maximumPrice/**").permitAll()
                .antMatchers("/api/v1/priceSuggestions/participants/**").permitAll()
                .antMatchers("/api/v1/scraps/hearts/**").permitAll()
                .antMatchers("/api/v1/items/statuses/**").permitAll()
                .antMatchers("/api/fcm").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))
                .and()
                .oauth2Login().loginPage("/token/expired")
                .successHandler(successHandler)
                .userInfoEndpoint().userService(customOAuth2UserService);
    }
}
