package vn.ute.mobile.project.service.impl;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.ute.mobile.project.constant.AppConstant;
import vn.ute.mobile.project.model.Account;
import vn.ute.mobile.project.repository.AccountRepository;

@Service
public class UserServiceImpl implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Account user = accountRepository.findAccountByUsername(username);
    if (user == null){
      throw new UsernameNotFoundException("Invalid username or password");
    }
    List<SimpleGrantedAuthority> authorities = Collections.singletonList(
        new SimpleGrantedAuthority("ROLE_USER")
    );

    boolean enable = user.getStatus().equals(AppConstant.ACCOUNT_STATUS_ACTIVE);
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), enable, true, true, true, authorities);
  }
}
