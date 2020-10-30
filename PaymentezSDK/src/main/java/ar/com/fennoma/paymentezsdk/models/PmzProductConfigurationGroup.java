package ar.com.fennoma.paymentezsdk.models;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class PmzProductConfigurationGroup {

    private PmzTitleItem title;
    private List<PmzProductConfiguration> configurations;

    public boolean shouldAddConfiguration(PmzProductConfiguration configuration) {
        if(title == null || TextUtils.isEmpty(title.getTitle())) {
            return true;
        }
        return title.getTitle().equals(configuration.getSubtypeName());
    }

    public void addConfig(PmzProductConfiguration configuration) {
        if(configurations == null) {
            configurations = new ArrayList<>();
        }
        configurations.add(configuration);
    }

    public int size() {
        if(configurations == null) {
            return 0;
        } else {
            return configurations.size();
        }
    }
}
