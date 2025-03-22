package vn.ute.mobile.project.repository;

import jakarta.validation.constraints.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ute.mobile.project.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

  Account findFirstByUsername(String username);

  Optional<Account> findByEmail(String email);

  Optional<Account> findByUsernameAndPassword(String username, String password);

  Optional<Account> findByEmailAndPassword(String email, String password);
}
