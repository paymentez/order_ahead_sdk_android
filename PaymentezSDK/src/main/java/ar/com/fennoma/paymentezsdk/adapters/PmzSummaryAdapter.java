package ar.com.fennoma.paymentezsdk.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.controllers.PmzBaseActivity;
import ar.com.fennoma.paymentezsdk.controllers.PmzSummaryActivity;
import ar.com.fennoma.paymentezsdk.models.PmzItem;

public class PmzSummaryAdapter extends SwiperAdapter<PmzItem, PmzSummaryAdapter.PmzSummaryHolder> {

    public interface IPmzSummaryAdapterListener {
        void onItemRemoved(PmzItem item);
        void onItemRestored(PmzItem item);
    }

    private final PmzBaseActivity activity;
    private List<PmzItem> items;
    private final IPmzSummaryAdapterListener listener;

    public PmzSummaryAdapter(PmzBaseActivity activity, IPmzSummaryAdapterListener listener) {
        this.activity = activity;
        this.items = new ArrayList<>();
        this.listener = listener;
    }


    @NonNull
    @Override
    public PmzSummaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PmzSummaryHolder(activity.getLayoutInflater().inflate(R.layout.activity_pmz_summary_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PmzSummaryHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<PmzItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public void removeItem(int position) {
        listener.onItemRemoved(items.get(position));
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void restoreItem(PmzItem item, int position) {
        if(position == items.size()) {
            items.add(item);
        } else {
            items.add(position, item);
        }
        notifyItemInserted(position);
        listener.onItemRestored(item);
    }

    public static class PmzSummaryHolder extends RecyclerView.ViewHolder {

        public View container;

        public PmzSummaryHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
        }
    }
}
