package com.dong.visit;

/**
 * Created by dong on 2017/9/19.
 */
public class ParamsEntity {

   private String eventId;

    private String paramsName;

    private String paramsType;

    private String classFullName;

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

    public String getParamsType() {
        return paramsType;
    }

    public void setParamsType(String paramsType) {
        this.paramsType = paramsType;
    }

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }

    @Override
    public String toString() {
        return "ParamsEntity{" +
                "eventId='" + eventId + '\'' +
                ", paramsName='" + paramsName + '\'' +
                ", paramsType='" + paramsType + '\'' +
                ", classFullName='" + classFullName + '\'' +
                '}';
    }
}
