package com.rohov.tagsoft.service.user.impl;

import com.rohov.tagsoft.configuration.db.DBContextHolder;
import com.rohov.tagsoft.configuration.db.DBType;
import com.rohov.tagsoft.entity.User;
import com.rohov.tagsoft.exception.ErrorMessages;
import com.rohov.tagsoft.exception.MissedPropertyException;
import com.rohov.tagsoft.model.Country;
import com.rohov.tagsoft.repository.UserRepository;
import com.rohov.tagsoft.service.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final String errorMsg = String.format(ErrorMessages.USER_WITH_EMAIL_NOT_FOUND, username);
        return userRepository.findByEmailAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException(errorMsg));
    }

    @Override
    public User create(User user) {
        String password = encoder.encode(user.getPassword());

        user.setPassword(password);

        checkProperties(user);

        return userRepository.save(user);
    }

    @Override
    public User update(Long userId, User user) {
        User current = findById(userId);
        String password = encoder.encode(user.getPassword());

        current.setName(user.getName());
        current.setPassword(password);
        current.setUpdatedDate(user.getUpdatedDate());

        checkProperties(user);

        return userRepository.save(current);
    }

    @Override
    public User patch(Long userId, User user) {
        User current = findById(userId);

        if (Objects.nonNull(user.getName())) {
            current.setName(user.getName());
        }
        if (Objects.nonNull(user.getPassword())) {
            String password = encoder.encode(user.getPassword());
            current.setPassword(password);
        }
        if (Objects.nonNull(user.getCountry())) {
            current.setCountry(user.getCountry());
        }
        if (Objects.nonNull(user.getUpdatedDate())) {
            current.setUpdatedDate(user.getUpdatedDate());
        }
        if (current.getCountry().equals(Country.Canada)) {
            if (Objects.nonNull(user.getProvince())) {
                current.setProvince(user.getProvince());
            }
            if (Objects.nonNull(user.getCity())) {
                current.setCity(user.getCity());
            }
        }
        if (current.getCountry().equals(Country.USA)) {
            if (!user.getStates().isEmpty()) {
                current.setStates(user.getStates());
            }
        }

        return userRepository.save(current);
    }

    @Override
    public void delete(Long userId) {
        User current = findById(userId);

        current.setDeleted(true);

        userRepository.save(current);
    }

    private User findById(Long userId) {
        final String errorMsg = String.format(ErrorMessages.USER_WITH_ID_NOT_FOUND, userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(errorMsg));
    }

    private void checkProperties(User user) {
        switch (user.getCountry()) {
            case USA: {
                if (user.getStates().isEmpty()) {
                    throw new MissedPropertyException("");
                }
            }
            case Canada: {
                if (Objects.isNull(user.getProvince()) || Objects.isNull(user.getCity())) {
                    throw new MissedPropertyException("");
                }
            }
        }
    }
}
