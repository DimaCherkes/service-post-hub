package com.posthub.iam_service.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String firstPassword = encoder.encode("123");
        String secondPassword = encoder.encode("123");
        String thirdPassword = encoder.encode("123");

        System.out.println("First password: " + firstPassword);
        System.out.println("Second password: " + secondPassword);
        System.out.println("Third password: " + thirdPassword);

    }
}
