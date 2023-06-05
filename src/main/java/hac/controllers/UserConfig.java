package hac.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class UserConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user1")
                .password(bCryptPasswordEncoder.encode("password"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("user2")
                .password(bCryptPasswordEncoder.encode("password"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("user3")
                .password(bCryptPasswordEncoder.encode("password"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("password"))
                .roles("ADMIN")
                .build());
        manager.createUser(User.withUsername("useradmin")
                .password(bCryptPasswordEncoder.encode("password"))
                .roles("USER", "ADMIN")
                .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(withDefaults())

                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/static/**", "/static/css/**", "/", "/403", "/errorpage", "/simulateError").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")

                )
                .formLogin((form) -> form
                        //.loginPage("/login")
                               .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/", true)
                               .failureUrl("/login?failure")
                                .permitAll()
                )
                .logout((logout) -> logout.permitAll())
                .exceptionHandling(
                        (exceptionHandling) -> exceptionHandling
                                .accessDeniedPage("/403")
                );

        return http.build();

    }

}
