package kz.iitu.springlab;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        System.out.println("USER: " +
                encoder.encode("user123"));

        System.out.println("ADMIN: " +
                encoder.encode("admin123"));
    }
}