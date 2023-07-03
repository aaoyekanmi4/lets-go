package learn.letsgo.Domain;

import learn.letsgo.Data.ContactRepository;
import learn.letsgo.Models.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> findAllForUser(int appUserId) {
        return contactRepository.findAllByUserId(appUserId);
    }

    public List<Contact> findAllBySavedEventId(int savedEventId) {
        return contactRepository.findAllBySavedEventId(savedEventId);
    }

    public Contact findContactById(int contactId) {
        return contactRepository.findById(contactId);
    }

    public Result<Contact> create(Contact contact) {
        Result<Contact> result = validate(contact);
        if (!result.isSuccess()) {
            return result;
        }
        Contact newContact = contactRepository.create(contact);
        result.setPayload(newContact);
        return result;
    }

    public Result<Contact> update(Contact contact) {
        Result<Contact> result = validate(contact);
        if (!result.isSuccess()) {
            return result;
        }

        if (contact.getContactId() <= 0) {
            result.addMessage( ResultType.INVALID, "contactId must be set for `update` operation");
            return result;
        }

        boolean contactDidUpdate = contactRepository.update(contact);

        if (!contactDidUpdate) {
            String msg = String.format("contactId: %s, not found", contact.getContactId());
            result.addMessage(ResultType.NOT_FOUND, msg);
        }
        return result;
    }

    public boolean deleteById(int contactId) {
        return contactRepository.deleteById(contactId);
    }

    private Result<Contact> validate(Contact contact) {
        Result<Contact> result = new Result<>();
        if (contact == null) {
            result.addMessage(ResultType.INVALID, "Contact cannot be null");
            return result;
        }
        if (Helpers.isNullOrBlank(contact.getFirstName())
                || Helpers.isNullOrBlank(contact.getLastName())) {
            result.addMessage(ResultType.INVALID, "First and last name are required");
        }
        if (Helpers.isNullOrBlank(contact.getPhone())) {
            result.addMessage(ResultType.INVALID, "Phone number is required");
        }

        if (Helpers.isNullOrBlank(contact.getEmail())) {
            result.addMessage(ResultType.INVALID, "Email is required");
            return result;
        }

        if (!Helpers.isValidEmail(contact.getEmail())) {
            result.addMessage(ResultType.INVALID, "User must enter a valid email");
        }

        return result;
    }
}
