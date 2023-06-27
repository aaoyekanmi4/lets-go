package learn.letsgo.Data;

import learn.letsgo.Models.Group;

import java.util.List;

public interface GroupRepository {
    Group findById(int groupId);

    Group create(Group group);

    boolean update(Group group);

    boolean deleteById(int groupId);

    List<Group> findAllByUserId(int userId);
}
