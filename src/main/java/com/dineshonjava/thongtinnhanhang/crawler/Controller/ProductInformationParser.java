/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dineshonjava.thongtinnhanhang.crawler.Controller;

import com.dineshonjava.thongtinnhanhang.crawler.POJO.ProductInformation;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Huy Thinh
 */
public class ProductInformationParser {
    public ProductInformation parseFromHTML(String html) {
        ProductInformation productInformation = new ProductInformation();
        Document doc = Jsoup.parse(html);
        
        productInformation.setProductId(doc.select(".detail-product-sku").text());
        productInformation.setProductName(doc.select("#object_id").val());
        feed.setCategory(doc.select(".detail-content .active a").text());
        feed.setPublishedDate(doc.select("meta[name=pubdate]").attr("content"));
        List<String> tags = new ArrayList<String>();
        Elements tagElements = doc.select(".block-key li a");
        for (Element tagElement : tagElements) {
            String tag = tagElement.text();
            tags.add(tag);
        }
        feed.setTags(tags);
        feed.setComments(commentParser.getComment(feed.getObjectId()));
        return feed;
    }
}
