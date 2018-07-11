package com.yandex.audit.run.impl;

import com.yandex.audit.api.Audit;
import com.yandex.audit.impl.AuditImplFast;
import com.yandex.audit.run.api.Event;

public class EventRunFast extends AbstractEvent implements Event {

    private Audit audit = new AuditImplFast();

    public Audit getAudit() {
        return audit;
    }
}
