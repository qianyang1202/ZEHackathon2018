package barcodescanningapp.hackathon.com.barcodescanningapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hackathon.Barcode;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> implements View.OnClickListener{

    private ArrayList<String> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtBarcode;
        TextView txtProduct;
        TextView txtDesctipt;
    }

    public CustomAdapter(ArrayList<String> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Barcode dataModel=(Barcode)object;
//
//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String dataModel = getItem(position);
        String[] s = dataModel.split("\u0000");
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtBarcode = (TextView) convertView.findViewById(R.id.barcode);
            viewHolder.txtProduct = (TextView) convertView.findViewById(R.id.product);
            viewHolder.txtDesctipt = (TextView) convertView.findViewById(R.id.descript);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtBarcode.setText(s[0]);
        viewHolder.txtProduct.setText(s[1]);
        viewHolder.txtDesctipt.setText(s[2]);
        // Return the completed view to render on screen
        return convertView;
    }
}
