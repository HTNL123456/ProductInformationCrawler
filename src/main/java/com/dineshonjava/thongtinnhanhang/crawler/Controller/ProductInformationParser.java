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
        
        //Parse productId
        Element productId = doc.selectFirst("div.detail-product-sku b");
        productInformation.setProductId(productId.text());
        
        //Parse productName
        Element productName = doc.selectFirst("h3.detail-product-name");
        productInformation.setProductName(productName.text());

        //Parse productPrice
        Element productPrice = doc.selectFirst("div.detail-product-old-price");
        productInformation.setProductPrice(Integer.parseInt(productPrice.text()));
        
        //Parse productDescription
        Element productDescription = doc.selectFirst("div.detail-product-desc div.detail-product-desc-content");
        productInformation.setProductDescription(productDescription.text());
        
        //Parse productCatelogies   
        Elements productCatelogies = doc.select("div.tek-breadcrumb div.tek-breadcrumb-content a[title]");
        productCatelogies.first().remove();
        List<String> catelogies = new ArrayList<String>();
        for(Element productCatelogy: productCatelogies){
            String catelogy = productCatelogy.text();
            catelogies.add(catelogy);
        }
        productInformation.setProductCatelogies(catelogies);
        
        //Parse productImages
        Elements imageLinks = doc.select("div.detail-gallery-img-block div.detail-gallery-images img[src~=(?i)\\\\.(png|jpe?g|gif)]");
        
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
