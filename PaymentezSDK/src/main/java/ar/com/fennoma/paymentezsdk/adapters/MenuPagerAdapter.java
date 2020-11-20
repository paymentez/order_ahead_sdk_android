package ar.com.fennoma.paymentezsdk.adapters;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ar.com.fennoma.paymentezsdk.fragments.PmzMenuFragment;
import ar.com.fennoma.paymentezsdk.models.PmzCategory;
import ar.com.fennoma.paymentezsdk.models.PmzMenu;

public class MenuPagerAdapter extends FragmentPagerAdapter {

    private PmzMenu menu;
    private List<PmzMenuFragment> fragments = new ArrayList<>();
    private List<PmzMenuFragment> originalFragments = new ArrayList<>();
    private String filter;

    public MenuPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void setMenu(PmzMenu menu) {
        this.menu = menu;
        fillFragmentArray();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        PmzMenuFragment fragment = fragments.get(position);
        fragment.setFilter(filter);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private void fillFragmentArray() {
        if(menu != null && menu.getCategories() != null) {
            fragments = new ArrayList<>();
            originalFragments = new ArrayList<>();
            for(PmzCategory category: menu.getCategories()) {
                PmzMenuFragment fragment = new PmzMenuFragment();
                fragment.setCategory(category);
                fragment.setFilter(filter);
                fragments.add(fragment);
                originalFragments.add(fragment);
            }
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(fragments != null && position < fragments.size() && fragments.get(position).getCategory() != null &&
                !TextUtils.isEmpty(fragments.get(position).getCategory().getName())) {
            return fragments.get(position).getCategory().getName();
        }
        if(menu != null && menu.getCategories() != null && position < menu.getCategories().size()) {
            return menu.getCategories().get(position).getName();
        }
        return "";
    }

    public void setFilter(String s) {
        filter = s;
        //filterFragments();
        //notifyDataSetChanged();
        for(PmzMenuFragment fragment: fragments) {
            if(fragment != null) {
                fragment.setFilter(s);
            }
        }
    }

    private void filterFragments() {
        fragments = new ArrayList<>();
        if(TextUtils.isEmpty(filter)) {
            fragments = originalFragments;
        } else {
            for (PmzMenuFragment fragment : originalFragments) {
                fragment.setFilter(filter);
                if (fragment.hasContent()) {
                    fragments.add(fragment);
                }
            }
        }
        notifyDataSetChanged();
    }
}
