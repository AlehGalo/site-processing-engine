package com.jdev.ngui.service;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDateTime;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdev.domain.config.audit.AuditEventConverter;
import com.jdev.domain.dao.repository.PersistenceAuditEventRepository;
import com.jdev.domain.domain.PersistentAuditEvent;

/**
 * Service for managing audit events.
 * <p/>
 * <p>
 * This is the default implementation to support SpringBoot Actuator
 * AuditEventRepository
 * </p>
 */
@Service
@Transactional
public class AuditEventService {

    @Inject
    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    @Inject
    private AuditEventConverter auditEventConverter;

    public List<AuditEvent> findAll() {
        return auditEventConverter.convertToAuditEvent(persistenceAuditEventRepository.findAll());
    }

    public List<AuditEvent> findByDates(final LocalDateTime fromDate, final LocalDateTime toDate) {
        final List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository
                .findByDates(fromDate, toDate);

        return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
    }
}