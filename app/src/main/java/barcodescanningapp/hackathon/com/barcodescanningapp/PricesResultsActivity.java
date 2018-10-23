package barcodescanningapp.hackathon.com.barcodescanningapp;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import static barcodescanningapp.hackathon.com.barcodescanningapp.MainActivity.KEY_BARCODE;

public class PricesResultsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices_results);

        Intent pricesResultsIntent = getIntent();
        String barcode = pricesResultsIntent.getStringExtra(KEY_BARCODE);
        AsyncTask retrievePricesTask = new RetrievePricesTask();
        retrievePricesTask.execute(barcode, this, getApplicationContext());
    }
}
