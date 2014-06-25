package com.jdev.ui.web.rest;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdev.ui.propertyeditor.LocaleDateTimeEditor;
import com.jdev.ui.security.AuthoritiesConstants;
import com.jdev.ui.service.AuditEventService;

/**
 * REST controller for getting the audit events.
 */
@RestController
@RequestMapping("/app")
public class AuditResource {

    @Autowired
    private AuditEventService auditEventService;

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new LocaleDateTimeEditor("yyyy-MM-dd",
                false));
    }

    @RequestMapping(value = "/rest/audits/all", method = RequestMethod.GET, produces = "application/json")
    @Secured(AuthoritiesConstants.ADMIN)
    public List<AuditEvent> findAll() {
        return auditEventService.findAll();
    }

    @RequestMapping(value = "/rest/audits/byDates", method = RequestMethod.GET, produces = "application/json")
    @Secured(AuthoritiesConstants.ADMIN)
    public List<AuditEvent> findByDates(
            @RequestParam(value = "fromDate") final LocalDateTime fromDate,
            @RequestParam(value = "toDate") final LocalDateTime toDate) {
        return auditEventService.findByDates(fromDate, toDate);
    }
}
