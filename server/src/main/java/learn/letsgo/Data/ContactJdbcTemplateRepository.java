package learn.letsgo.Data;

import learn.letsgo.Models.Contact;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactJdbcTemplateRepository implements ContactRepository{
    @Override
    public Contact findById(int contactId) {
        return null;
    }

    @Override
    public Contact create(Contact contact) {
        return null;
    }

    @Override
    public boolean update(Contact contact) {
        return false;
    }

    @Override
    public boolean delete(Contact contact) {
        return false;
    }

    @Override
    public List<Contact> findAll(int appUserId) {
        return null;
    }
}
