package ru.telros.test_case_for_telros_ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.telros.test_case_for_telros_ru.model.UserCredential;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

}
