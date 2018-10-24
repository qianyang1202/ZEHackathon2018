package barcodescanningapp.hackathon.com.barcodescanningapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends Activity implements OnClickListener {

    public static final String KEY_BARCODE = MainActivity.class.getName() + ".KEY_BARCODE";
    private Button scanBtn, histBtn, demoBtn;
    private TextView formatTxt, contentTxt;
    private EditText demoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn = (Button)findViewById(R.id.scan_button);
        histBtn = (Button)findViewById(R.id.history_button);
        demoBtn = (Button)findViewById(R.id.demo_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        demoTxt = (EditText)findViewById(R.id.demo_text_field);
        scanBtn.setOnClickListener(this);
        histBtn.setOnClickListener(this);
        demoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //respond to clicks
        int viewId = v.getId();
        switch (viewId) {
            case R.id.scan_button: {
                //scan
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
            }
            case R.id.history_button: {
                Intent intent = new Intent(this, ScanHistory.class);
                startActivity(intent);
                break;
            }
            case R.id.demo_button: {
                Intent displayProductPricesIntent = new Intent(this, PricesResultsActivity.class);
                String demoValue = demoTxt.getText().toString();
                if (null != demoValue && demoValue.length() != 0) {
                    displayProductPricesIntent.putExtra(KEY_BARCODE, demoValue);
                    startActivity(displayProductPricesIntent);
                }
                break;
            }
            default: {
                //nothing;
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
         //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        System.out.println();
        if (null != scanningResult && null != scanningResult.getContents()) {
            //we have a result
            String scanContent = scanningResult.getContents();
            Intent displayProductPricesIntent = new Intent(this, PricesResultsActivity.class);
            displayProductPricesIntent.putExtra(KEY_BARCODE, scanContent);
            startActivity(displayProductPricesIntent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }


}
