package io.github.tn1.server.entity.refresh_token;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
	Optional<RefreshToken> findByToken(String token);
}
