package ru.telros.test_case_for_telros_ru.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.telros.test_case_for_telros_ru.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUserName(String username);

    Optional<User> findById(Long userId);

    Optional<User> findByUserName(String username);
}
