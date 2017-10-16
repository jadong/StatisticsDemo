package com.dong.visit;

/**
 *  注解实体 @PointParams
 * Created by dong on 2017/9/19.
 */
public class AnnotationParams {

    private boolean isCTRClick = false;

    private boolean isCTRView = false;

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

    public boolean isCTRClick() {
        return isCTRClick;
    }

    public void setCTRClick(boolean CTRClick) {
        isCTRClick = CTRClick;
    }

    public boolean isCTRView() {
        return isCTRView;
    }

    public void setCTRView(boolean CTRView) {
        isCTRView = CTRView;
    }

    @Override
    public String toString() {
        return "AnnotationParams{" +
                "eventId='" + eventId + '\'' +
                ", paramsName='" + paramsName + '\'' +
                '}';
    }
}
