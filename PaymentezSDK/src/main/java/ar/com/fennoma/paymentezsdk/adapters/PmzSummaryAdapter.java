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

public class PmzSummaryAdapter extends RecyclerView.Adapter<PmzSummaryAdapter.PmzSummaryHolder> {

    private final PmzBaseActivity activity;
    private List<PmzItem> items;

    public PmzSummaryAdapter(PmzBaseActivity activity) {
        this.activity = activity;
        this.items = new ArrayList<>();
    }

    @NonNull
    @Override
    public PmzSummaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PmzSummaryHolder(activity.getLayoutInflater().inflate(R.layout.activity_pmz_summary_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PmzSummaryHolder holder, int position) {
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
        setStyles(holder);
    }

    private void setStyles(PmzSummaryHolder holder) {
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
        if(items != null) {
            this.items = items;
            notifyDataSetChanged();
        }
    }

    public static class PmzSummaryHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView description;
        protected TextView price;
        protected ImageView image;
        protected View editButton;

        public PmzSummaryHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);
            editButton = itemView.findViewById(R.id.edit);
        }
    }
}
