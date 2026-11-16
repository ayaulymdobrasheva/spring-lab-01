package kz.iitu.springlab.service;
import kz.iitu.springlab.repository.AppUser;
import kz.iitu.springlab.repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService
        implements UserDetailsService {

    private final AppUserRepository users;

    public DatabaseUserDetailsService(
            AppUserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username) {

        AppUser u = users.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(username)
                );

        return User.withUsername(u.getUsername())
                .password(u.getPasswordHash())
                .authorities(
                        u.getRoles().stream()
                                .map(r -> "ROLE_" + r)
                                .toArray(String[]::new)
                )
                .disabled(!u.isEnabled())
                .build();
    }
}