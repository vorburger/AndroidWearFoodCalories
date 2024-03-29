package calories.fit.vorburger.ch.foodcalories;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * See https://developer.android.com/training/wearables/ui/lists.html
 */
public class WearableListViewAdapter<T> extends WearableListView.Adapter {

    private List<T> mDataset;
    private final Context mContext;
    private final LayoutInflater mInflater;

    // Provide a suitable constructor (depends on the kind of dataset)
    public WearableListViewAdapter(Context context, List<T> dataset) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDataset = dataset;
    }

    // Provide a reference to the type of views you're using
    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
        }
    }

    // Create new views for list items (invoked by the WearableListView's layout manager)
    @Override public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
    }

    // Replace the contents of a list item
    // Instead of creating new views, the list tries to recycle existing ones
    // (invoked by the WearableListView's layout manager).
    @Override public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        // retrieve the text view
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView view = itemHolder.textView;
        T data = mDataset.get(position);
        // replace text contents
        view.setText(data.toString());
        // replace list item's metadata
        holder.itemView.setTag(data);
    }

    // Return the size of your dataset (invoked by the WearableListView's layout manager)
    @Override public int getItemCount() {
        return mDataset.size();
    }
}