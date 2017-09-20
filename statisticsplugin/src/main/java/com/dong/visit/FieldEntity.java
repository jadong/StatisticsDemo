package com.dong.visit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dong on 2017/9/18.
 */
public class FieldEntity {

    private String refVarName;
    private String refVarType;
    private String refClassFullName;
    private String classFullName;
    private Map<Integer, DataField> dataFields = new HashMap<>();

    public String getRefVarName() {
        return refVarName;
    }

    public void setRefVarName(String refVarName) {
        this.refVarName = refVarName;
    }

    public String getRefVarType() {
        return refVarType;
    }

    public void setRefVarType(String refVarType) {
        this.refVarType = refVarType;
    }

    public String getRefClassFullName() {
        return refClassFullName;
    }

    public void setRefClassFullName(String refClassFullName) {
        this.refClassFullName = refClassFullName;
    }

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }

    public void addDataField(DataField dataField) {
        if (dataField == null || dataField.getDataIds() == null) {
            return;
        }
        int[] dataId = dataField.getDataIds();
        for (int i = 0; i < dataId.length; i++) {
            dataFields.put(dataId[i], dataField);
        }
    }

    public DataField getDataField(int dataId) {
        return dataFields.get(dataId);
    }

    @Override
    public String toString() {
        return "FieldEntity{" +
                "classFullName='" + classFullName + '\'' +
                ", dataFields=" + dataFields +
                '}';
    }

    public static class DataField {

        private int[] dataIds;
        private String dataName;
        private String dataType;

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public int[] getDataIds() {
            return dataIds;
        }

        public void setDataIds(int[] dataIds) {
            this.dataIds = dataIds;
        }

        public String getDataName() {
            return dataName;
        }

        public void setDataName(String dataName) {
            this.dataName = dataName;
        }

        @Override
        public String toString() {
            return "DataField{" +
                    "dataIds=" + Arrays.toString(dataIds) +
                    ", dataName='" + dataName + '\'' +
                    ", dataType='" + dataType + '\'' +
                    '}';
        }
    }
}