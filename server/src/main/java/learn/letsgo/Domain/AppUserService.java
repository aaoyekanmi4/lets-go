package learn.letsgo.Domain;

import learn.letsgo.Data.AppUserRepository;
import learn.letsgo.Models.AppUser;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository,
                          PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repository.findByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return appUser;
    }

    public Result<AppUser> create(String username, String password,
                                  String email, String phone, String firstName, String lastName,boolean isEnabled) {
        Result<AppUser> result = validate(username, password);
        if (!result.isSuccess()) {
            return result;
        }

        password = encoder.encode(password);

        AppUser appUser = new AppUser(0, username, password,email, phone, firstName, lastName,  isEnabled, List.of("USER"));

        try {
            appUser = repository.create(appUser);
            result.setPayload(appUser);
        } catch (DuplicateKeyException e) {
            String errorMsg = e.getMessage();
            String[] parts = errorMsg.split(" ");
            String end = parts[parts.length -1].split("\\.")[1];
            String field  = end.substring(0, end.length()-1);
            field = field.equals("phone") ? "phone number" : field;

            result.addMessage(ResultType.INVALID, String.format("The %s entered is taken by another user.", field));
        }

        return result;
    }

    private Result<AppUser> validate(String username, String password) {
        Result<AppUser> result = new Result<>();
        if (username == null || username.isBlank()) {
            result.addMessage(ResultType.INVALID, "username is required");
            return result;
        }

        if (password == null) {
            result.addMessage(ResultType.INVALID, "password is required");
            return result;
        }

        if (username.length() > 50) {
            result.addMessage(ResultType.INVALID, "username must be less than 50 characters");
        }

        if (!isValidPassword(password)) {
            result.addMessage(ResultType.INVALID,
                    "password must be at least 8 character and contain a digit," +
                            " a letter, and a non-digit/non-letter");
        }

        return result;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        return digits > 0 && letters > 0 && others > 0;
    }
}