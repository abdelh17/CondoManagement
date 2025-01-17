package com.rently.rentlyAPI.security.config;

import com.rently.rentlyAPI.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static com.rently.rentlyAPI.security.Permission.*;
import static com.rently.rentlyAPI.security.Role.COMPANY_ADMIN;
import static com.rently.rentlyAPI.security.Role.SYSTEM_ADMIN;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableMethodSecurity //Enables @PreAuthroize annotation
public class WebSecurityConfig {

//    @Autowired
//    private RentlyOAuth2UserService oAuth2UserService;
//
//    @Autowired
//    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private static final String[] WHITE_LIST_URL = {
//        "/api/system-admin/**",
//            "/api/company-admin/**",
            "api/system-admin/create/system-admin",
            "/api/public-user/**",
            "http://localhost:8080/api/v1/auth/**",
            "/api/authentication/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/api/v1/company/**").hasAnyRole(SYSTEM_ADMIN.name(), COMPANY_ADMIN.name())
                                .requestMatchers(GET, "/api/v1/company/**").hasAnyAuthority(SYSTEM_ADMIN_READ.name(), COMPANY_ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/company/**").hasAnyAuthority(SYSTEM_ADMIN_CREATE.name(), COMPANY_ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/company/**").hasAnyAuthority(SYSTEM_ADMIN_UPDATE.name(), COMPANY_ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/company/**").hasAnyAuthority(SYSTEM_ADMIN_DELETE.name(), COMPANY_ADMIN_DELETE.name())

                                .requestMatchers("/api/v1/admin/**").hasRole(SYSTEM_ADMIN.name())

                                .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(SYSTEM_ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(SYSTEM_ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(SYSTEM_ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(SYSTEM_ADMIN_DELETE.name())

                                .requestMatchers("/api/company-admin/**").hasAnyRole(COMPANY_ADMIN.name())
                                .requestMatchers(GET, "/api/company-admin/**").hasAnyAuthority(COMPANY_ADMIN_READ.name())
                                .requestMatchers(POST, "/api/company-admin/**").hasAnyAuthority(COMPANY_ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/company-admin/**").hasAnyAuthority(COMPANY_ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/company-admin/**").hasAnyAuthority(COMPANY_ADMIN_DELETE.name())


                                .anyRequest()
                                .authenticated()
                )
//                .oauth2Login(oauth2Login -> oauth2Login
////                    TODO: Do we need this (below)?
////                    .loginPage("http://localhost:5173/login")
////                    TODO: change sucess redirect endpoint
////                    .defaultSuccessUrl("http://localhost:5173/register")
//                    .failureUrl("/api/v1/auth/login-failure")
//                    .successHandler(oAuth2LoginSuccessHandler)
//                    .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
//                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )

        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:3000", "http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Access-Control-Allow-Headers", "X-Requested-With"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
