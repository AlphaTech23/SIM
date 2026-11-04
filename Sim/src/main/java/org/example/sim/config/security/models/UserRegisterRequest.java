package org.example.sim.config.security.models;

import org.example.sim.models.RoleEntity;
import org.example.sim.models.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class UserRegisterRequest {
    private Long id;
    private String username;
    private String email;
    private String password;
    private RoleEntity roleidRoleEntity;
    public UserEntity get() {
        UserEntity user = new UserEntity();
        user.setId(this.id);
        user.setName(this.username);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setRoleidRoleEntity(this.roleidRoleEntity);
        return user;
    }
}
