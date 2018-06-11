/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dineshonjava.thongtinnhanhang.crawler.POJO;

import com.mongodb.BasicDBObject;
import java.util.HashMap;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Huy Thinh
 */
public class ProductInformation {
    private String productId;
    private String productName;
    private Integer productPrice;
    private String productBrand;
    private List<String> productCatelogies;
    private List<String> productImages;
    private String productDescription;
    private HashMap<String, String> productSpecifications;
    private String productUrl;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public List<String> getProductCatelogies() {
        return productCatelogies;
    }

    public void setProductCatelogies(List<String> productCatelogies) {
        this.productCatelogies = productCatelogies;
    }

    public List<String> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<String> productImages) {
        this.productImages = productImages;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public HashMap<String, String> getProductSpecifications() {
        return productSpecifications;
    }

    public void setProductSpecifications(HashMap<String, String> productSpecifications) {
        this.productSpecifications = productSpecifications;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
    
    public Document getDocument(){
        Document document = new Document();
        document.append("productId", this.productId);
        document.append("productUrl",this.productUrl);
        document.append("productName", this.productName);
        document.append("productPrice", this.productPrice);
        document.append("productBrand", this.productBrand);
        document.append("productCatelogy", this.productCatelogies);
        document.append("productImage", this.productImages);
        document.append("productDescription", this.productDescription);
//        document.append("productSpecification", this.productSpecifications);
        
        BasicDBObject productDesc = new BasicDBObject(this.productSpecifications);
        document.append("productSpecification", productDesc);
               
        return document;
    }
}
