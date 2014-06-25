package com.jdev.ui.web.websocket;

import java.io.IOException;
import java.util.Calendar;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdev.domain.dto.ActivityDTO;
import com.jdev.ui.jackson.ActivityDTOJacksonDecoder;

@ManagedService(path = "/websocket/activity")
public class ActivityService {

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

    private final Broadcaster b = BroadcasterFactory.getDefault()
            .lookup("/websocket/tracker", true);

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormat
            .forPattern("yyyy-MM-dd HH:mm:ss");

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Disconnect
    public void onDisconnect(final AtmosphereResourceEvent event) throws IOException {
        log.debug("Browser {} disconnected", event.getResource().uuid());
        AtmosphereRequest request = event.getResource().getRequest();
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setUuid(event.getResource().uuid());
        activityDTO.setPage("logout");
        String json = jsonMapper.writeValueAsString(activityDTO);
        for (AtmosphereResource trackerResource : b.getAtmosphereResources()) {
            trackerResource.getResponse().write(json);
        }
    }

    @Message(decoders = { ActivityDTOJacksonDecoder.class })
    public void onMessage(final AtmosphereResource atmosphereResource, final ActivityDTO activityDTO)
            throws IOException {
        AtmosphereRequest request = atmosphereResource.getRequest();
        activityDTO.setUuid(atmosphereResource.uuid());
        activityDTO.setIpAddress(request.getRemoteAddr());
        activityDTO.setTime(dateTimeFormatter.print(Calendar.getInstance().getTimeInMillis()));
        String json = jsonMapper.writeValueAsString(activityDTO);
        log.debug("Sending user tracking data {}", json);
        for (AtmosphereResource trackerResource : b.getAtmosphereResources()) {
            trackerResource.getResponse().write(json);
        }
    }
}
