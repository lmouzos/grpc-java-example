package com.lambda.mi.grpc.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ReallyComplexObject implements Serializable {
    private final String id;
    private final int level;
    private final List<String> itemList;
    private final Set<String> itemSet;

    public ReallyComplexObject(String id, int level, List<String> itemList, Set<String> itemSet) {
        this.id = id;
        this.level = level;
        this.itemList = itemList;
        this.itemSet = itemSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReallyComplexObject that = (ReallyComplexObject) o;
        return level == that.level && Objects.equals(id, that.id) && Objects.equals(itemList, that.itemList) && Objects.equals(itemSet, that.itemSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level, itemList, itemSet);
    }
}
