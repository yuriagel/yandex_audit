package com.yandex.audit.impl;

import com.yandex.audit.api.Audit;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AuditImpl implements Audit {
    private static final int DAY_TIME = 24 * 3600 * 1000;
    private static final int HOUR_TIME = 3600 * 1000;
    private static final int MINUTE_TIME = 60 * 1000;
    private final ConcurrentLinkedQueue<Long> eventList = new ConcurrentLinkedQueue<>();

    public void addEvent() {
        eventList.add(new Date().getTime());
    }


    public void addEvent(Date eventDate) {
        eventList.add(eventDate.getTime());
    }


    public long getEventCountLastMinute() {
        return getCountOnPeriod(MINUTE_TIME);
    }

    public long getEventCountLastHour() {
        return getCountOnPeriod(HOUR_TIME);
    }

    public long getEventCountLastDay() {
        return getCountOnPeriod(DAY_TIME);
    }

    @Override
    public void removeOldData() {
        long date = new Date(System.currentTimeMillis() - DAY_TIME).getTime();
        eventList.stream().filter(el -> el<date).findFirst()
                .map(p -> {
                    eventList.remove(p);
                    return p;
                });
    }

    private long getCountOnPeriod(int dayTime) {
        long date = new Date(System.currentTimeMillis() - dayTime).getTime();
        return eventList.stream().filter(el -> el>date).count();
    }
}
