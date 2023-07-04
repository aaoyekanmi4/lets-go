package learn.letsgo.Data;

import learn.letsgo.Data.Mappers.ContactMapper;
import learn.letsgo.Data.Mappers.GroupMapper;
import learn.letsgo.Models.Contact;
import learn.letsgo.Models.Group;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class GroupJdbcTemplateRepository implements GroupRepository{

    private final JdbcTemplate jdbcTemplate;

    public GroupJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Group> findAllByUserId(int appUserId) {
        final String sql = "select group_id, app_user_id, group_name "
                + "from `group` "
                + "where app_user_id =?;";
        return jdbcTemplate.query(sql, new GroupMapper(), appUserId);
    }

    @Override
    public List<Group> findAllBySavedEventId(int savedEventId) {
        final String sql = "select g.group_id, g.app_user_id, g.group_name "
                + "from `group` g "
                + "inner join group_saved_event gs on gs.group_id=g.group_id "
                + "where gs.saved_event_id=?;";
        return jdbcTemplate.query(sql, new GroupMapper(), savedEventId);
    }

    @Override
    public Group findById(int groupId) {
        final String sql = "select group_id, app_user_id, group_name "
                + "from `group` "
                + "where group_id =?;";

        Group result = jdbcTemplate.query(sql, new GroupMapper(), groupId).stream()
                .findFirst().orElse(null);

        if (result != null) {
            addContacts(result);
        }
        return result;
    }

    @Override
    public Group create(Group group) {
        final String sql = "insert into `group` (app_user_id, group_name)"
                + "values (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, group.getAppUserId());
            ps.setString(2, group.getName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        group.setGroupId(keyHolder.getKey().intValue());
        return group;
    }

    @Override
    public boolean update(Group group) {
        final String sql = "update `group` set "
                + "app_user_id = ?, "
                + "group_name = ? "
                + "where group_id = ?;";

        return jdbcTemplate.update(sql,
                group.getAppUserId(),
                group.getName(),
                group.getGroupId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int groupId) {
        jdbcTemplate.update("delete from group_saved_event where group_id=?;", groupId);
       jdbcTemplate.update("delete from group_contact where group_id = ?;", groupId);
       return jdbcTemplate.update("delete from `group` where group_id = ?;", groupId) > 0;
    }

    @Override
    public boolean addContactToGroup(int contactId, int groupId) {
       final String sql = "insert into group_contact(group_id, contact_id) values (?,?);";
       return jdbcTemplate.update(sql, groupId, contactId) > 0;
    }

    @Override
    @Transactional
    public boolean batchAddContactsToGroup(List<Integer> contactIds, int groupId) {
        final String sql = "insert into group_contact (contact_id, group_id)"
                + "values (?,?);";
        int[] insertedRows = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, contactIds.get(i));
                ps.setInt(2,groupId);
            }

            @Override
            public int getBatchSize() {
                return contactIds.size();
            }
        });
        return insertedRows.length == contactIds.size();
    }

    @Override
    public boolean removeContactFromGroup(int contactId, int groupId) {
        return jdbcTemplate.update("delete from group_contact where group_id =? and contact_id=?;",
                groupId, contactId) > 0;
    }

    @Override
    public boolean batchUpdateContactsInGroup(List<Integer> contactIds, int groupId) {
        jdbcTemplate.update("delete from group_contact where group_id =?;", groupId);

        return batchAddContactsToGroup(contactIds, groupId);
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
