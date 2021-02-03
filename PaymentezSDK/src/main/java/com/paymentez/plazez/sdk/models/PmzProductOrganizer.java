package com.paymentez.plazez.sdk.models;

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
            if(groups != null) {
                for(PmzProductConfigurationGroup group: groups) {
                    setDefaults(group);
                }
            }
        }
    }

    private void setDefaults(PmzProductConfigurationGroup group) {
        int defaultSelection = -1;
        int minSelection = 0;
        if(group != null && group.getConfigurations() != null) {
            for(int i = 0; i < group.getConfigurations().size(); i++) {
                PmzProductConfiguration config = group.getConfigurations().getConfigurations().get(i);
                minSelection = config.getMinConfiguration();
                if(config.getDefaultValue()) {
                    defaultSelection = i;
                    config.setChecked(true);
                }
            }
            if(minSelection > 0 && defaultSelection == -1) {
                defaultSelection = 0;
                if(group.getConfigurations().size() > 0) {
                    PmzProductConfiguration pmzProductConfiguration = group.getConfigurations().getConfigurations().get(defaultSelection);
                    pmzProductConfiguration.setDefault(true);
                    pmzProductConfiguration.setChecked(true);
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

    public void setItem(PmzItem item) {
        if(item != null && item.getConfigurations() != null) {
            for(PmzConfiguration config: item.getConfigurations()) {
                PmzProductConfiguration productConfig = getProductConfigById(config.getConfigurationId());
                productConfig.setChecked(true);
            }
        }
    }

    private PmzProductConfiguration getProductConfigById(Long id) {
        PmzProductConfiguration result = null;
        if(groups != null && id != null) {
            for (PmzProductConfigurationGroup group : groups) {
                if(group != null && group.getConfigurations() != null && group.getConfigurations().getConfigurations() != null) {
                    for(PmzProductConfiguration config: group.getConfigurations().getConfigurations()) {
                        if(config != null && config.getId() != null && config.getId().equals(id)) {
                            result = config;
                        }
                    }
                }
            }
        }
        return result;
    }
}
