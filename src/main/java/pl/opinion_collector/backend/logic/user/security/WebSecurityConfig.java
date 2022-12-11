package pl.opinion_collector.backend.logic.user.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.opinion_collector.backend.logic.user.security.jwt.AuthEntryPointJwt;
import pl.opinion_collector.backend.logic.user.security.jwt.AuthTokenFilter;
import pl.opinion_collector.backend.logic.user.security.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

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
        final String[] ALL_USERS_PERMISSIONS = {
                "/**/users/login", "/**/users/register",
                "/**/products/**", "/**/products/search", "/**/products/details",
                "/**/opinions/product"
        };
        final String[] STD_USER_PERMISSIONS = {
                "/**/suggestions/user", "/**/suggestions/add", "/**/suggestions",
                "/**/opinions/user", "/**/opinions/add",
        };
        final String[] ADMIN_PERMISSIONS = {
                "/**/users", "/**/users/update",
                "/**/products/add", "/**/products/edit", "/**/products/delete",
                "/**/categories", "/**/categories/get", "/**/categories/add",
                "/**/categories/edit", "/**/categories/delete",
                "/**/suggestions/get", "/**/suggestions/reply"
        };
        final String[] SWAGGER_WHITELIST = {
                "/**/swagger-ui/**", "/**/swagger-ui/index.html",
                "/**/v2/api-docs", "/**/swagger-resources",
                "/**/swagger-resources/**", "/**/configuration/ui",
                "/**/configuration/security", "/**/swagger-ui.html",
                "/**/webjars/**", "/**/v3/api-docs/**", "/**/swagger-ui/**"
        };

        http
                .cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
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