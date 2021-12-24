package com.switchfully.goatpark.domain.person;

import com.google.common.collect.Lists;

import java.util.List;

import static com.switchfully.goatpark.domain.person.Feature.*;

public enum Role {
    // replace nulls with features
    MANAGER("manager", CREATE_PARKING_SPOT_ALLOCATION, CREATE_DIVISION, GET_ALL_MEMBERS, GET_ALL_DIVISIONS, CREATE_PARKING_LOT, GET_ALL_PARKING_LOTS),
    MEMBER("member", CREATE_PARKING_SPOT_ALLOCATION, DEFAULT);

    private final String label;
    private final List<Feature> featureList;

    Role(String label, Feature... featureList) {
        this.label = label;
        this.featureList = Lists.newArrayList(featureList);
    }

    public List<Feature> getFeatures() {
        return featureList;
    }

    public String getLabel() {
        return label;
    }
}
