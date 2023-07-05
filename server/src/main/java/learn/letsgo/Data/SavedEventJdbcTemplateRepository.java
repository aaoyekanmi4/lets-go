package learn.letsgo.Data;

import learn.letsgo.Data.Mappers.ContactMapper;
import learn.letsgo.Data.Mappers.GroupMapper;
import learn.letsgo.Data.Mappers.SavedEventMapper;
import learn.letsgo.Models.Contact;
import learn.letsgo.Models.Group;
import learn.letsgo.Models.SavedEvent;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SavedEventJdbcTemplateRepository implements SavedEventRepository {

    private final JdbcTemplate jdbcTemplate;
    private final EventRepository eventRepository;

    public SavedEventJdbcTemplateRepository(JdbcTemplate jdbcTemplate, EventRepository eventRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventRepository = eventRepository;
    }

    public List<SavedEvent> findAll(int appUserId) {
        final String sql = "select se.saved_event_id, se.app_user_id, se.event_id "
                + "from saved_event se "
                + "where se.app_user_id =?;";

        List<SavedEvent> result = jdbcTemplate.query(sql, new SavedEventMapper(eventRepository),  appUserId);
        System.out.println(result);
        return result;
    }

    public SavedEvent findById(int savedEventId) {
        final String sql = "select se.saved_event_id, se.app_user_id, se.event_id "
                + "from saved_event se "
                + "where se.saved_event_id=?;";
        SavedEvent result = jdbcTemplate.query(sql, new SavedEventMapper(eventRepository), savedEventId).stream()
                .findFirst()
                .orElse(null);

        if (result != null) {
            addGroups(result);
            addContacts(result);
        }
        return result;
    }

    public SavedEvent findSavedEventForUser(int eventId, int appUserId) {
        final String sql = "select se.saved_event_id, se.app_user_id, se.event_id "
                + "from saved_event se "
                + "where se.app_user_id =? and se.event_id=?;";
        SavedEvent result = jdbcTemplate.query(sql, new SavedEventMapper(eventRepository),  appUserId, eventId).stream()
                .findFirst()
                .orElse(null);

        if (result != null) {
            addGroups(result);
            addContacts(result);
        }
        System.out.println(result);
        return result;
    }

    @Override
    public boolean addEventToUser(int eventId, int appUserId) {
        final String sql = "insert into saved_event(event_id, app_user_id) values (?,?);";
        return jdbcTemplate.update(sql, eventId, appUserId) > 0;
    }

    @Override
    public Integer getSavedEventId(int eventId, int appUserId)  {
        return jdbcTemplate.queryForObject("select saved_event_id from saved_event where app_user_id =? and event_id =?;",
                Integer.class, appUserId, eventId);
    }

    @Override
    @Transactional
    public boolean removeEventFromUser(int eventId, int appUserId) {
        Integer savedEventId = jdbcTemplate.queryForObject("select saved_event_id from saved_event where app_user_id =? and event_id =?;",
                Integer.class, appUserId, eventId);

        jdbcTemplate.update("delete from group_saved_event  "
                + "where saved_event_id =?;", savedEventId);
        jdbcTemplate.update("delete from contact_saved_event "
                + "where saved_event_id =?;", savedEventId);
        return jdbcTemplate.update("delete from saved_event where saved_event_id=?;", savedEventId) > 0;
    }

    @Override
    public boolean addContactToEvent(int contactId, int savedEventId) {
        final String sql ="insert into contact_saved_event(contact_id, saved_event_id) values (?,?);";
        return jdbcTemplate.update(sql, contactId, savedEventId) > 0;
    }

    @Override
    public boolean removeContactFromEvent(int contactId, int savedEventId) {
        return jdbcTemplate.update("delete from contact_saved_event "
                + "where saved_event_id =? and contact_id=?;", savedEventId, contactId) > 0;

    }

    @Override
    public boolean addGroupToEvent(int groupId, int savedEventId) {
        final String sql ="insert into group_saved_event(group_id, saved_event_id) values (?,?);";
        return jdbcTemplate.update(sql, groupId, savedEventId) > 0;
    }

    @Override
    public boolean removeGroupFromEvent(int groupId, int savedEventId) {
        return jdbcTemplate.update("delete from group_saved_event "
                + "where saved_event_id =? and group_id=?;", savedEventId, groupId) > 0;
    }

    private void addGroups(SavedEvent savedEvent) {
        final String sql = "select g.group_id, g.app_user_id, g.group_name "
                + "from `group` g "
                + "inner join group_saved_event gs on g.group_id =  gs.group_id "
                + "where gs.saved_event_id = ?;";

        List<Group> groups = jdbcTemplate.query(sql, new GroupMapper(), savedEvent.getSavedEventId());

        savedEvent.setGroups(groups);
        System.out.println(savedEvent);
    }

    private void addContacts(SavedEvent savedEvent) {
        final String sql = "select c.contact_id, c.app_user_id, c.email, "
                + "c.phone, c.first_name, c.last_name "
                + "from contact c "
                + "inner join contact_saved_event cs on c.contact_id = cs.contact_id "
                + "where cs.saved_event_id = ?";

        List<Contact> contacts = jdbcTemplate.query(sql, new ContactMapper(), savedEvent.getSavedEventId());
        savedEvent.setContacts(contacts);
    }

    @Override
    @Transactional
    public boolean batchAddContactsToEvent(List<Integer> contactIds, int savedEventId) {
        final String sql = "insert into contact_saved_event (contact_id, saved_event_id)"
                + "values (?,?);";
        int[] insertedRows = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, contactIds.get(i));
                ps.setInt(2, savedEventId);
            }

            @Override
            public int getBatchSize() {
                return contactIds.size();
            }
        });
        return insertedRows.length == contactIds.size();
    }

    @Override
    @Transactional
    public boolean batchAddGroupsToEvent(List<Integer> groupIds, int savedEventId) {
        final String sql = "insert into group_saved_event (group_id, saved_event_id)"
                + "values (?,?);";
        int[] insertedRows = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, groupIds.get(i));
                ps.setInt(2, savedEventId);
            }

            @Override
            public int getBatchSize() {
                return groupIds.size();
            }
        });
        return insertedRows.length == groupIds.size();
    }

    @Override
    public boolean batchUpdateContactsInEvent(List<Integer> contactIds, int savedEventId) {
        jdbcTemplate.update("delete from contact_saved_event where saved_event_id =?;", savedEventId);

        return batchAddContactsToEvent(contactIds, savedEventId);
    }

    @Override
    public boolean batchUpdateGroupsInEvent(List<Integer> groupIds, int savedEventId) {
        jdbcTemplate.update("delete from group_saved_event where saved_event_id =?;", savedEventId);

        return batchAddGroupsToEvent(groupIds, savedEventId);
    }

    private void addContacts (Group group) {

        final String sql ="select c.contact_id, c.app_user_id, c.email, "
                + "c.phone, c.first_name, c.last_name "
                + "from contact c "
                + "inner join group_contact gc on gc.contact_id = c.contact_id "
                + "inner join `group` g on g.group_id = gc.group_id "
                + "where g.group_id= ?;";

        List<Contact> contacts = jdbcTemplate.query(sql, new ContactMapper(), group.getGroupId());
        group.setContacts(contacts);
    }
}
