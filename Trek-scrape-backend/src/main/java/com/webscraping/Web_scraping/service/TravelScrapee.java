package com.webscraping.Web_scraping.service;

import com.webscraping.Web_scraping.model.Trek;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class TravelScrapee {

    public List<Trek> scrapeeWebsite() {
        List<Trek> results = new ArrayList<>();
        String url = "https://www.trekupindia.com/treks-in-karnataka";
        try {
            Document docs = Jsoup.connect(url).get();

            // Remove footers and unwanted sections from the main page
            docs.select("footer, .footer, #footer, .disclaimer, .copyright").remove();

            Elements places = docs.select(".elementor-post__card");

            ExecutorService executor = Executors.newFixedThreadPool(5);
            List<Future<Trek>> futures = new ArrayList<>();

            for (Element pls : places) {
                String name = pls.select("h2>a").text();
                String trekUrl = pls.select("a").attr("href");

                futures.add(executor.submit(() -> scrapeNestedPage(name, trekUrl)));
            }

            for (Future<Trek> future : futures) {
                results.add(future.get());
            }

            executor.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private Trek scrapeNestedPage(String name, String trekUrl) {
        String title = "";
        StringBuilder description = new StringBuilder();

        try {
            Document trekPage = Jsoup.connect(trekUrl).get();

            // Remove footers/unwanted sections from the nested page
            trekPage.select("footer, .footer, #footer, .disclaimer, .copyright").remove();

            title = trekPage.select("h1").text();

            // Select only paragraphs from main content
            Elements descElements = trekPage.select(".elementor-widget-text-editor p");
            for (Element p : descElements) {
                String text = p.text().trim();
                if (!text.isEmpty()) {
                    description.append(text).append("\n");
                }
            }

        } catch (Exception e) {
            description.append("Error fetching nested page: ").append(e.getMessage());
        }

        return new Trek(name, title, description.toString().trim());
    }
}
