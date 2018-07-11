package com.yandex.audit.api;
import java.util.Date;

public interface Audit {

    void addEvent();
    void addEvent(Date eventDate);
    long getEventCountLastMinute();
    long getEventCountLastHour();
    long getEventCountLastDay();

    void removeOldData();

}
