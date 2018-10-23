package com.hackathon;

import java.util.Map;

public class Barcode {

    private Map<String, String> productDetails;
    private Map<String, String> shoppingInfoDetails;
    private String productName;
    private String barcodeUrl;

    Barcode(String productName, String barcodeUrl, Map<String, String> productDetails, Map<String, String> shoppingInfoDetails) {
        this.productName = productName;
        this.barcodeUrl = barcodeUrl;
        this.productDetails = productDetails;
        this.shoppingInfoDetails = shoppingInfoDetails;
    }

    public String productName() {
        return productName;
    }

    public String barcodeUrl() {
        return barcodeUrl;
    }

    public Map<String, String> productDetails() {
        return productDetails;
    }

    public Map<String, String> shoppingInfoDetails() {
        return shoppingInfoDetails;
    }
}
