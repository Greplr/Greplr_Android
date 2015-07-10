/*
 * Greplr : A super-aggregator. One app to rule them all.
 *     Copyright (C) 2015  Greplr Team
 *     Where Greplr Team consists of :
 *       1. Arnav Gupta
 *       2. Abhinav Sinha
 *       3. Prempal Singh
 *       4. Raghav Apoorv
 *       5. Shubham Dokania
 *       6. Yogesh Balan
 *
 *     The source code of this program is confidential and proprietary. If you are not part of the
 *     Greplr Team (one of the above 6 named individuals) you should not be viewing this code.
 *
 *     You should immediately close your copy of code, and destory the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr.models.news;

import java.util.List;

/**
 * Created by championswimmer on 29/6/15.
 */
public class Feed {
    private String id;
    private List<FeedItem> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FeedItem> getItems() {
        return items;
    }

    public void setItems(List<FeedItem> items) {
        this.items = items;
    }

    public class FeedItem {
        String originId;
        List<String> keywords;
        String title;
        Subcontent content;
        long published;
        Subcontent summary;
        Visual visual;
        String Author;

        public String getOriginId() {
            return originId;
        }

        public void setOriginId(String originId) {
            this.originId = originId;
        }

        public List<String> getKeywords() {
            return keywords;
        }

        public void setKeywords(List<String> keywords) {
            this.keywords = keywords;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Subcontent getContent() {
            return content;
        }

        public void setContent(Subcontent content) {
            this.content = content;
        }

        public long getPublished() {
            return published;
        }

        public void setPublished(long published) {
            this.published = published;
        }

        public Subcontent getSummary() {
            return summary;
        }

        public void setSummary(Subcontent summary) {
            this.summary = summary;
        }

        public Visual getVisual() {
            return visual;
        }

        public void setVisual(Visual visual) {
            this.visual = visual;
        }

        public String getAuthor() {
            return Author;
        }

        public void setAuthor(String author) {
            Author = author;
        }

        public class Subcontent {
            String content;
            String direction;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }
        }

        public class Visual {
            String url;
            String contentType;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getContentType() {
                return contentType;
            }

            public void setContentType(String contentType) {
                this.contentType = contentType;
            }
        }

    }
}
