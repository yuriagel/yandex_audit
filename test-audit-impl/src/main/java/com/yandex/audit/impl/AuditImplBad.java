package com.yandex.audit.impl;

import com.yandex.audit.api.Audit;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class AuditImplBad implements Audit {
    private static final int DAY_TIME = 24 * 3600 * 1000;
    private static final int HOUR_TIME = 3600 * 1000;
    private static final int MINUTE_TIME = 60 * 1000;
    private final List<Date> eventList = new LinkedList<>();

    public void addEvent() {
        eventList.add(new Date());
    }


    public void addEvent(Date eventDate) {
        eventList.add(new Date(eventDate.getTime()));
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

    private long getCountOnPeriod(int dayTime) {
        Date date = new Date(System.currentTimeMillis() - dayTime);
        return eventList.stream().filter(el -> el.after(date)).count();
    }

    @Override
    public void removeOldData() {
        Date date = new Date(System.currentTimeMillis() - DAY_TIME);
        eventList.stream().filter(el -> el.before(date)).findFirst()
                .map(p -> {
                    eventList.remove(p);
                    return p;
                });
    }
}
