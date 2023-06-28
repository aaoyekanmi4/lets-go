package learn.letsgo.Data;

import learn.letsgo.Models.Group;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupJdbcTemplateRepository implements GroupRepository{
    @Override
    public Group findById(int groupId) {
        return null;
    }

    @Override
    public Group create(Group group) {
        return null;
    }

    @Override
    public boolean update(Group group) {
        return false;
    }

    @Override
    public boolean deleteById(int groupId) {
        return false;
    }

    @Override
    public List<Group> findAllByUserId(int userId) {
        return null;
    }
}
