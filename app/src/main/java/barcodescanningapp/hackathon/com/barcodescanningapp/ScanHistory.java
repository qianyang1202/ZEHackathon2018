package barcodescanningapp.hackathon.com.barcodescanningapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class ScanHistory extends Activity {

    private static Context mContext;

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

        ListView listView = (ListView) findViewById(R.id.list);


        ArrayAdapter<String> adapter= new CustomAdapter(list, mContext);
        listView.setAdapter(adapter);

    }
}
