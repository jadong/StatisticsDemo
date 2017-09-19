package com.dong.visit;

import org.objectweb.asm.Type;

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
        try {
            this.paramsType = Type.getDescriptor(Class.forName(paramsType));
        } catch (ClassNotFoundException e) {
            this.paramsType = "Ljava/lang/String;";
            e.printStackTrace();
        }
    }

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName.replaceAll("\\.", "/");
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
