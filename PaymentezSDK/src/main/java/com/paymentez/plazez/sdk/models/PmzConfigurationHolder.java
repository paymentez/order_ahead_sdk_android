package com.paymentez.plazez.sdk.models;

import java.util.ArrayList;
import java.util.List;

public class PmzConfigurationHolder implements PmzIProductDisplay{

    private List<PmzProductConfiguration> configurations;

    public PmzConfigurationHolder() {
        configurations = new ArrayList<>();
    }

    public List<PmzProductConfiguration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<PmzProductConfiguration> configurations) {
        this.configurations = configurations;
    }

    public void add(PmzProductConfiguration configuration) {
        configurations.add(configuration);
    }

    public int size() {
        if(configurations != null) {
            return configurations.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getType() {
        return ITEM;
    }
}
