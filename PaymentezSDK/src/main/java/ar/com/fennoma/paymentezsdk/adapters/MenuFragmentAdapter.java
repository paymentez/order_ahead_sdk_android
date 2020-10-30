package ar.com.fennoma.paymentezsdk.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;
import ar.com.fennoma.paymentezsdk.models.PmzProduct;
import ar.com.fennoma.paymentezsdk.utils.ColorHelper;
import ar.com.fennoma.paymentezsdk.utils.ImageUtils;

public class MenuFragmentAdapter extends RecyclerView.Adapter<MenuFragmentAdapter.MenuFragmentHolder> {

    private final Activity activity;
    private final PmzMenuProductListener listener;
    private List<PmzProduct> products;

    public interface PmzMenuProductListener {
        void onProductAdded(PmzProduct product);
    }

    public MenuFragmentAdapter(Activity activity, List<PmzProduct> products, PmzMenuProductListener listener) {
        this.activity = activity;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuFragmentHolder(activity.getLayoutInflater().inflate(R.layout.item_pmz_menu_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuFragmentHolder holder, int position) {
        PmzProduct product = products.get(position);

        ImageUtils.loadProductImage(activity, holder.image, product.getImageUrl());

        holder.title.setText(product.getName());
        holder.price.setText("$".concat(String.valueOf(product.getListPrice())));
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onProductAdded(products.get(holder.getAdapterPosition()));
            }
        });

        if(PaymentezSDK.getInstance().getStyle().getButtonTextColor() != null) {
            holder.addButton.setTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
            holder.title.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
            holder.price.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor() != null) {
            ColorHelper.replaceButtonBackground(holder.addButton, PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
        }
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    static class MenuFragmentHolder extends RecyclerView.ViewHolder {

        protected ImageView image;
        protected TextView title;
        protected TextView price;
        protected TextView addButton;

        public MenuFragmentHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            addButton = itemView.findViewById(R.id.add_button);
        }
    }
}
