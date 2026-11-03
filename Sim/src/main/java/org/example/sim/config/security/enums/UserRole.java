package org.example.sim.config.security.enums;

import lombok.Getter;

@Getter
public enum UserRole {
  ADMIN("ROLE_ADMIN");

  private final String authority;

  UserRole(String authority) {
    this.authority = authority;
  }
}
