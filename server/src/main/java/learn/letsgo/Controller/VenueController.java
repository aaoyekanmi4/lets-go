package learn.letsgo.Controller;

import learn.letsgo.Domain.Result;
import learn.letsgo.Domain.VenueService;
import learn.letsgo.Models.Venue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/venue")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public List<Venue> findAll () {
        return venueService.findAll();
    }

    @GetMapping("/{venueId}")
    public Venue findById(@PathVariable int venueId) {
        return venueService.findById(venueId);
    }

    @PostMapping
    public ResponseEntity<Object> upsertVenue(@RequestBody Venue venue) {
        Result<Venue> result = venueService.upsert(venue);
        if (result.isSuccess()) {
            return new ResponseEntity<Object>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
     }

     @PutMapping("/{venueId}")
     public ResponseEntity<?> updateVenue(@RequestBody Venue venue, @PathVariable int venueId) {
        if (venueId != venue.getVenueId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
         Result<Venue> result = venueService.update(venue);
         if (result.isSuccess()) {
             return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
         }
         return ErrorResponse.build(result);
     }
}
