package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.dto.UserDto;
import recipes.security.UserService;

import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }
}
