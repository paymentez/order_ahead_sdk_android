package ar.com.fennoma.paymentezsdk.models;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PmzProductConfigurationGroup {

    private PmzTitleItem title;
    private PmzConfigurationHolder configurations;

    public boolean shouldAddConfiguration(PmzProductConfiguration configuration) {
        if(title == null || TextUtils.isEmpty(title.getTitle())) {
            return true;
        }
        return title.getTitle().equals(configuration.getSubtypeName());
    }

    public void addConfig(PmzProductConfiguration configuration) {
        if(configurations == null) {
            configurations = new PmzConfigurationHolder();
        }
        configurations.add(configuration);
        if(title == null) {
            title = new PmzTitleItem(configuration);
        }
    }

    public PmzConfigurationHolder getConfigurations() {
        return configurations;
    }

    public PmzTitleItem getTitle() {
        return title;
    }

    public int size() {
        if(configurations == null) {
            return 0;
        } else {
            int result = 0;
            if(getTitle() != null) {
                result += 1;
            }
            if(getConfigurations() != null) {
                result += 1;
            }
            return result;
        }
    }

    public PmzIProductDisplay getItem(int position) {
        if(position == 0) {
            return title;
        } else if(configurations != null && position == 1) {
            return configurations;
        } else {
            return null;
        }
    }

    public long measureExtras() {
        long result = 0;
        if(configurations != null && configurations.getConfigurations() != null) {
            for(PmzProductConfiguration configuration: configurations.getConfigurations()) {
                if(configuration.isChecked()) {
                    result += configuration.getExtraPrice();
                }
            }
        }
        return result;
    }

    public List<PmzConfiguration> getConfigurationsForJSON() {
        List<PmzConfiguration> result = new ArrayList<>();
        if(configurations != null && configurations.getConfigurations() != null) {
            for(PmzProductConfiguration config: configurations.getConfigurations()) {
                if(config.isChecked()) {
                    result.add(new PmzConfiguration(config.getId()));
                }
            }
        }
        return result;
    }
}
