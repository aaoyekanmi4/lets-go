package learn.letsgo.Data;

import learn.letsgo.App;
import learn.letsgo.Models.AppUser;
import org.springframework.transaction.annotation.Transactional;

public interface AppUserRepository {

    @Transactional
    AppUser findById(int appUserId);
    @Transactional
    AppUser findByUsername(String username);

    @Transactional
    AppUser create(AppUser user);

    @Transactional
    boolean update(AppUser user);

    boolean addEventToUser(int eventId, int appUserId);

    boolean removeEventFromUser(int eventId, int appUserId);
}
