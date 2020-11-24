package ar.com.fennoma.paymentezsdk.models;

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
        return configurations.size();
    }

    @Override
    public int getType() {
        return ITEM;
    }
}
