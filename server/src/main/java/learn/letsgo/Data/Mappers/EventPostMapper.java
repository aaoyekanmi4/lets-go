package learn.letsgo.Data.Mappers;

import learn.letsgo.Models.EventPost;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EventPostMapper implements RowMapper<EventPost> {
    @Override
    public EventPost mapRow(ResultSet rs, int i) throws SQLException {
        AppUserMapper userMapper = new AppUserMapper(List.of("USER"));
        EventMapper eventMapper = new EventMapper();

        EventPost post = new EventPost();
        post.setPostId(rs.getInt("event_post_id"));
        post.setEvent(eventMapper.mapRow(rs, i));
        post.setAppUser(userMapper.mapRow(rs, i));
        post.setPostBody(rs.getString("post_body"));
        post.setLikes(rs.getInt("likes"));

        return post;
    }
}
