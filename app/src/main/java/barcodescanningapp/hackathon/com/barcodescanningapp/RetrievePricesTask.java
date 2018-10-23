package barcodescanningapp.hackathon.com.barcodescanningapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hackathon.Barcode;
import com.hackathon.BarcodeHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RetrievePricesTask extends AsyncTask {

    private PricesResultsActivity pricesResultsActivity;
    private ListView listView;
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
        Barcode bc = (Barcode) o;
        TextView productNameTextView =  pricesResultsActivity.findViewById(R.id.product_name);
        productNameTextView.setText(bc.productName());

        listView = pricesResultsActivity.findViewById(R.id.properties_display);

        List<Map.Entry<String, String>> productDetailsList = new ArrayList<>();

        Set<Map.Entry<String, String>> productDetailsPropsSet = bc.productDetails().entrySet();

        for (Map.Entry<String, String> prop : productDetailsPropsSet) {
            productDetailsList.add(prop);
        }

        pricesResultsAdapter = new PricesResultsAdapter(pricesResultsActivity, productDetailsList);
        listView.setAdapter(pricesResultsAdapter);

        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String stringValue = bc.barcode() + "\u0000" + bc.productName() + "\u0000" + bc.barcodeUrl();
        editor.putString("" + sharedPref.getAll().size() , stringValue);
        editor.commit();
    }
}
