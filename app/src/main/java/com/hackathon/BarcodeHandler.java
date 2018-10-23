package com.hackathon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class BarcodeHandler extends Object {

    private static final Pattern TITLE_PATTERN = Pattern.compile("(.*)\\|\\s*upcitemdb.*");

    public static final String STORE_URL = "STORE_URL";
    public static final String PRODUCT_DETAIL = "PRODUCT_DETAIL";

    public String getHtmlString(String barcode) throws Exception {
        String url = String.format("https://www.upcitemdb.com/upc/%s", barcode);
        String html = Jsoup.connect(url).get().html();
        return html;
    }

    public Barcode getBarcodeProductDetails (String barcodeStr) throws IOException {
        Map<String, String> productDetails = new HashMap<String, String>();
        List<Map<String, String>> shoppingInfoList = new ArrayList<Map<String, String>>();
        String url = String.format("https://www.upcitemdb.com/upc/%s", barcodeStr);
        Document doc = Jsoup.connect(url).get();
        String productName;

        String title = doc.title();
        final Matcher matcher = TITLE_PATTERN.matcher(title);
        if (matcher.matches()) {
            productName = matcher.group(1);
        }
        else {
            productName = "N/A";
        }

        Elements productInfos = doc.select("#info").first().select("table > tbody > tr");
        for (Element ele : productInfos) {
            String key = ele.select("td:nth-child(1)").text().replaceAll(":", "");
            String value = ele.select("td:nth-child(2)").text();
            productDetails.put(key, value);
        }

        Elements shoppingInfo = doc.getElementsByAttributeValueContaining("title", "Go to");
        if (shoppingInfo != null && shoppingInfo.size() > 0) {
            for (Element element : shoppingInfo) {
                Map<String, String> shoppingInfoDetails = new HashMap<>();
                String storeUrl = element.attr("href");
                shoppingInfoDetails.put(STORE_URL, String.format("https://www.upcitemdb.com/%s", storeUrl));
                String storeName = element.text();
                shoppingInfoDetails.put("STORE_NAME", storeName);
                Elements nodes = element.parent().siblingElements();
                int count = 0;
                for (int i = 0; i < nodes.size(); i++) {
                    if (i == 0) {
                        shoppingInfoDetails.put(PRODUCT_DETAIL, nodes.get(i).text());
                    }
                    else if (i == 1) {
                        shoppingInfoDetails.put("PRODUCT_PRICE", nodes.get(i).text());
                    }
                    else {
                        shoppingInfoDetails.put("UPDATE_TIME", nodes.get(i).text());
                    }
                }
                shoppingInfoList.add(shoppingInfoDetails);
            }
        }
        return new Barcode(productName, url, productDetails, shoppingInfoList, barcodeStr);
    }

}

