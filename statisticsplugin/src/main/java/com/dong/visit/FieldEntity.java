package com.dong.visit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dong on 2017/9/18.
 */
public class ParamsEntity {

    private boolean isHave = false;

    private List<DataField> dataFields = new ArrayList<>();

    public void addDataField(DataField dataField) {
        dataFields.add(dataField);
    }

    public boolean isHave() {
        return dataFields.size() > 0;
    }

    public static class DataField {

        private String[] dataId;
        private String dataName;

        public String[] getDataId() {
            return dataId;
        }

        public void setDataId(String[] dataId) {
            this.dataId = dataId;
        }

        public String getDataName() {
            return dataName;
        }

        public void setDataName(String dataName) {
            this.dataName = dataName;
        }
    }
}