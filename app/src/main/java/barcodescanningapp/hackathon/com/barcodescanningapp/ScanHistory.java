package barcodescanningapp.hackathon.com.barcodescanningapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

import static barcodescanningapp.hackathon.com.barcodescanningapp.MainActivity.KEY_BARCODE;

public class ScanHistory extends Activity {

    private static Context mContext;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_scan_history);
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                getString(R.string.app_name), Context.MODE_PRIVATE);
        Map<String,?> entries = sharedPref.getAll();

        final ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String,?> entry: entries.entrySet()) {
            list.add(entry.getValue().toString());
        }

        listView = (ListView) findViewById(R.id.list);


        ArrayAdapter<String> adapter= new CustomAdapter(list, mContext);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = parent.getItemAtPosition(position);
                String storedString = (String) object;
                String barcode = storedString.split("\u0000")[0];
                Intent displayProductPricesIntent = new Intent(mContext, PricesResultsActivity.class);
                displayProductPricesIntent.putExtra(KEY_BARCODE, barcode);
                mContext.startActivity(displayProductPricesIntent);
            }
        });

    }
}
