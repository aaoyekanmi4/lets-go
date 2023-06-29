package learn.letsgo.Data;

import learn.letsgo.Models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserJdbcTemplateRepositoryTest {

    @Autowired
    AppUserJdbcTemplateRepository appUserRepository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAppUserByUsername() {
        AppUser actual = appUserRepository.findByUsername("jay@dev10.com");
        assertNotNull(actual);
        assertEquals("Jay", actual.getFirstName());
        assertEquals(2, actual.getGroups().size());
        assertEquals(1, actual.getEvents().size());
        assertEquals(1, actual.getContacts().size());
    }

    @Test
    void shouldCreateAppUser() {
        AppUser actual = appUserRepository.create(makeAppUser());
        assertNotNull(actual);
        assertEquals("Bill", actual.getFirstName());
    }

    @Test
    void shouldUpdateAppUser() {
        AppUser appUserToUpdate = appUserRepository.findByUsername("arit@dev10.com");
        appUserToUpdate.setPhone("5555555");
        boolean actual = appUserRepository.update(appUserToUpdate);
        assertTrue(actual);
        AppUser updatedUser = appUserRepository.findByUsername("arit@dev10.com");
        assertEquals("5555555", updatedUser.getPhone());
    }

    @Test
    void addEventToUser() {
        int initialCount =  appUserRepository.findByUsername("eric@dev10.com").getEvents().size();
        boolean actual = appUserRepository.addEventToUser(1,1);
        assertTrue(actual);
        int resultCount = appUserRepository.findByUsername("eric@dev10.com").getEvents().size();
        assertEquals(1, resultCount - initialCount);
    }

    @Test
    void removeEventFromUser() {
        int initialCount =  appUserRepository.findByUsername("eric@dev10.com").getEvents().size();
        boolean actual = appUserRepository.removeEventFromUser(3,1);
        assertTrue(actual);
        int resultCount = appUserRepository.findByUsername("eric@dev10.com").getEvents().size();
        assertEquals(-1, resultCount - initialCount);
    }

    AppUser makeAppUser () {
        AppUser appUser = new AppUser(10, "bill@dev10.com",
                "password", "bill@dev10.com", "4444444",
                "Bill", "Bellamy", true, List.of("USER", "ADMIN"));
       return appUser;
    }
}