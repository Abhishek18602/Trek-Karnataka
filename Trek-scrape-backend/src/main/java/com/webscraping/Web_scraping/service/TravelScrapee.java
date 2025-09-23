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
import java.util.stream.Collectors;

@Service
public class TravelScrapee {

    private final ExecutorService executor = Executors.newFixedThreadPool(20); // increase threads

    public List<Trek> scrapeeWebsite() {
        List<Trek> results = new ArrayList<>();
        String url = "https://www.trekupindia.com/treks-in-karnataka";

        try {
            Document docs = Jsoup.connect(url).get();

            // Remove unwanted sections
            docs.select("footer, .footer, #footer, .disclaimer, .copyright").remove();

            Elements places = docs.select(".elementor-post__card");

            // Create async tasks for nested pages
            List<CompletableFuture<Trek>> futures = places.stream()
                    .map(pls -> {
                        String name = pls.select("h2>a").text();
                        String trekUrl = pls.select("a").attr("href");
                        return CompletableFuture.supplyAsync(() -> scrapeNestedPage(name, trekUrl), executor);
                    })
                    .collect(Collectors.toList());

            // Wait for all tasks to finish
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            // Collect results
            for (CompletableFuture<Trek> f : futures) {
                try {
                    results.add(f.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

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

            // Remove footers/unwanted sections
            trekPage.select("footer, .footer, #footer, .disclaimer, .copyright").remove();

            title = trekPage.select("h1").text();

            // Limit to first 3 paragraphs for speed
            Elements descElements = trekPage.select(".elementor-widget-text-editor p");
            int count = 0;
            for (Element p : descElements) {
                if (count++ >= 3) break; // only take first 3 paragraphs
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
