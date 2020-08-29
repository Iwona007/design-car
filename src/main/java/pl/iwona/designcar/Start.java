package pl.iwona.designcar;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.iwona.designcar.model.Role;
import pl.iwona.designcar.model.RoleName;
import pl.iwona.designcar.model.User;
import pl.iwona.designcar.repository.RoleRepository;
import pl.iwona.designcar.repository.UserRepository;

@Component
public class Start {

    RoleRepository roleRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public Start(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Role admin = new Role(RoleName.ROLE_ADMIN);
        Role ruser = new Role(RoleName.ROLE_USER);

        roleRepository.save(admin);
        roleRepository.save(ruser);

        Set<Role> roles = new HashSet<>();
        Role role1 = new Role();
        role1.setName(RoleName.ROLE_ADMIN);
        Role role2 = new Role();
        role2.setName(RoleName.ROLE_USER);

        roles.add(admin);
        roles.add(ruser);

        User user = new User("Admin", "email1@eamil.com",
                passwordEncoder.encode("123456"), roles);
        user.setRoles(roles);
        User user2 = new User("User", "email2@eamil.com", passwordEncoder.encode("654321"),
                roles.stream().filter(user1 -> user1.equals(ruser)).collect(Collectors.toSet()));
        user.setRoles(roles);
        userRepository.save(user);
        userRepository.save(user2);
    }
}
