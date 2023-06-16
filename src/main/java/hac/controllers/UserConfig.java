package hac.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("password"))
                .roles("ADMIN")
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
                        .requestMatchers("/", "/css/**", "/403", "/errorpage").permitAll()
                        .requestMatchers("/expense/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/budget/**").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/admin/users").hasRole("ADMIN") // Allow only admin to access /admin/users
                        .requestMatchers("/**").hasRole("ADMIN")
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        //.loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/", true)
                        //.failureUrl("/login?failure")
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
