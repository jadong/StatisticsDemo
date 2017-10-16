package com.dong.visit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 当前类的描述信息
 * Created by dong on 2017/9/18.
 */
public class ClassDescEntity {

    private String outerClassRefName;//外部类的引用名称
    private String outerClassRefType;//外部类的引用类型
    private String outerClassFullName;//外部类的全名
    private String classFullName;//当前类的全名
    private Map<Integer, DataField> dataFields = new HashMap<>();

    public String getOuterClassRefName() {
        return outerClassRefName;
    }

    public void setOuterClassRefName(String outerClassRefName) {
        this.outerClassRefName = outerClassRefName;
    }

    public String getOuterClassRefType() {
        return outerClassRefType;
    }

    public void setOuterClassRefType(String outerClassRefType) {
        this.outerClassRefType = outerClassRefType;
    }

    public String getOuterClassFullName() {
        return outerClassFullName;
    }

    public void setOuterClassFullName(String outerClassFullName) {
        this.outerClassFullName = outerClassFullName;
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
        return "ClassDescEntity{" +
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