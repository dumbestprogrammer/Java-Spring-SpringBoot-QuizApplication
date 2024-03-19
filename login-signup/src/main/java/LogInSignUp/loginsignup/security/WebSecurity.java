package LogInSignUp.loginsignup.security;

import LogInSignUp.loginsignup.service.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurity {

    private Environment environment;

    private UsersService usersService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder,Environment environment) {
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.environment=environment;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // Configure AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);

        //-------------------------------------------------------------------------------------
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        AuthenticationFilter authenticationFilter= new AuthenticationFilter(usersService,environment,authenticationManager);
        authenticationFilter.setFilterProcessesUrl(("/users/login"));

        http.csrf((csrf) -> csrf.disable());
        http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/users", "POST"),
                                new AntPathRequestMatcher("/users/login", "POST")).permitAll()
                        .anyRequest().authenticated())


//				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .authenticationManager(authenticationManager)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configure session management
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())); // Configure frame options

        return http.build();

    }
}

