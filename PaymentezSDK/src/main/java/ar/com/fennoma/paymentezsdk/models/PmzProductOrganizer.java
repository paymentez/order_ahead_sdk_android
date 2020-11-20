package ar.com.fennoma.paymentezsdk.models;

import java.util.ArrayList;
import java.util.List;

public class PmzProductOrganizer {

    private List<PmzProductConfigurationGroup> groups;

    public void setProduct(PmzProduct product) {
        if(product != null && product.getConfigurations() != null) {
            for(PmzProductConfiguration config : product.getConfigurations()) {
                PmzProductConfigurationGroup group = getLastGroup();
                if(group.shouldAddConfiguration(config)) {
                    group.addConfig(config);
                } else {
                    PmzProductConfigurationGroup newGroup = new PmzProductConfigurationGroup();
                    groups.add(newGroup);
                    newGroup.addConfig(config);
                }
            }
        }
    }

    private PmzProductConfigurationGroup getLastGroup() {
        if(groups != null) {
            return groups.get(groups.size() - 1);
        } else {
            groups = new ArrayList<>();
            PmzProductConfigurationGroup group = new PmzProductConfigurationGroup();
            groups.add(group);
            return group;
        }
    }


    public int size() {
        if(groups == null) {
            return 0;
        } else {
            int result = 0;
            for(PmzProductConfigurationGroup group : groups) {
                result += group.size();
            }
            return result;
        }
    }

    public PmzIProductDisplay getItem(int position) {
        if(groups != null) {
            int reference = position;
            for(PmzProductConfigurationGroup group: groups) {
                if(group.size() <= reference) {
                    reference -= group.size();
                } else {
                    return group.getItem(reference);
                }
            }
        }
        return null;
    }

    public long measureExtras() {
        long result = 0;
        if(groups != null) {
            for(PmzProductConfigurationGroup group: groups) {
                result += group.measureExtras();
            }
        }
        return result;
    }

    public List<PmzConfiguration> getConfigurations() {
        List<PmzConfiguration> configs = new ArrayList<>();
        if(groups != null) {
            for(PmzProductConfigurationGroup group: groups) {
                configs.addAll(group.getConfigurationsForJSON());
            }
        }
        return configs;
    }
}
