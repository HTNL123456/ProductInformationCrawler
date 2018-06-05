/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dineshonjava.thongtinnhanhang.crawler.POJO;

import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Huy Thinh
 */
public class ProductInformation {
    private String productId;
    private String productName;
    private Double productPrice;
    private String productBrand;
    private String productCatelogy;
    private List<String> productImages;
    private List<String> productDescriptions;
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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductCatelogy() {
        return productCatelogy;
    }

    public void setProductCatelogy(String productCatelogy) {
        this.productCatelogy = productCatelogy;
    }
    
    public List<String> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<String> productImages) {
        this.productImages = productImages;
    }

    public List<String> getProductDescriptions() {
        return productDescriptions;
    }

    public void setProductDescriptions(List<String> productDescriptions) {
        this.productDescriptions = productDescriptions;
    }

    public HashMap<String, String> getProductSpecifications() {
        return productSpecifications;
    }

    public void setProductSpecification(HashMap<String, String> productSpecifications) {
        this.productSpecifications = productSpecifications;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
    
    public org.bson.Document getDocument(){
        org.bson.Document document = new org.bson.Document();
        document.append("productId", this.productId);
        document.append("productUrl",this.productUrl);
        document.append("productName", this.productName);
        document.append("productPrice", this.productPrice);
        document.append("productBrand", this.productBrand);
        document.append("productCatelogy", this.productCatelogy);
        document.append("productImages", this.productImages);
        document.append("productDescriptions", this.productDescriptions);
        document.append("productSpecifications", this.productSpecifications);
          
        return document;
    }
}
