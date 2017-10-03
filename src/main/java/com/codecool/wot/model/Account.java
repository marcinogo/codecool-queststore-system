package com.codecool.wot.model;

public abstract class Account{

    protected String name;
    protected String surname;
    protected String email;
    protected String login;
    protected String password;
    protected String ID;

    public Account(String name, String surname, String email, String login, String password, String ID) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.ID = ID;
    }


    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSurname(){
        return this.surname;
    }

    public void setSurname(String surname){ this.surname = surname; }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getLogin(){
        return this.login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getPassword(){ return this.password; }

    public void setPassword(String password){
        this.password = password;
    }

    public String getId() {
        return this.ID;
    }

    public SchoolClass getSchoolClass(){
        return null;
    }

    public String toString() {
        return String.format("ID=%d, name=%s, surname=%s, email=%s, login=%s, password=%s",
                ID, name, surname, email, login, password);
    }

}
