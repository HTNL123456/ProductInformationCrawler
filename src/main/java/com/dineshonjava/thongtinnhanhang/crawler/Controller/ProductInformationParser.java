/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dineshonjava.thongtinnhanhang.crawler.Controller;

import com.dineshonjava.thongtinnhanhang.crawler.POJO.ProductInformation;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
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
        if (productId != null) {
            productInformation.setProductId(productId.text());
            out.println("\nproductId: " + productInformation.getProductId());
        }

        //Parse productName
        Element productName = doc.selectFirst("h1.detail-product-name");
        if (productName != null) {
            productInformation.setProductName(productName.text());
            out.println("\nproductName: " + productInformation.getProductName());
        }

        //Parse productPrice
        Element productPrice = doc.selectFirst("div.detail-product-old-price");
        if (productPrice != null) {
            productInformation.setProductPrice(productPrice.text());
            out.println("\nproductPrice: " + productInformation.getProductPrice());
        }

        //Parse productDescription
        Element productDescription = doc.selectFirst("div.detail-product-desc div.detail-product-desc-content");
        if (productDescription != null) {
            productInformation.setProductDescription(productDescription.text());
            out.println("\nproductDesc: " + productInformation.getProductDescription());
        }

        //Parse productCatelogies   
        Elements productCatelogies = doc.select("div.tek-breadcrumb div.tek-breadcrumb-content");
        if (productCatelogies != null) {
            productCatelogies.remove(0);
            List<String> catelogies = new ArrayList<String>();
            for (Element productCatelogy : productCatelogies.select("a")) {
                String catelogy = productCatelogy.text();
                catelogies.add(catelogy);
            }

            for (String catelogy : catelogies) {
                out.println("\nList catelogy: " + catelogy);
            }

            productInformation.setProductCatelogies(catelogies);

//            for (String catelogy : productInformation.getProductCatelogies()) {
//                out.println("\nList catelogy: " + catelogy);
//            }
        }

        //Parse productImage
        Elements imageLinks = doc.select("div#myModal div.modal-content" /*img[src~=(?i)\\\\.(png|jpe?g|gif)]"*/);
        if (imageLinks != null) {
            List<String> links = new ArrayList<String>();
            for (Element imageLink : imageLinks) {
                String link = imageLink.select("div.mySlides img[src~=(?i)\\\\\\\\.(png|jpe?g|gif)]").attr("src");
                links.add(link);
            }
            for (String url : links) {
                out.println("\nList image url: " + url);
            }
            productInformation.setProductImages(links);
//            for(String url : productInformation.getProductImages()){
//                out.println("\nList image url: " + url);
//            }
        }

        //Parse productSpecification + productBrand
        Elements productSpecifications = doc.select("div.attributePopup div.modal-dialog div.modal-content div.modal-body table.attribute-table tbody");
        if (productSpecifications != null) {
            //Parse productBrand
            Element productBrand = productSpecifications.get(1).selectFirst("tr td.attribute-value");
            productInformation.setProductBrand(productBrand.text());

            //Specifications  
            HashMap<String, String> specifications = new HashMap<String, String>();
            for (Element row : productSpecifications.select("tr")) {
                String label = row.selectFirst("th.attribute-label").text();
                String value = row.selectFirst("td.attribute-value").text();
                specifications.put(label, value);
            }
            productInformation.setProductSpecifications(specifications);
        }

        return productInformation;
    }
}
