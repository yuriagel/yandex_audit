package com.yandex.audit.impl;

import com.yandex.audit.api.Audit;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;

public class AuditImplFast implements Audit {
    private static final int DAY_TIME = 24 * 3600 * 1000;
    private static final int HOUR_TIME = 3600 * 1000;
    private static final int MINUTE_TIME = 60 * 1000;
    private AtomicLongArray longArr = new AtomicLongArray(20_000_000); //сразу выделяем большой кусок памяти, чтобы больше не переживать
    private AtomicInteger l = new AtomicInteger(0);

    public void addEvent() {
       // updateArrSize();
        longArr.addAndGet(l.getAndIncrement(), new Date().getTime());
    }

    private synchronized void updateArrSize() { //TODO если выделять память под массив динамически - то этот метод подойдет, но он не потокобезопасен
         if (longArr.length()-1< l.get()) {
                AtomicLongArray copy = new AtomicLongArray(2 * longArr.length());
                for (int i = 0; i < longArr.length(); i++) {
                    copy.set(i, longArr.get(i));
                }
                longArr = copy;
        }
    }


    public void addEvent(Date eventDate) {
      //  updateArrSize();
        longArr.addAndGet(l.getAndIncrement(), eventDate.getTime());
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

    }

    private long getCountOnPeriod(int dayTime) {
        long date = new Date(System.currentTimeMillis() - dayTime).getTime();
        int i = l.get();
        while ((longArr.get(i)<date)|| i>0) {
            i--;
        }
        return l.get() -i;
    }
}
