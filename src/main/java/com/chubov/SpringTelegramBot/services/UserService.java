package com.chubov.SpringTelegramBot.services;

import com.chubov.SpringTelegramBot.DTO.RoleDTO;
import com.chubov.SpringTelegramBot.DTO.UserDTO;
import com.chubov.SpringTelegramBot.models.Role;
import com.chubov.SpringTelegramBot.models.User;
import com.chubov.SpringTelegramBot.repositories.RoleRepository;
import com.chubov.SpringTelegramBot.repositories.UserRepository;
import com.chubov.SpringTelegramBot.telegramBotStarter.utils.Buttons;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    //  Add a new User to database
    public User saveNewUser(Long userId, String username, String firstName) {
        Optional<User> user = userRepository.findByTelegramId(userId);
        if (user.isPresent()) {
            //  Check if user data is updated, update database
            UserDTO validUserDto = new UserDTO(userId, username, firstName, user.get().getRoles().stream().map(this::convertToRoleDTO).collect(Collectors.toSet()));
            UserDTO userDTO = convertToUserDTO(user.get());
            if (!userDTO.equals(validUserDto) && userDTO.getTelegramId().equals(validUserDto.getTelegramId())) {
                //  Update data
                Set<Role> roles = user.get().getRoles();
                User updatedUser = convertToUser(validUserDto);
                updatedUser.setRoles(roles);
                updatedUser.setUserId(user.get().getUserId());
                return userRepository.save(updatedUser);
            } else {
                log.info("User already exist");
                return user.get();
            }
        } else {
            Set<Role> role = roleRepository.findByRoleName("USER");
            User newUser = new User(userId, username, firstName, role);
            newUser.setRoles(role);
            return userRepository.save(newUser);
        }
    }


    //  Get one user
    public Optional<User> getUser(Long userId) {
        return userRepository.findByTelegramId(userId);
    }

    //  Get list of users
    public List<UserDTO> getAllUser() {
        return userRepository.findAll().stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }

    //  Get user role by tg.Id
    public String getUserRole(Long telegramId) {
        Optional<User> user = userRepository.findByTelegramId(telegramId);
        if (user.isPresent()) {
            return user.get().getRoles().stream().findAny().get().getRoleName();
        }
        throw new EntityNotFoundException();
    }

    public SendMessage startBot(String receivedMessage, Long chatId, Long userId, String username, String firstName) {
//        Optional<User> user = getUser(userId);
//        String role = getUserRole(userId);

        //  Send Text + button response
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Здравствуйте, " + firstName + "! " + "\nВы являетесь администратором бота." + "\nНажмите на кнопку ниже, для настроки бота.");
        message.setReplyMarkup(Buttons.inlineMarkup(userId));
        return message;
    }

    public SendMessage sayHello(long chatId, String firstName, Long userId) {
        Optional<User> user = userRepository.findByTelegramId(userId);
        SendMessage message = new SendMessage();
        if (user.isPresent()) {
            String role = user.get().getRoles().stream().findAny().get().getRoleName();
            if (role.equals("ADMIN")) {
                message.setChatId(chatId);
                message.setText("Hello, " + role + "! I'm a Telegram bot.");
            } else if (role.equals("CUSTOMER")) {
                message.setChatId(chatId);
                message.setText("Hello, " + role + "! I'm a Telegram bot.");
            } else if (role.equals("USER")) {
                message.setChatId(chatId);
                message.setText("Hello, " + firstName + " :ROLES: " + role + "! I'm a Telegram bot.");
            }

        } else {
            log.info("User doesn't exist");
        }
        return message;
    }

    //  ModelMapper methods. Converters.

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private Role convertToRole(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }

    private RoleDTO convertToRoleDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public void setCustomer(UserDTO request) {
        //  Найти юзера по ID, изменить его роль на Customer.
        Optional<User> user = getUser(request.getTelegramId());
        if (user.isPresent()) {
            Set<Role> role = roleRepository.findByRoleName("CUSTOMER");
            user.get().setRoles(role);
            userRepository.save(user.get());
            return;
        }
        throw new EntityNotFoundException();
    }

    //  Return count of users (people in db with a role - USER)
    public Integer getCountUsers() {
        return userRepository.countUsersByRoles(roleRepository.findByRoleName("USER").stream().findAny().get());
    }

}
