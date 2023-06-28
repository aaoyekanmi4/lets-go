package learn.letsgo.Data.Mappers;

import learn.letsgo.Models.Group;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        Group group = new Group();
        group.setGroupId(rs.getInt("group_id"));
        group.setAppUserId(rs.getInt("app_user_id"));
        group.setName(rs.getString("group_name"));
        return group;
    }
}
