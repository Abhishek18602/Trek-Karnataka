package com.webscraping.Web_scraping.model;

public class Trek {
        private String name;
//        private String url;
        private String title;
        private String description;

        public Trek() {}

        public Trek(String name, String title, String description) {
            this.name = name;
//            this.url = url;
            this.title = title;
            this.description = description;
        }

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }



        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }


