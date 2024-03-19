package LogInSignUp.loginsignup.dao;

import LogInSignUp.loginsignup.shared.UserDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UsersDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UsersDao(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    public UserDto saveUser(UserDto userDetails) {
        String sql = "INSERT INTO users (firstName, lastName, email, userId, encryptedPassword) " +
                "VALUES (:firstName, :lastName, :email, :userId, :encryptedPassword)";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("firstName", userDetails.getFirstName())
                .addValue("lastName", userDetails.getLastName())
                .addValue("email", userDetails.getEmail())
                .addValue("userId", userDetails.getUserId())
                .addValue("encryptedPassword", userDetails.getEncryptedPassword());

        try {
            int result = namedParameterJdbcTemplate.update(sql, parameters);
            System.out.println("result::::::::::::::" + result);
            if (result == 1) {
                return userDetails; // Return the saved user details
            } else {
                return null; // Failed to save user
            }
        } catch (DataAccessException e) {
            // Handle database exception
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDto findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = :email";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("email", email);

        return namedParameterJdbcTemplate.queryForObject(
                sql,
                parameters,
                new BeanPropertyRowMapper<>(UserDto.class)
        );
    }
}