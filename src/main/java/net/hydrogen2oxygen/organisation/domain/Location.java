package net.hydrogen2oxygen.organisation.domain;

import org.apache.commons.math3.analysis.function.Add;

import java.util.ArrayList;
import java.util.List;

public class Location implements ITags {

    private String name;
    private String purpose;
    private Address address;
    private List<String> tags = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    @Override
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
