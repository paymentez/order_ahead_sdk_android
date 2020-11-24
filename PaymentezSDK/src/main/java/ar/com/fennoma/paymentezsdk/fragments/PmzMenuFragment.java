package ar.com.fennoma.paymentezsdk.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.adapters.MenuFragmentAdapter;
import ar.com.fennoma.paymentezsdk.controllers.PmzMenuActivity;
import ar.com.fennoma.paymentezsdk.models.PmzCategory;
import ar.com.fennoma.paymentezsdk.models.PmzProduct;

public class PmzMenuFragment extends Fragment {

    private PmzCategory category;
    private MenuFragmentAdapter adapter;
    private String filter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pmz_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(view);
        setRecycler(view);
        if(!TextUtils.isEmpty(filter)) {
            adapter.setFilter(filter);
        }
    }

    private void setTitle(View view) {
        TextView title = view.findViewById(R.id.title);
        if (category != null && !TextUtils.isEmpty(category.getName())) {
            title.setText(category.getName());
            //title.setVisibility(View.VISIBLE);
        } else {
            //title.setVisibility(View.GONE);
        }
    }

    private void setRecycler(View view) {
        RecyclerView recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MenuFragmentAdapter(getActivity(), category.getProducts(), new MenuFragmentAdapter.PmzMenuProductListener() {
            @Override
            public void onProductAdded(PmzProduct product) {
                if (getActivity() != null && getActivity() instanceof PmzMenuActivity) {
                    PmzMenuActivity activity = (PmzMenuActivity) getActivity();
                    activity.addProduct(product);
                }
            }
        });
        recycler.setAdapter(adapter);
    }

    public void setCategory(PmzCategory category) {
        this.category = category;
        if(getView() != null) {
            setTitle(getView());
        }
    }

    public PmzCategory getCategory() {
        return category;
    }

    public void setFilter(String s) {
        filter = s;
        if(s != null && adapter != null) {
            adapter.setFilter(s);
        }
    }

    public boolean hasContent() {
        if(category != null && category.getProducts() != null) {
            if(TextUtils.isEmpty(filter)) {
                return true;
            }
            for(PmzProduct product: category.getProducts()) {
                if(product != null && !TextUtils.isEmpty(product.getName()) && product.getName().toLowerCase().contains(filter.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }
}
