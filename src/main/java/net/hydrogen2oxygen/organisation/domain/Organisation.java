package net.hydrogen2oxygen.organisation.domain;

import java.util.ArrayList;
import java.util.List;

public class Organisation {

    private List<Group> groups = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
