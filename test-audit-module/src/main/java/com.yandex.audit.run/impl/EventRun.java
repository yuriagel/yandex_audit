package com.yandex.audit.run.impl;

import com.yandex.audit.api.Audit;
import com.yandex.audit.impl.AuditImpl;
import com.yandex.audit.run.api.Event;

public class EventRun extends AbstractEvent implements Event {

    private Audit audit = new AuditImpl();

    public Audit getAudit() {
        return audit;
    }
}
