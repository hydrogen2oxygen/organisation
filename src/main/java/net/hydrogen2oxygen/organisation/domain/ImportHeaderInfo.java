package net.hydrogen2oxygen.organisation.domain;

import java.util.ArrayList;
import java.util.List;

public class ImportHeaderInfo {

    private List<String> headerNames = new ArrayList<>();

    public List<String> getHeaderNames() {
        return headerNames;
    }

    public void setHeaderNames(List<String> headerNames) {
        this.headerNames = headerNames;
    }
}
