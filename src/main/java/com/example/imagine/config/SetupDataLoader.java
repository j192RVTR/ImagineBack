package com.example.imagine.config;

import com.example.imagine.entity.Role;
import com.example.imagine.entity.UserEntity;
import com.example.imagine.repository.RoleRepository;
import com.example.imagine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private Boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup)
            return;
        Role userRole = createRoleIfNotFound(Role.ROLE_USER);
        Role adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);

        createUserIfNotFound("user", userRole);
        createUserIfNotFound("admin", adminRole);

        alreadySetup = true;
    }

    @Transactional
    private Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role = roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    private UserEntity createUserIfNotFound(final String name, final Role role) {
        UserEntity user = userRepository.findByEmail(name);
        if (user == null) {
            user = new UserEntity(name, "$2y$10$3rsGofo9qyJrhb/r/vccSezf8p8KNJ.GYoYb6mgXJg.a6iJBW.CJG");
            user.setRoles(Set.of(role));
            user = userRepository.save(user);
        }
        return user;
    }


}
