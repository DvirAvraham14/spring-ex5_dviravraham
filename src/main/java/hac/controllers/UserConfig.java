package hac.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/*
    * This class represents a configuration for the user.
    * It is used to create users and to set their roles.
    * It also sets the password encoder.
    * It also sets the security filter chain.
    * It also sets the logout configuration.
 */
@Configuration
public class UserConfig {


    // Create users and set their roles
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("1")
                .password(bCryptPasswordEncoder.encode("1"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("2")
                .password(bCryptPasswordEncoder.encode("2"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("3")
                .password(bCryptPasswordEncoder.encode("3"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("a")
                .password(bCryptPasswordEncoder.encode("1"))
                .roles("ADMIN")
                .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Set the security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(withDefaults())

                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/css/**", "/403", "/error").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/expense/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/budget/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/**").hasRole("ADMIN")
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        //.loginProcessingUrl("/login")
                        //                        .defaultSuccessUrl("/", true)
                        //.failureUrl("/login?failure")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(
                        (exceptionHandling) -> exceptionHandling
                                .accessDeniedPage("/403")
                );

        return http.build();
    }

}
