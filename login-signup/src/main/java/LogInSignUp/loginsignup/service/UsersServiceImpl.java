package LogInSignUp.loginsignup.service;

import LogInSignUp.loginsignup.dao.UsersDao;
import LogInSignUp.loginsignup.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {


    UsersDao usersDao;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersServiceImpl(UsersDao usersDao, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.usersDao = usersDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //---------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto savedUser = usersDao.saveUser(userDetails);
        if (savedUser != null) {
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //------------------------------------------------------------------------------------------------------

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto aUser= usersDao.findByEmail(username);

        if(aUser == null) throw new UsernameNotFoundException(username);

        return new User(aUser.getEmail(),aUser.getEncryptedPassword(),
                true, true, true, true, new ArrayList<>());
    }

    //----------------------------------------------------------------------------------------------------------0
    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserDto gettingUser = usersDao.findByEmail(email);

        if(gettingUser == null) throw new UsernameNotFoundException(email);

        return new ModelMapper().map(gettingUser, UserDto.class);
    }

}
