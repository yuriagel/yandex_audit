package com.yandex.audit.run.impl;

import com.yandex.audit.api.Audit;
import com.yandex.audit.run.api.Event;

import java.util.Date;

public abstract class AbstractEvent implements Event {
    public void goSomeStaff() {
        Date d = new Date();
        getAudit().addEvent(d);
        //some Work;

    }

    protected abstract Audit getAudit();


    public void showStat() {
        System.out.println(getAudit().getEventCountLastMinute());
        System.out.println(getAudit().getEventCountLastHour());
        System.out.println(getAudit().getEventCountLastDay());

        System.out.println();
    }

    public long getCountMinute() {
        return getAudit().getEventCountLastMinute();
    }

    public long getEventCountLastDay() {
        return getAudit().getEventCountLastDay();
    }
}
