package com.example.Library.Management.System.models;

import com.example.Library.Management.System.enums.AccountStatus;

public abstract class Account {
    private String id;
    private String password;
    private AccountStatus status;
    private Person person;

    public boolean resetPassword() {
        return false;
    }

    public String getId() {
        return id;
    }
}
