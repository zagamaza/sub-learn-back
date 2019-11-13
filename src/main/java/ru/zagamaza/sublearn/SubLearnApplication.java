package ru.zagamaza.sublearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Locale;

@SpringBootApplication
@EnableFeignClients
public class SubLearnApplication {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("ru", "RU"));
        SpringApplication.run(SubLearnApplication.class, args);
    }

}
