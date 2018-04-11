package com.github.liaojiacan.spring.i18n.demo;

import com.github.liaojiacan.spring.i18n.demo.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@ComponentScan(value = "com.github.liaojiacan.spring.i18n.demo")
@Import(AppConfig.class)
public class Main {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
	}
}
