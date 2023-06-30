package learn.letsgo.Data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SavedEventJdbcTemplateRepository implements SavedEventRepository {

    private final JdbcTemplate jdbcTemplate;

    public SavedEventJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addEventToUser(int eventId, int appUserId) {
        final String sql = "insert into saved_event(event_id, app_user_id) values (?,?);";
        return jdbcTemplate.update(sql, eventId, appUserId) > 0;
    }

    @Override
    @Transactional
    public boolean removeEventFromUser(int eventId, int appUserId) {
        int savedEventId = jdbcTemplate.queryForObject("select saved_event_id from saved_event where app_user_id =? and event_id =?;",
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
}
