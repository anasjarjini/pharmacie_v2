package ma.anas.pharmacie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/access-denied").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/ventes/**").hasAnyRole("ADMIN", "PHARMACIEN", "CAISSIER")
                        .requestMatchers(HttpMethod.POST, "/api/**", "/medicaments/**").hasAnyRole("ADMIN", "PHARMACIEN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("ADMIN", "PHARMACIEN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("ADMIN", "PHARMACIEN")
                        .requestMatchers(HttpMethod.GET, "/medicaments/**", "/api/**")
                        .hasAnyRole("ADMIN", "PHARMACIEN", "CLIENT")
                        .anyRequest().authenticated())
                .formLogin(f -> f.loginPage("/login").defaultSuccessUrl("/home", true).permitAll())
                .logout(l -> l.logoutSuccessUrl("/login?logout").permitAll())
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(e -> e.accessDeniedPage("/access-denied"));
        return http.build();
    }
}