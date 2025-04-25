package vn.ute.mobile.project.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ute.mobile.project.model.Account;

public interface AccountRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {
  Optional<Account> findByEmail(String email);

  Account findAccountByUsername(String username);
}
