package com.webscraping.Web_scraping;

import com.webscraping.Web_scraping.service.TravelScrapee;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebScrapingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebScrapingApplication.class, args);
	}
//    @Bean
//    public CommandLineRunner commandLineRunner(TravelScrapee thetravelScrapee)
//    {
//        return  run-> {
//            thetravelScrapee.scrapeeWebsite();
//        };
//    }
}
