package ar.com.fennoma.paymentezsdk.adapters;

import android.text.TextUtils;
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
import ar.com.fennoma.paymentezsdk.controllers.PmzBaseActivity;
import ar.com.fennoma.paymentezsdk.models.PmzConfiguration;
import ar.com.fennoma.paymentezsdk.models.PmzItem;
import ar.com.fennoma.paymentezsdk.styles.PmzStyle;
import ar.com.fennoma.paymentezsdk.utils.ImageUtils;
import ar.com.fennoma.paymentezsdk.utils.PmzCurrencyUtils;

public class PmzCartAdapter extends SwiperAdapter<PmzItem, PmzCartAdapter.PmzCartHolder> {

    public interface IPmzCartAdapterListener {
        void onItemRemoved(PmzItem item, int position);
        void onItemRestored(PmzItem item);
        void onEditItem(PmzItem item);
    }

    private final PmzBaseActivity activity;
    private List<PmzItem> items;
    private final IPmzCartAdapterListener listener;

    public PmzCartAdapter(PmzBaseActivity activity, IPmzCartAdapterListener listener) {
        this.activity = activity;
        this.items = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public PmzCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PmzCartHolder(activity.getLayoutInflater().inflate(R.layout.activity_pmz_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PmzCartHolder holder, int position) {
        PmzItem item = items.get(position);
        holder.title.setText(item.getProductName());
        holder.price.setText(PmzCurrencyUtils.formatPrice(item.getPrice()));
        ImageUtils.loadProductImage(activity, holder.image, item.getImageUrl());

        String description = getDescription(item.getConfigurations());
        if(!TextUtils.isEmpty(description)) {
            holder.description.setText(description);
            holder.description.setVisibility(View.VISIBLE);
        } else {
            holder.description.setVisibility(View.GONE);
        }
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onEditItem(items.get(holder.getAdapterPosition()));
                }
            }
        });
        setStyles(holder);
    }

    private void setStyles(PmzCartHolder holder) {
        PmzStyle style = PaymentezSDK.getInstance().getStyle();
        if(style != null) {
            if(style.getTextColor() != null) {
                holder.title.setTextColor(style.getTextColor());
                holder.price.setTextColor(style.getTextColor());
            }
        }
    }

    private String getDescription(List<PmzConfiguration> configurations) {
        String result = "";
        if(configurations != null) {
            for(PmzConfiguration configuration: configurations) {
                if(!TextUtils.isEmpty(result)) {
                    result = result.concat("\n");
                }
                result = result.concat("+").concat(configuration.getDescription());
            }
        }
        return result;
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
        PmzItem pmzItem = items.get(position);
        items.remove(position);
        notifyItemRemoved(position);
        listener.onItemRemoved(pmzItem, position);
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

    public static class PmzCartHolder extends RecyclerView.ViewHolder {

        public View container;
        protected TextView title;
        protected TextView description;
        protected TextView price;
        protected ImageView image;
        protected View editButton;

        public PmzCartHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);
            editButton = itemView.findViewById(R.id.edit);
        }
    }
}
