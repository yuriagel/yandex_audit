package com.yandex.audit.run.impl;

import com.yandex.audit.api.Audit;
import com.yandex.audit.impl.AuditImplBad;
import com.yandex.audit.run.api.Event;

public class EventRunBad extends AbstractEvent implements Event {

    private Audit audit = new AuditImplBad();

    public Audit getAudit() {
        return audit;
    }
}
