package hr.tvz.android.animetracker.controller;

import hr.tvz.android.animetracker.dto.UserDto;
import hr.tvz.android.animetracker.model.EResponse;
import hr.tvz.android.animetracker.model.User;
import hr.tvz.android.animetracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody final UserDto userDto) {
        return userService.login(userDto);
    }

    @PostMapping("/register")
    public EResponse register(@RequestBody final UserDto userDto) {
        return userService.register(userDto);
    }

    @PostMapping("/change-password/{userId}")
    public EResponse changePassword(@PathVariable final Long userId, @RequestBody final Pair<String, String> passwordPair) {
        return userService.changePassword(userId, passwordPair);
    }

    @PostMapping("/change-profile-picture/{userId}")
    public User changeProfilePicture(@PathVariable final Long userId, @RequestPart("picture") MultipartFile picture) throws IOException {
        return userService.changeProfilePicture(userId, picture);
    }
}
