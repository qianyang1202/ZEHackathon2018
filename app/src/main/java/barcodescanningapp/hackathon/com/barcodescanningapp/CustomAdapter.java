package barcodescanningapp.hackathon.com.barcodescanningapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {

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
            viewHolder.txtProduct = (TextView) convertView.findViewById(R.id.product);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtProduct.setText("Product: " + s[1]);
        // Return the completed view to render on screen
        return convertView;
    }
}
