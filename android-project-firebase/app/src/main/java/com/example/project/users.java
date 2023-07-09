package com.example.project;

public class users {
    private String name , password,email;
    users()
    {

    }

    users(String name,String password,String email) {
        this.name = name;
        this.email=email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString()
    {
       return name + "," + password ;
    }
}
