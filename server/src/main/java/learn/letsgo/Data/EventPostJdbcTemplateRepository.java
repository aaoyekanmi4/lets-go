package learn.letsgo.Data;

import learn.letsgo.Data.Mappers.EventPostMapper;
import learn.letsgo.Models.EventPost;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class EventPostJdbcTemplateRepository implements EventPostRepository{

    private final JdbcTemplate jdbcTemplate;

    public EventPostJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EventPost> findAllByEventId(int eventId) {
        final String sql = "select event_post_id, event_id, app_user_id, post_body, likes "
                + "from event_post "
                + "where event_id=?;";
        return jdbcTemplate.query(sql, new EventPostMapper(), eventId);
    }
    @Override
    public EventPost findById(int postId) {
        final String sql = "select event_post_id, event_id, app_user_id, post_body, likes "
                + "from event_post "
                + "where event_post_id=?;";
        return jdbcTemplate.query(sql, new EventPostMapper(), postId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public EventPost create(EventPost eventPost) {
        final String sql = "insert into event_post (event_id, app_user_id, post_body, likes)"
                + "values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, eventPost.getEventId());
            ps.setInt(2, eventPost.getAppUserId());
            ps.setString(3, eventPost.getPostBody());
            ps.setInt(4, eventPost.getLikes());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        eventPost.setPostId(keyHolder.getKey().intValue());
        return eventPost;
    }

    @Override
    public boolean update(EventPost eventPost) {
    final String sql = "update event_post set "
            + "event_id= ?, "
            + "app_user_id= ?, "
            + "post_body= ?, "
            + "likes= ? "
            + "where event_post_id = ?;";

        return jdbcTemplate.update(sql,
            eventPost.getEventId(),
            eventPost.getAppUserId(),
            eventPost.getPostBody(),
            eventPost.getLikes(),
            eventPost.getPostId()) > 0;
    }


    @Override
    public boolean deleteById(int postId) {
        return jdbcTemplate.update("delete from event_post where event_post_id=?;", postId) > 0;
    }
}
