package learn.letsgo.Data.Mappers;

import learn.letsgo.Models.EventPost;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EventPostMapper implements RowMapper<EventPost> {
    @Override
    public EventPost mapRow(ResultSet rs, int i) throws SQLException {
        LocalDateTime postDateTime = rs.getTimestamp("post_date").toLocalDateTime();
        EventPost post = new EventPost();
        post.setPostId(rs.getInt("event_post_id"));
        post.setEventId(rs.getInt("event_id"));
        post.setAuthor(rs.getString("author"));
        post.setPostDate(postDateTime);
        post.setAppUserId(rs.getInt("app_user_id"));
        post.setPostBody(rs.getString("post_body"));
        post.setLikes(rs.getInt("likes"));

        return post;
    }
}
