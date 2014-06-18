package com.jdev.domain.dao.repository;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import com.jdev.domain.config.audit.AuditEventConverter;
import com.jdev.domain.entity.PersistentAuditEvent;

/**
 * Wraps an implementation of Spring Boot's AuditEventRepository.
 */
@Repository
public class CustomAuditEventRepository {

    @Autowired
    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    @Bean
    public AuditEventRepository auditEventRepository() {
        return new AuditEventRepository() {

            // @Inject
            @Autowired
            private AuditEventConverter auditEventConverter;

            @Override
            public List<AuditEvent> find(final String principal, final Date after) {
                final Iterable<PersistentAuditEvent> persistentAuditEvents;
                if (principal == null && after == null) {
                    persistentAuditEvents = persistenceAuditEventRepository.findAll();
                } else if (after == null) {
                    persistentAuditEvents = persistenceAuditEventRepository
                            .findByPrincipal(principal);
                } else {
                    persistentAuditEvents = persistenceAuditEventRepository
                            .findByPrincipalAndAuditEventDateGreaterThan(principal,
                                    new LocalDateTime(after));
                }

                return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
            }

            @Override
            public void add(final AuditEvent event) {
                PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent();
                persistentAuditEvent.setPrincipal(event.getPrincipal());
                persistentAuditEvent.setAuditEventType(event.getType());
                persistentAuditEvent.setAuditEventDate(new LocalDateTime(event.getTimestamp()));
                persistentAuditEvent.setData(auditEventConverter.convertDataToStrings(event
                        .getData()));

                persistenceAuditEventRepository.save(persistentAuditEvent);
            }
        };
    }
}
