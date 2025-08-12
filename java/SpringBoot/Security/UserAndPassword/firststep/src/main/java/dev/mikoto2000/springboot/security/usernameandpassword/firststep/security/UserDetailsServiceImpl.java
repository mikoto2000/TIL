package dev.mikoto2000.springboot.security.usernameandpassword.firststep.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import dev.mikoto2000.springboot.security.usernameandpassword.firststep.entity.Account;
import dev.mikoto2000.springboot.security.usernameandpassword.firststep.repository.AccountRepository;
import jakarta.transaction.Transactional;

@Component
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
  // ユーザー情報テーブル用のリポジトリを注入
  @Autowired
  private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // DBからユーザー情報をSELECTする。
    // ユーザーが見つからない場合は例外をスローする
    Account account = accountRepository.findById(username)
        .orElseThrow(() -> {
          System.out.println("ユーザー:" + username + "が見つかりません。");
          throw new UsernameNotFoundException(username);
        });

    System.out.println(account);

    // 見つかったユーザー情報により認証情報を生成する
    return User.withUsername(
        account.getUsername())
        .password(account.getPassword())
        .roles(account.getRole())
        .disabled(!account.isEnabled())
        .build();
  }
}
