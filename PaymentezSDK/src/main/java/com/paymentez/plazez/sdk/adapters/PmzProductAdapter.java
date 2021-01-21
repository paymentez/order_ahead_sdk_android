package com.paymentez.plazez.sdk.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paymentez.plazez.sdk.R;
import com.paymentez.plazez.sdk.controllers.PmzBaseActivity;
import com.paymentez.plazez.sdk.controls.PmzRadioGroup;
import com.paymentez.plazez.sdk.models.PmzConfigurationHolder;
import com.paymentez.plazez.sdk.models.PmzIProductDisplay;
import com.paymentez.plazez.sdk.models.PmzItem;
import com.paymentez.plazez.sdk.models.PmzProduct;
import com.paymentez.plazez.sdk.models.PmzProductOrganizer;
import com.paymentez.plazez.sdk.models.PmzTitleItem;

public class PmzProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final PmzBaseActivity activity;
    private final IExtrasListener listener;
    private PmzProductOrganizer organizer;
    private boolean editing = false;

    public PmzProductOrganizer getOrganizer() {
        return organizer;
    }

    public interface IExtrasListener {
        void onExtrasUpdated(long extras);
    }

    public PmzProductAdapter(PmzBaseActivity activity, IExtrasListener listener) {
        this.activity = activity;
        this.organizer = new PmzProductOrganizer();
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        PmzIProductDisplay item = organizer.getItem(position);
        if(item != null) {
            return item.getType();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == PmzIProductDisplay.ITEM) {
            return new PmzProductHolder(activity.getLayoutInflater().inflate(R.layout.pmz_radio_group, parent, false));
        }
        return new PmzProductTitleHolder(activity.getLayoutInflater().inflate(R.layout.pmz_item_product_title, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder genericHolder, int position) {
        int viewType = getItemViewType(position);
        if(viewType == PmzIProductDisplay.ITEM) {
            PmzProductHolder holder = (PmzProductHolder) genericHolder;
            PmzConfigurationHolder item = (PmzConfigurationHolder) organizer.getItem(position);
            holder.radio.setItem(editing, item, new PmzRadioGroup.IExtrasChangedListener() {
                @Override
                public void onExtrasChanged() {
                    listener.onExtrasUpdated(organizer.measureExtras());
                }
            });
        } else {
            PmzProductTitleHolder holder = (PmzProductTitleHolder) genericHolder;
            PmzTitleItem item = (PmzTitleItem) organizer.getItem(position);
            holder.title.setText(item.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return organizer.size();
    }

    public void setProduct(PmzProduct product) {
        this.organizer.setProduct(product);
    }

    public void setProductForEdit(PmzProduct product, PmzItem item) {
        editing = true;
        this.organizer.setProduct(product);
        this.organizer.setItem(item);
        notifyDataSetChanged();
    }

    public Long getExtras() {
        if(organizer != null) {
            return organizer.measureExtras();
        }
        return 0L;
    }

    static class PmzProductHolder extends RecyclerView.ViewHolder {

        protected PmzRadioGroup radio;

        public PmzProductHolder(@NonNull View itemView) {
            super(itemView);
            radio = itemView.findViewById(R.id.radio_group);
        }
    }

    static class PmzProductTitleHolder extends RecyclerView.ViewHolder {

        protected TextView title;

        public PmzProductTitleHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
