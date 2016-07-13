package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;

/**
 * Created by Timur on 13.07.2016.
 */
public class RoleUtil {
    private int id;
    private Role role;

    public RoleUtil(int id, Role role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }
}
