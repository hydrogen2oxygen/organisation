package net.hydrogen2oxygen.organisation.domain;

import java.util.ArrayList;
import java.util.List;

public class KeyValueMap {

    private List<KeyValue> keyValueList = new ArrayList<>();

    public List<KeyValue> getKeyValueList() {
        return keyValueList;
    }

    public void setKeyValueList(List<KeyValue> keyValueList) {
        this.keyValueList = keyValueList;
    }
}
