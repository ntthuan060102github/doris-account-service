package doris.dorisaccountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doris.dorisaccountservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Boolean existsByEmail(String email);
    public User findByEmail(String email);
}
