package nsu_group.ui;

import java.util.ArrayList;

public interface UserRepository {
    ArrayList<User> findAll();

    User save(User user);

    User check(User user);

    User findUser(int id);
}
