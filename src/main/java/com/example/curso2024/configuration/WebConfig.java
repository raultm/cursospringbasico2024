package com.example.curso2024.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@Configuration
public class WebConfig {

    // @Bean
    // public LocaleResolver localeResolver() {
    // AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    // localeResolver.setDefaultLocale(Locale.forLanguageTag("de"));
    // return localeResolver;
    // }

    @Bean
    public LocaleResolver localeResolver() {
        return new FixedLocaleResolver(Locale.CHINESE);
    }

}