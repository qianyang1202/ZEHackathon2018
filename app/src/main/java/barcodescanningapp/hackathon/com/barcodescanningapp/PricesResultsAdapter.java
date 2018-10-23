package barcodescanningapp.hackathon.com.barcodescanningapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class PricesResultsAdapter extends ArrayAdapter<Map.Entry<String, String>> {

    private Context mContext;
    private List<Map.Entry<String, String>> list;

    public PricesResultsAdapter(@NonNull Context context,  List<Map.Entry<String, String>> list) {
        super(context, 0, list);
        mContext = context;
        this.list = list;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.properties_list,parent,false);
        }

        Map.Entry<String, String> currentEntry = list.get(position);

        TextView key = (TextView) listItem.findViewById(R.id.key);
        key.setText(currentEntry.getKey());

        TextView value = (TextView) listItem.findViewById(R.id.value);
        value.setText(currentEntry.getValue());

        return listItem;
    }
}
