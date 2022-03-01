package by.teachmeskills.crud.service;

import by.teachmeskills.crud.model.User;
import by.teachmeskills.crud.util.MySqlDriverManager;
import by.teachmeskills.crud.util.MySqlScriptsManager;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.Value;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Value
public class UserService implements Service<Integer, User>, AutoCloseable {
    private static final MySqlDriverManager mySqlDriverManager = new MySqlDriverManager();
    private static final MySqlScriptsManager mySqlScriptsManager = new MySqlScriptsManager();

    @Override
    public boolean create(@NonNull User user) {
        int state = SQL_STATEMENT_RETURN_NOTHING;
        @NonNull String query = mySqlScriptsManager.getQuery("create-user");
        try {
            @Cleanup PreparedStatement preparedStatement = mySqlDriverManager.prepareStatement(query);
            preparedStatement.setString(FIRST_PREPARED_STATEMENT_PARAMETER, user.getName());
            preparedStatement.setString(SECOND_PREPARED_STATEMENT_PARAMETER, user.getEmail());
            state = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state != SQL_STATEMENT_RETURN_NOTHING;
    }

    @Override
    public List<User> findAll() {
        @NonNull String query = mySqlScriptsManager.getQuery("find-all-users");
        try {
            @Cleanup PreparedStatement preparedStatement = mySqlDriverManager.prepareStatement(query);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User userOccurrence = getUserOccurrence(resultSet);
                userList.add(userOccurrence);
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private User getUserOccurrence(@NonNull ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        return new User(id, name, email);
    }

    @Override
    public User findById(@NonNull Integer id) {
        @NonNull String query = mySqlScriptsManager.getQuery("find-user-by-id");
        try {
            @Cleanup PreparedStatement preparedStatement = mySqlDriverManager.prepareStatement(query);
            preparedStatement.setInt(FIRST_PREPARED_STATEMENT_PARAMETER, id);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return getUserOccurrence(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User update(User user) {
        int state = SQL_STATEMENT_RETURN_NOTHING;
        @NonNull String query = mySqlScriptsManager.getQuery("update-user");
        try {
            @Cleanup PreparedStatement preparedStatement = mySqlDriverManager.prepareStatement(query);
            preparedStatement.setString(FIRST_PREPARED_STATEMENT_PARAMETER, user.getName());
            preparedStatement.setString(SECOND_PREPARED_STATEMENT_PARAMETER, user.getEmail());
            preparedStatement.setInt(THIRD_PREPARED_STATEMENT_PARAMETER, user.getId());
            state = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state != SQL_STATEMENT_RETURN_NOTHING ? user : null;
    }

    @Override
    public boolean deleteAll() {
        int state = SQL_STATEMENT_RETURN_NOTHING;
        @NonNull String query = mySqlScriptsManager.getQuery("delete-all-users");
        try {
            @Cleanup PreparedStatement preparedStatement = mySqlDriverManager.prepareStatement(query);
            state = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state != SQL_STATEMENT_RETURN_NOTHING;
    }

    @Override
    public boolean deleteById(@NonNull Integer id) {
        int state = SQL_STATEMENT_RETURN_NOTHING;
        @NonNull String query = mySqlScriptsManager.getQuery("delete-user-by-id");
        try {
            @Cleanup PreparedStatement preparedStatement = mySqlDriverManager.prepareStatement(query);
            preparedStatement.setInt(FIRST_PREPARED_STATEMENT_PARAMETER, id);
            state = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state != SQL_STATEMENT_RETURN_NOTHING;
    }

    @Override
    public void close() {
        mySqlDriverManager.close();
    }
}