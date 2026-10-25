package ma.anas.pharmacie.service;

import lombok.RequiredArgsConstructor;
import ma.anas.pharmacie.model.Role;
import ma.anas.pharmacie.model.User;
import ma.anas.pharmacie.repository.RoleRepository;
import ma.anas.pharmacie.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        for (String r : List.of("ADMIN", "PHARMACIEN", "CAISSIER", "CLIENT"))
            if (roleRepo.findByName(r).isEmpty()) roleRepo.save(new Role(null, r));

        createUser("admin", "admin123", "ADMIN");
        createUser("pharma", "pharma123", "PHARMACIEN");
        createUser("caisse", "caisse123", "CAISSIER");
        createUser("client", "client123", "CLIENT");
    }

    private void createUser(String username, String pwd, String role) {
        if (userRepo.findByUsername(username).isPresent()) return;
        User u = new User();
        u.setUsername(username);
        u.setPassword(encoder.encode(pwd));
        u.getRoles().add(roleRepo.findByName(role).orElseThrow());
        userRepo.save(u);
    }
}