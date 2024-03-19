package LogInSignUp.loginsignup.service;

import LogInSignUp.loginsignup.shared.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
    //UserDto createUser(UserDto userDetails);
    ResponseEntity<UserDto> createUser(UserDto userDetails);
    UserDto getUserDetailsByEmail(String email);
}