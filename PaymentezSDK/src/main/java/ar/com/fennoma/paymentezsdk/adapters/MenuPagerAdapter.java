package ar.com.fennoma.paymentezsdk.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ar.com.fennoma.paymentezsdk.fragments.MenuFragment;
import ar.com.fennoma.paymentezsdk.models.PmzCategory;
import ar.com.fennoma.paymentezsdk.models.PmzMenu;

public class MenuPagerAdapter extends FragmentPagerAdapter {

    private PmzMenu menu;
    private final List<MenuFragment> fragments = new ArrayList<>();

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
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private void fillFragmentArray() {
        if(menu != null && menu.getCategories() != null) {
            for(PmzCategory category: menu.getCategories()) {
                MenuFragment fragment = new MenuFragment();
                fragment.setCategory(category);
                fragments.add(fragment);
            }
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(menu != null && menu.getCategories() != null && position < menu.getCategories().size()) {
            return menu.getCategories().get(position).getName();
        }
        return "";
    }
}
