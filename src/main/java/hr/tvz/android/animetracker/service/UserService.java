package hr.tvz.android.animetracker.service;

import hr.tvz.android.animetracker.dto.UserDto;
import hr.tvz.android.animetracker.model.EResponse;
import hr.tvz.android.animetracker.model.User;
import hr.tvz.android.animetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final Path pictureFolder = Paths.get("db/pictures/user");

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public User login(final UserDto userDto) {
        final Optional<User> user = userRepository.findByUsername(userDto.getUsername());
        if(user.filter(u -> passwordEncoder.matches(userDto.getPassword(), u.getPassword())).isPresent()) {
            return user.get();
        }

        return new User();
    }

    public EResponse register(final UserDto userDto) {
        if(userRepository.existsUserByUsername(userDto.getUsername())) {
            return EResponse.USERNAME_TAKEN;
        }

        final String password = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(password);

        final User user = modelMapper.map(userDto, User.class);
        user.setJoinDate(LocalDate.now().toString());

        userRepository.save(user);
        return EResponse.SUCCESS;
    }

    public EResponse changePassword(final Long userId, final Pair<String, String> passwordPair) {
        final Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            return EResponse.ERROR;
        }

        if(!passwordEncoder.matches(passwordPair.getFirst(), user.get().getPassword())) {
            return EResponse.ERROR;
        }

        user.get().setPassword(passwordEncoder.encode(passwordPair.getSecond()));
        userRepository.save(user.get());
        return EResponse.SUCCESS;
    }

    public User changeProfilePicture(final Long userId, final MultipartFile picture) throws IOException {
        final Optional<User> usero = userRepository.findById(userId);
        if(usero.isEmpty()) {
            return null;
        }

        final User user = usero.get();
        final String pictureFileName = generateFilename(LocalDateTime.now(), picture.getOriginalFilename());
        Files.copy(picture.getInputStream(), pictureFolder.resolve(pictureFileName));

        user.setProfilePicture(pictureFileName);
        return userRepository.save(user);
    }

    private String generateFilename(final LocalDateTime publishDate, final String originalFilename) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return publishDate.format(formatter) + "-" + originalFilename;
    }

}
