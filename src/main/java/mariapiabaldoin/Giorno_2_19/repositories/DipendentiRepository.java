package mariapiabaldoin.Giorno_2_19.repositories;


import mariapiabaldoin.Giorno_2_19.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface DipendentiRepository extends JpaRepository<Dipendente, UUID> {
    Optional<Dipendente> findByEmail(String email);

    Optional<Dipendente> findByUsername(String username);
}