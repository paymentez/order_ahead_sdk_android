package ar.com.fennoma.paymentezsdk.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ar.com.fennoma.paymentezsdk.adapters.PmzProductAdapter;
import ar.com.fennoma.paymentezsdk.models.PmzConfigurationHolder;
import ar.com.fennoma.paymentezsdk.models.PmzProductConfiguration;

public class PmzRadioGroup extends LinearLayout {

    private IExtrasChangedListener listener;
    private PmzConfigurationHolder item;

    private List<PmzRadioItem> items;
    private List<Integer> selections;

    public interface IExtrasChangedListener {
        void onExtrasChanged();
    }

    private int minSelection = 0;
    private int maxSelection = 0;
    private int defaultSelection = -1;

    public PmzRadioGroup(Context context) {
        super(context);
        init();
    }

    public PmzRadioGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PmzRadioGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        selections = new ArrayList<>();
        setOrientation(VERTICAL);
    }

    public void setItem(PmzConfigurationHolder item, IExtrasChangedListener listener) {
        this.item = item;
        this.listener = listener;
        analiseItems();
        recreateData();
    }

    private void analiseItems() {
        if(item != null && item.getConfigurations() != null) {
            for(int i = 0; i < item.getConfigurations().size(); i++) {
                PmzProductConfiguration config = item.getConfigurations().get(i);
                minSelection = config.getMinConfiguration();
                maxSelection = config.getMaxConfiguration();
                if(config.isDefault()) {
                    defaultSelection = i;
                }
            }
        }
        if(minSelection > 0 && defaultSelection == -1) {
            defaultSelection = 0;
        }
    }

    private void recreateData() {
        removeAllViews();
        items = new ArrayList<>();
        if(item != null && item.getConfigurations() != null) {
            for(int i = 0; i < item.getConfigurations().size(); i++) {
                PmzProductConfiguration config = item.getConfigurations().get(i);
                PmzRadioItem radioItem = new PmzRadioItem(getContext());
                radioItem.setItem(config, i, minSelection == 0, new PmzRadioItem.IPmzRadioListener() {
                    @Override
                    public void onValueChanged(int position) {
                        selections.add(position);
                        changeValuesFrom();
                    }
                });
                if(defaultSelection == i) {
                    radioItem.setValue(true);
                    selections.add(i);
                }
                items.add(radioItem);
                addView(radioItem);
            }
        }
    }

    private void changeValuesFrom() {
        int selections = getAmountOfSelections();
        if(selections > maxSelection) {
            int selectionsToRemove = selections - maxSelection;
            for(int i = 0; i < selectionsToRemove; i++) {
                items.get(this.selections.get(i)).setValue(false);
                this.selections.remove(0);
            }
        }
        if(listener != null) {
            listener.onExtrasChanged();
        }
    }

    public long measureExtras() {
        long result = 0;
        for(PmzRadioItem item: items) {
            result += item.getExtras();
        }
        return result;
    }

    private int getAmountOfSelections() {
        int result = 0;
        for(PmzProductConfiguration config: item.getConfigurations()) {
            if(config.isChecked()) {
                result++;
            }
        }
        return result;
    }
}
