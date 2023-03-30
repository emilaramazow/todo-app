package bg.todo.todo.service;

import bg.todo.todo.model.dto.UserDTO;
import bg.todo.todo.model.entity.User;

public interface UserService {

    User registerUser(UserDTO userDTO);

    String loginUserToken(User user);

    User findById(Long id);
}
