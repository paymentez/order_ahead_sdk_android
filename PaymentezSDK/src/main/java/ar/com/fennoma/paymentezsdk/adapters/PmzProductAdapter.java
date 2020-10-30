package ar.com.fennoma.paymentezsdk.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.controllers.PmzBaseActivity;
import ar.com.fennoma.paymentezsdk.controllers.PmzProductActivity;
import ar.com.fennoma.paymentezsdk.models.PmzConfiguration;
import ar.com.fennoma.paymentezsdk.models.PmzIProductDisplay;
import ar.com.fennoma.paymentezsdk.models.PmzProduct;
import ar.com.fennoma.paymentezsdk.models.PmzProductOrganizer;

public class PmzProductAdapter extends RecyclerView.Adapter<PmzProductAdapter.PmzProductHolder> {

    private final PmzBaseActivity activity;
    private PmzProductOrganizer organizer;

    public PmzProductAdapter(PmzBaseActivity activity) {
        this.activity = activity;
        this.organizer = new PmzProductOrganizer();
    }

    @NonNull
    @Override
    public PmzProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PmzProductHolder(activity.getLayoutInflater().inflate(R.layout.pmz_item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PmzProductHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return organizer.size();
    }

    public void setProduct(PmzProduct product) {
        this.organizer.setProduct(product);
    }

    public Long getExtras() {
        return 0L;
    }

    static class PmzProductHolder extends RecyclerView.ViewHolder {

        public PmzProductHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
