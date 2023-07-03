package learn.letsgo.Controller;

import learn.letsgo.Domain.ContactService;
import learn.letsgo.Domain.Result;
import learn.letsgo.Models.Contact;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/contact")

public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/user/{appUserId}")
    public List<Contact> findAllContactsForUser(@PathVariable int appUserId) {
        return contactService.findAllForUser(appUserId);
    }

    @GetMapping("/event/{savedEventId}")
    public List<Contact> findAllContactsForSavedEvent(@PathVariable int savedEventId) {
        return contactService.findAllBySavedEventId(savedEventId);
    }

    @GetMapping("/{contactId}")
    public Contact findContactById(@PathVariable int contactId) {
        return contactService.findContactById(contactId);
    }

    @PostMapping
    public ResponseEntity<Object> createContact(@RequestBody Contact contact) {
        Result<Contact> result = contactService.create(contact);
        if (result.isSuccess()) {
            return new ResponseEntity<Object>(result.getPayload(),HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<Object> updateContact(@RequestBody Contact contact, @PathVariable int contactId){
        if (contactId != contact.getContactId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Contact> result = contactService.update(contact);
        if (result.isSuccess()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<?> deleteContact(@PathVariable int contactId) {
        if(contactService.deleteById(contactId)) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
}
