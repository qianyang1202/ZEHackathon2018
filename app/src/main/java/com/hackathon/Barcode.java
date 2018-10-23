package com.hackathon;

import java.util.List;
import java.util.Map;

public class Barcode {

    private Map<String, String> productDetails;
    private List<Map<String, String>> shoppingInfoDetails;
    private String productName;
    private String barcodeUrl;
    private String barcode;

    Barcode(String productName, String barcodeUrl, Map<String, String> productDetails, List<Map<String, String>> shoppingInfoDetail, String barcode) {
        this.productName = productName;
        this.barcodeUrl = barcodeUrl;
        this.productDetails = productDetails;
        this.shoppingInfoDetails = shoppingInfoDetail;
        this.barcode = barcode;
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

    public List<Map<String, String>> shoppingInfoDetails() {
        return shoppingInfoDetails;
    }

    public String getBarcode() {
        return barcode;
    }
}