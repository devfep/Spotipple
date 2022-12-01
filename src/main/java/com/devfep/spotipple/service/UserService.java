package com.devfep.spotipple.service;

import com.devfep.spotipple.constants.SpottipleConstants;
import com.devfep.spotipple.dto.UserDto;
import com.devfep.spotipple.entity.Role;
import com.devfep.spotipple.entity.User;
import com.devfep.spotipple.repository.RoleRepository;
import com.devfep.spotipple.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewUser(User user) {
        boolean isSaved = false;
        Role role = roleRepository.getByRoleName(SpottipleConstants.USER_ROLE);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        if (null != user && user.getUserId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }
}
