package com.webscraping.Web_scraping.controller;

import com.webscraping.Web_scraping.model.Trek;
import com.webscraping.Web_scraping.service.TravelScrapee;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/scrapee")
public class TravelScrapeeController {

//    inject here first
    private  final TravelScrapee travelScrapee;
    public  TravelScrapeeController(TravelScrapee travelScrapee1)
    {
        this.travelScrapee=travelScrapee1;
    }

    @GetMapping("/data")
    public List<Trek> scrapeeInfo()
    {
        return travelScrapee.scrapeeWebsite();
    }
}
