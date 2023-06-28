package learn.letsgo.Data;

import learn.letsgo.Models.Contact;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContactRepository {
    Contact findById(int contactId);

    Contact create(Contact contact);

    boolean update(Contact contact);

    @Transactional
    boolean deleteById(int contactId);

    List<Contact> findAllByUserId(int appUserId);
}
