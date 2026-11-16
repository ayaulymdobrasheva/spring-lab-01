package kz.iitu.springlab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Разрешаем обработку внутренних ошибок
                        .dispatcherTypeMatchers(
                                DispatcherType.ERROR,
                                DispatcherType.FORWARD
                        ).permitAll()

                        // Публичные страницы
                        .requestMatchers(
                                "/", "/login", "/error",
                                "/css/**", "/js/**"
                        ).permitAll()

                        // USER и ADMIN могут просматривать книги
                        .requestMatchers(
                                HttpMethod.GET,
                                "/books", "/books/**"
                        ).hasAnyRole("USER", "ADMIN")

                        // Редактирование владельцем или ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/books/*/edit"
                        ).hasAnyRole("USER", "ADMIN")

                        // Остальные изменения — только ADMIN
                        .requestMatchers(
                                "/books", "/books/**"
                        ).hasRole("ADMIN")

                        // Страницы администратора
                        .requestMatchers(
                                "/admin", "/admin/**"
                        ).hasRole("ADMIN")

                        .anyRequest().denyAll()
                )

                // Стандартная форма авторизации
                .formLogin(form -> form
                        .permitAll()
                )

                // Выход через POST с CSRF-токеном
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}