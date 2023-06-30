package learn.letsgo.Data;

import learn.letsgo.Data.Mappers.ContactMapper;
import learn.letsgo.Data.Mappers.GroupMapper;
import learn.letsgo.Models.Contact;
import learn.letsgo.Models.Group;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ContactJdbcTemplateRepository implements ContactRepository{

    private final JdbcTemplate jdbcTemplate;

    public ContactJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Contact> findAllByUserId(int appUserId) {
        final String sql = "select contact_id, first_name, last_name,email, phone, app_user_id "
                + "from contact where app_user_id=? limit 1000;";
        return jdbcTemplate.query(sql, new ContactMapper(), appUserId);
    }

    @Override
    public List<Contact> findAllBySavedEventId(int savedEventId) {
        final String sql = "select c.contact_id, c.first_name, c.last_name, c.email, c.phone, c.app_user_id "
                + "from contact c "
                + "inner join contact_saved_event cs on cs.contact_id=c.contact_id "
                + "where cs.saved_event_id=?;";
        return jdbcTemplate.query(sql, new ContactMapper(), savedEventId);
    }

    @Override
    public Contact findById(int contactId) {
        final String sql = "select contact_id, first_name, last_name,email, phone, app_user_id "
                + "from contact where contact_id=?;";

        Contact result = jdbcTemplate.query(sql, new ContactMapper(), contactId).stream()
                .findFirst()
                .orElse(null);

        if (result != null) {
            addGroups(result);
        }
        return result;
    }

    @Override
    public Contact create(Contact contact) {
        final String sql = "insert into contact (app_user_id, email, phone, first_name, last_name)"
                + "values (?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, contact.getAppUserId());
            ps.setString(2, contact.getEmail());
            ps.setString(3, contact.getPhone());
            ps.setString(4, contact.getFirstName());
            ps.setString(5, contact.getLastName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        contact.setContactId(keyHolder.getKey().intValue());
        return contact;
    }

    @Override
    public boolean update(Contact contact) {

        final String sql = "update contact set "
                + "app_user_id = ?, "
                + "email = ?, "
                + "phone = ?, "
                + "first_name = ?, "
                + "last_name = ? "
                + "where contact_id = ?;";

        return jdbcTemplate.update(sql,
                contact.getAppUserId(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getContactId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int contactId) {
        jdbcTemplate.update("delete from contact_saved_event where contact_id=?;", contactId);
        jdbcTemplate.update("delete from group_contact where contact_id = ?;", contactId);
        return jdbcTemplate.update("delete from contact where contact_id = ?;", contactId) > 0;
    }

    private void addGroups(Contact contact) {
        final String sql = "select g.group_id, g.group_name, g.app_user_id, "
                + "gc.contact_id "
                + "from `group` g "
                + "inner join group_contact gc on gc.group_id=g.group_id "
                + "where gc.contact_id = ?";

        List<Group> groups = jdbcTemplate.query(sql, new GroupMapper(), contact.getContactId());
        contact.setGroups(groups);
    }
}
