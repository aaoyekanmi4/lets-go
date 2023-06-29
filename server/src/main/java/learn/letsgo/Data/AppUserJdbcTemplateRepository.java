package learn.letsgo.Data;
import learn.letsgo.Data.Mappers.AppUserMapper;
import learn.letsgo.Data.Mappers.ContactMapper;
import learn.letsgo.Data.Mappers.EventMapper;
import learn.letsgo.Data.Mappers.GroupMapper;
import learn.letsgo.Models.AppUser;
import learn.letsgo.Models.Contact;
import learn.letsgo.Models.Event;
import learn.letsgo.Models.Group;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public AppUser findByUsername(String username) {
        List<String> roles = getRolesByUsername(username);

        final String sql = "select app_user_id, username, password_hash, email, phone, first_name, last_name, enabled "
                + "from app_user "
                + "where username = ?;";

        AppUser result = jdbcTemplate.query(sql, new AppUserMapper(roles), username)
                .stream()
                .findFirst().orElse(null);

        if (result != null) {
            addEvents(result);
            addGroups(result);
            addContacts(result);
        }
        return result;
    }

    @Override
    @Transactional
    public AppUser create(AppUser user) {

        final String sql = "insert into app_user (username, password_hash,email, phone, first_name, last_name) " +
                "values (?, ?, ?, ?, ?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getFirstName());
            ps.setString(6, user.getLastName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setAppUserId(keyHolder.getKey().intValue());

        updateRoles(user);

        return user;
    }

    @Override
    @Transactional
    public boolean update(AppUser user) {

        final String sql = "update app_user set "
                + "username = ?, "
                + "first_name = ?, "
                + "last_name = ?, "
                + "email = ?, "
                + "phone = ?, "
                + "enabled = ? "
                + "where app_user_id = ?";

        boolean updated = jdbcTemplate.update(sql,
                user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getPhone(), user.isEnabled(), user.getAppUserId()) > 0;

        if (updated) {
            updateRoles(user);
        }

        return updated;
    }

    @Override
    public boolean addEventToUser(int eventId, int appUserId) {
        final String sql = "insert into saved_event(event_id, app_user_id) values (?,?);";
        return jdbcTemplate.update(sql, eventId, appUserId) > 0;
    }

    @Override
    public boolean removeEventFromUser(int eventId, int appUserId) {
        return jdbcTemplate.update("delete from saved_event where app_user_id =? and event_id =?", appUserId, eventId) > 0;
    }

    private void updateRoles(AppUser user) {
        // delete all roles, then re-add
        jdbcTemplate.update("delete from app_user_role where app_user_id = ?;", user.getAppUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if (authorities == null) {
            return;
        }

        for (GrantedAuthority role : authorities) {
            String sql = "insert into app_user_role (app_user_id, app_role_id) "
                    + "select ?, app_role_id from app_role where `name` = ?;";
            jdbcTemplate.update(sql, user.getAppUserId(), role.getAuthority());
        }
    }

    private List<String> getRolesByUsername(String username) {
        final String sql = "select r.name "
                + "from app_user_role ur "
                + "inner join app_role r on ur.app_role_id = r.app_role_id "
                + "inner join app_user au on ur.app_user_id = au.app_user_id "
                + "where au.username = ?";
        return jdbcTemplate.query(sql, (rs, rowId) -> rs.getString("name"), username);
    }
//    TODO: Add groups, contacts to user
    private void addEvents (AppUser appUser) {
        final String sql ="select e.event_id, e.category, e.event_name, e.image_url, e.description, e.event_date, "
                + "e.source, e.source_id, e.event_link, e.venue_id, "
                + "v.venue_name, v.address, v.city, v.state, v.country, v.zipcode "
                + "from event e "
                + "inner join venue v on e.venue_id = v.venue_id "
                + "inner join saved_event se on se.event_id = e.event_id "
                + "inner join app_user a on a.app_user_id = se.app_user_id "
                + "where a.app_user_id= ?;";
        List<Event> events = jdbcTemplate.query(sql, new EventMapper(), appUser.getAppUserId());
        appUser.setEvents(events);
    }

    private void addGroups (AppUser appUser) {
        final String sql ="select g.group_id, g.app_user_id, g.group_name "
                + "from `group` g "
                + "where g.app_user_id= ?;";
        List<Group> groups = jdbcTemplate.query(sql, new GroupMapper(), appUser.getAppUserId());
        appUser.setGroups(groups);
    }

    private void addContacts (AppUser appUser) {
        final String sql ="select c.contact_id, c.app_user_id, c.email, "
                + "c.phone, c.first_name, c.last_name "
                + "from contact c "
                + "where c.app_user_id= ?;";
        List<Contact> contacts = jdbcTemplate.query(sql, new ContactMapper(), appUser.getAppUserId());
        appUser.setContacts(contacts);
    }
}
