package common;

import integration.entity.Role;

public class RoleDTO {
    private String role;

    public RoleDTO(String role) {
        this.role = role;
    }

    public RoleDTO(Role role) {
        this.role = role.getName();
    }

    public String getRole() {
        return role;
    }
}
