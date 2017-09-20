package com.dong.visit;

/**
 *  注解实体 @PointParams
 * Created by dong on 2017/9/19.
 */
public class ParamsEntity {

    private String eventId;

    private String paramsName;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getParamsName() {
        return paramsName;
    }

    public void setParamsName(String paramsName) {
        this.paramsName = paramsName;
    }

    @Override
    public String toString() {
        return "ParamsEntity{" +
                "eventId='" + eventId + '\'' +
                ", paramsName='" + paramsName + '\'' +
                '}';
    }
}
