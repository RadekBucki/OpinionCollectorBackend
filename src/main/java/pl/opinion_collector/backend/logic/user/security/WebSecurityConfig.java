package pl.opinion_collector.backend.logic.user.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.opinion_collector.backend.logic.user.security.jwt.AuthEntryPointJwt;
import pl.opinion_collector.backend.logic.user.security.jwt.AuthTokenFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private static final String[] ALL_USERS_PERMISSIONS = {
            "/**/users/login", "/**/users/register",
            "/**/products/**", "/**/products/search", "/**/products/details",
            "/**/opinions/product"
    };
    private static final String[] STD_USER_PERMISSIONS = {
            "/**/suggestions/user", "/**/suggestions/add", "/**/suggestions",
            "/**/opinions/user", "/**/opinions/add",
    };
    private static final String[] ADMIN_PERMISSIONS = {
            "/**/users", "/**/users/update/**",
            "/**/products/add", "/**/products/edit", "/**/products/delete",
            "/**/categories", "/**/categories/get", "/**/categories/add",
            "/**/categories/edit", "/**/categories/delete",
            "/**/suggestions/get", "/**/suggestions/reply"
    };
    private static final String[] SWAGGER_WHITELIST = {
            "/**/swagger-ui/**", "/**/swagger-ui/index.html",
            "/**/v2/api-docs", "/**/swagger-resources",
            "/**/swagger-resources/**", "/**/configuration/ui",
            "/**/configuration/security", "/**/swagger-ui.html",
            "/**/webjars/**", "/**/v3/api-docs/**", "/**/swagger-ui/**"
    };

    @Bean
    public AuthEntryPointJwt getUnauthorizedHandler() {
        return new AuthEntryPointJwt();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(getUnauthorizedHandler()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                    .antMatchers(SWAGGER_WHITELIST).permitAll()
                    .antMatchers(ALL_USERS_PERMISSIONS).permitAll()
                    .antMatchers(STD_USER_PERMISSIONS).hasRole("USER")
                    .antMatchers(ADMIN_PERMISSIONS).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}