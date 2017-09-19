package com.dong.visit;

/**
 * Created by dong on 2017/9/18.
 */
public class ClickEntity {

    private int dataId;
    private String eventId;

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "ClickEntity{" +
                "dataId=" + dataId +
                ", eventId='" + eventId + '\'' +
                '}';
    }
}
