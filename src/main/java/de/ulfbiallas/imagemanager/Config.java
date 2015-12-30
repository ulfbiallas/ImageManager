package de.ulfbiallas.imagemanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan({"de.ulfbiallas.imagemanager.resource", "de.ulfbiallas.imagemanager.service"})
public class Config extends WebMvcConfigurerAdapter {

    @Bean
    public StandardServletMultipartResolver multipartResolver(){
        return new StandardServletMultipartResolver();
    }

}
