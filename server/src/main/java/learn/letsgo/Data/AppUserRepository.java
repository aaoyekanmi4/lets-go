package learn.letsgo.Data;

import learn.letsgo.App;
import learn.letsgo.Models.AppUser;
import org.springframework.transaction.annotation.Transactional;

public interface AppUserRepository extends BridgeTableRepository<AppUser> {

    @Transactional
    AppUser findById(int appUserId);
    @Transactional
    AppUser findByUsername(String username);

    @Transactional
    AppUser create(AppUser user);

    @Transactional
    boolean update(AppUser user);
}
