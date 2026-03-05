package br.com.licitai.iam.domain.port.out;

import br.com.licitai.iam.domain.Email;
import br.com.licitai.iam.domain.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsById(UUID id);
    void deleteById(UUID id);

    boolean existsByEmail(Email email, UUID uuid);
}
