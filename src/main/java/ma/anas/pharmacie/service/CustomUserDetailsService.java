package ma.anas.pharmacie.service;

import lombok.RequiredArgsConstructor;
import ma.anas.pharmacie.model.User;
import ma.anas.pharmacie.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())
                .disabled(!u.isEnabled())
                .authorities(u.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                        .toList())
                .build();
    }
}