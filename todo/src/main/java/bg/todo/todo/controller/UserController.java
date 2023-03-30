package bg.todo.todo.controller;

import bg.todo.todo.model.dto.JwtResponse;
import bg.todo.todo.model.dto.UserDTO;
import bg.todo.todo.model.entity.User;
import bg.todo.todo.repository.UserRepository;
import bg.todo.todo.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        userService.registerUser(userDTO);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserDTO UserDTO) {
        User user = userRepository.findByUsername(UserDTO.getUsername());

        if (user == null || !passwordEncoder.matches(UserDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid username or password!");
        }

        String token = userService.loginUserToken(user);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
