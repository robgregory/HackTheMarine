package hackthemarine.coastalwearable;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BeachesAdapter extends  WearableListView.Adapter{

    private String[] beaches;
    private final LayoutInflater mInflater;

    public BeachesAdapter(Context context, String[] beaches){
        this.mInflater = LayoutInflater.from(context);
        this.beaches = beaches;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.beach_small, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return beaches.length;
    }

    private static class ItemViewHolder extends WearableListView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}