package barcodescanningapp.hackathon.com.barcodescanningapp;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hackathon.Barcode;
import com.hackathon.BarcodeHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RetrievePricesTask extends AsyncTask {

    private PricesResultsActivity pricesResultsActivity;
    private String barcode;

    @Override
    protected Object doInBackground(Object[] objects) {
        barcode = (String) objects[0];
        pricesResultsActivity = (PricesResultsActivity) objects[1];

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
    }
}
