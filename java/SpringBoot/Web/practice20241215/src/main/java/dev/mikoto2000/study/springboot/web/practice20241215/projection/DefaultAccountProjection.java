package dev.mikoto2000.study.springboot.web.practice20241215.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.Account;
import dev.mikoto2000.study.springboot.web.practice20241215.entity.Role;

/**
 * DefaultAccountProjection
 */
@Projection(name = "defaultAccountProjection", types = { Account.class })
public interface DefaultAccountProjection {
  @Value("#{target.id}")
  Long getId();
  String getName();
  Role getRole();
}
