package net.yc.citronix.DTO;

import jakarta.validation.constraints.PositiveOrZero;

public class FieldDTO {
    private String id;
    private double size;
    private String farmId;
    private int treeCount;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public int getTreeCount() {
        return treeCount;
    }

    public void setTreeCount(int treeCount) {
        this.treeCount = treeCount;
    }
}
