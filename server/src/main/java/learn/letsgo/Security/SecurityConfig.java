package learn.letsgo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
        // we're not using HTML forms in our app
        //so disable CSRF (Cross Site Request Forgery)
        http.csrf().disable();

        // this configures Spring Security to allow
        //CORS related requests (such as preflight checks)
        http.cors();

        // the order of the antMatchers() method calls is important
        // as they're evaluated in the order that they're added
        http.authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/refresh_token").authenticated()
                .antMatchers("/api/create_account").permitAll()
                .antMatchers("/api/message/*").authenticated()
                .antMatchers(HttpMethod.GET,"/api/event-post/post/*").permitAll()
                .antMatchers(HttpMethod.GET,"/api/event-post/*").permitAll()
                .antMatchers("/api/event-post").authenticated()
                .antMatchers("/api/event/user/contact/*/*/*").authenticated()
                .antMatchers("/api/event/user/group/*/*/*").authenticated()
                .antMatchers("/api/event/user/*").authenticated()
                .antMatchers("/api/event/saved/*").authenticated()
                .antMatchers("/api/event/user/*/*").authenticated()
                .antMatchers("/api/event/user/group/*/*/*").authenticated()
                .antMatchers("/api/contact").authenticated()
                .antMatchers("/api/contact/*").authenticated()
                .antMatchers("/api/contact/*/*").authenticated()
                .antMatchers("/api/group").authenticated()
                .antMatchers("/api/group/*").authenticated()
                .antMatchers("/api/group/*/*").authenticated()
                .antMatchers(HttpMethod.GET, "/api/venue").permitAll()
                .antMatchers(HttpMethod.GET, "/api/venue/*").permitAll()
                .antMatchers( "/api/venue").authenticated()
                .antMatchers( "/api/venue/*").authenticated()
                .antMatchers("/api/event/*").permitAll()
                // if we get to this point, let's deny all requests
                .antMatchers("/**").denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(authConfig), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
