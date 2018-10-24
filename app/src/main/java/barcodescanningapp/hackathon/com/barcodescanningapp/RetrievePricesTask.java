package barcodescanningapp.hackathon.com.barcodescanningapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hackathon.Barcode;
import com.hackathon.BarcodeHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.hackathon.BarcodeHandler.PRODUCT_DETAIL;
import static com.hackathon.BarcodeHandler.STORE_URL;

public class RetrievePricesTask extends AsyncTask {

    private PricesResultsActivity pricesResultsActivity;
    private ListView listDetailsView;
    private ListView listShoppingView;
    private PricesResultsAdapter pricesResultsAdapter;
    private String barcode;
    private Context mContext;

    @Override
    protected Object doInBackground(Object[] objects) {
        barcode = (String) objects[0];
        pricesResultsActivity = (PricesResultsActivity) objects[1];
        mContext = (Context) objects[2];

        BarcodeHandler barcodeHandler = new BarcodeHandler();
        Barcode bc = null;
        try {
            bc = barcodeHandler.getBarcodeProductDetails(barcode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bc;
    }

    @Override
    protected void onPostExecute(Object o){
        final Barcode bc = (Barcode) o;
        TextView productNameTextView =  pricesResultsActivity.findViewById(R.id.product_name);
        productNameTextView.setText(bc.productName());

        listDetailsView = pricesResultsActivity.findViewById(R.id.properties_display);

        List<Map.Entry<String, String>> productDetailsList = new ArrayList<>();

        Set<Map.Entry<String, String>> productDetailsPropsSet = bc.productDetails().entrySet();

        for (Map.Entry<String, String> prop : productDetailsPropsSet) {
            productDetailsList.add(prop);
        }

        pricesResultsAdapter = new PricesResultsAdapter(pricesResultsActivity, productDetailsList);
        listDetailsView.setAdapter(pricesResultsAdapter);

        listShoppingView = pricesResultsActivity.findViewById(R.id.shopping_display);

        List<Map.Entry<String, String>> productShoppingList = new ArrayList<>();

        Set<Map.Entry<String, String>> productShoppingPropsSet = bc.shoppingInfoDetails().get(0).entrySet();

        for (Map.Entry<String, String> prop : productShoppingPropsSet) {
            final String entryKey = prop.getKey();
            if (!PRODUCT_DETAIL.equals(entryKey) & !STORE_URL.equals(prop.getKey())) {
                productShoppingList.add(prop);
            }

            if (STORE_URL.equals(entryKey)){
                listShoppingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Map.Entry<String, String> entry = (Map.Entry<String, String>) parent.getItemAtPosition(position);

                        if("STORE_NAME".equals(entry.getKey())) {
                            String url = String.format("https://www.upcitemdb.com/upc/%s", bc.getBarcode());
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            pricesResultsActivity.startActivity(i);
                        }
                    }
                });
            }
        }

        pricesResultsAdapter = new PricesResultsAdapter(pricesResultsActivity, productShoppingList);
        listShoppingView.setAdapter(pricesResultsAdapter);



        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String stringValue = bc.getBarcode() + "\u0000" + bc.productName() + "\u0000" + bc.barcodeUrl();
        editor.putString(bc.getBarcode() , stringValue);
        editor.commit();
    }
}
