package learn.letsgo.Domain;

import learn.letsgo.Data.VenueRepository;
import learn.letsgo.Models.Venue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {
    private final VenueRepository venueRepository;


    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<Venue> findAll() {
        return venueRepository.findAll();
    }

    public Venue findById(int venueId) {
        return venueRepository.findById(venueId);
    }

    public Result<Venue> upsert(Venue venue) {
        Result<Venue> result = validate(venue);
        if (!result.isSuccess()) {
            return result;
        }
        Venue existingVenue = Helpers.findVenueIfExists(venue, venueRepository);
        if (existingVenue != null) {
            result.setPayload(existingVenue);
            return result;
        } else {
            Venue newVenue = venueRepository.create(venue);
            result.setPayload(newVenue);
        }
        return result;
    }

    public Result<Venue> update(Venue venue) {
        Result<Venue> result = validate(venue);
        if (!result.isSuccess()) {
            return result;
        }
        boolean didUpdate = venueRepository.update(venue);
        if (!didUpdate) {
            result.addMessage(ResultType.NOT_FOUND, String.format("venue with venueId %s not found",
                    venue.getVenueId()));
        }
        return result;
    }

    private Result<Venue> validate(Venue venue) {
        Result<Venue> result = new Result<>();
        if (venue == null) {
            result.addMessage(ResultType.INVALID, "Venue cannot be null");
            return result;
        }
        if (Helpers.isNullOrBlank(venue.getAddress())) {
            result.addMessage(ResultType.INVALID, "Address is required");
        }
        if (Helpers.isNullOrBlank(venue.getCity())) {
            result.addMessage(ResultType.INVALID, "City is required");
        }
        if (Helpers.isNullOrBlank(venue.getVenueName())) {
            result.addMessage(ResultType.INVALID, "Venue name is required");
        }
        if (venue.getZipCode() == 0) {
            result.addMessage(ResultType.INVALID, "Zipcode is required");
        }
        return result;
    }
}
