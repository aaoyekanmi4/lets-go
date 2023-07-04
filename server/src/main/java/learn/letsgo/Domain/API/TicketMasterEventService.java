package learn.letsgo.Domain.API;

import learn.letsgo.Models.API.TicketMasterEvent;
import learn.letsgo.Models.API.TicketMasterEventResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class TicketMasterEventService {

    private final RestTemplate restTemplate;

    @Value("${ticketmaster.api.key}")
    private String API_KEY;

    public TicketMasterEventService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TicketMasterEvent> getEvents(String postalCode) {
        String url = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=" + API_KEY + "&size=100";

        if (postalCode != null && !postalCode.isEmpty()) {
            url += "&postalCode=" + postalCode;
        }

        TicketMasterEventResponse eventResponse = restTemplate.getForObject(url, TicketMasterEventResponse.class);
        if (eventResponse != null && eventResponse.get_embedded() != null) {
            List<TicketMasterEvent> events = eventResponse.get_embedded().getEvents();
            for (TicketMasterEvent event : events) {
                List<TicketMasterEvent.Image> images = event.getImages();
                images.removeIf(image ->
                        !"16_9".equals(image.getRatio()) ||
                                image.getWidth() != 1024 ||
                                image.getHeight() != 576);
                event.setSource("TicketMaster");
            }
            return events;
        } else {
            return Collections.emptyList();
        }
    }

    public TicketMasterEvent getEventById(String id) {
        String url = String.format("https://app.ticketmaster.com/discovery/v2/events/%s?apikey=" + API_KEY, id);

        TicketMasterEvent ticketMasterEvent = restTemplate.getForObject(url, TicketMasterEvent.class);
        System.out.println(ticketMasterEvent);
        if (ticketMasterEvent != null) {
            List<TicketMasterEvent.Image> images = ticketMasterEvent.getImages();
            images.removeIf(image ->
                    !"16_9".equals(image.getRatio()) ||
                            image.getWidth() != 1024 ||
                            image.getHeight() != 576);
            ticketMasterEvent.setSource("TicketMaster");
            return ticketMasterEvent;
        }
        return null;
    }
}
