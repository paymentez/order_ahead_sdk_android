package ar.com.fennoma.paymentezsdk.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;
import ar.com.fennoma.paymentezsdk.models.PmzStore;
import ar.com.fennoma.paymentezsdk.utils.ImageUtils;

public class PmzStoresAdapter extends RecyclerView.Adapter<PmzStoresAdapter.PmzStoreHolder> {

    private final Activity activity;
    private final PmzStoreItemListener listener;
    private List<PmzStore> stores;

    public interface PmzStoreItemListener {
        void onStoreClicked(PmzStore store);
    }

    public PmzStoresAdapter(Activity activity, PmzStoreItemListener listener) {
        this.activity = activity;
        this.listener = listener;
        stores = new ArrayList<>();
    }

    @NonNull
    @Override
    public PmzStoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PmzStoreHolder(activity.getLayoutInflater().inflate(R.layout.item_pmz_store, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PmzStoreHolder holder, int position) {
        PmzStore store = stores.get(position);
        ImageUtils.loadStoreImage(activity, holder.image, store.getImageUrl());
        ImageUtils.loadStoreImage(activity, holder.icon, store.getImageUrl());

        holder.title.setText(store.getName());
        holder.description.setText(store.getCommerceName());

        if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
            holder.title.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
            holder.description.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onStoreClicked(stores.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public void setStores(List<PmzStore> stores) {
        this.stores = stores;
        notifyDataSetChanged();
    }

    static class PmzStoreHolder extends RecyclerView.ViewHolder {

        protected View container;
        protected ImageView image;
        protected ImageView icon;
        protected TextView title;
        protected TextView description;
        protected TextView distance;

        public PmzStoreHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            distance = itemView.findViewById(R.id.distance);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
