package com.codecool.wot.view;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

import com.codecool.wot.model.*;


public class SchoolView {

//    School school;
//    public SchoolView(School newSchool) {
//        school = newSchool;
//    }
//
//    public Account logIn() {
//
//        Scanner in = new Scanner(System.in);
//        String login;
//        String password;
//        Boolean toLog = true;
//
//        while (toLog){
//
//            login = getStringInput("Provide login: ");
//
//            if (isSuchlogin(login)) {
//
//                password = getStringInput("Provide password: ");
//
//                if (doesPasswordMatch(login, password)) {
//
//                    Account user = returnUser(login);
//                    return user;
//                }
//                else {
//                    toLog = toKeepLoging("Password doesn't match the login \npress Y if you want to continue logging");
//                }
//            }
//            else {
//                toLog = toKeepLoging("Such login doesn't exist \npress Y if you want to continue logging");
//            }
//        }
//        System.out.println("Login failed");
//        return createDefaultUser();
//
//    }
//
//    private String getStringInput(String question) {
//
//        Scanner in = new Scanner(System.in);
//        System.out.println(question);
//        String answer = in.next();
//        return answer;
//    }
//    private Boolean toKeepLoging(String warning) {
//
//        Scanner in = new Scanner(System.in);
//        System.out.println(warning);
//        String answer = in.next().toUpperCase();
//
//        if (!answer.equals("Y")) {
//            return false;
//        }
//        return true;
//    }
//    private Boolean isSuchlogin(String login) {
//
//        Iterator iter = school.getAccounts().iterator();
//
//        while(iter.hasNext()) {
//            Account account = (Account) iter.next();
//            if (account.getLogin().equals(login)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private Boolean doesPasswordMatch(String login, String password) {
//
//        Iterator iter = school.getAccounts().iterator();
//
//        while(iter.hasNext()) {
//            Account account = (Account) iter.next();
//            if (account.getLogin().equals(login)) {
//                if (account.getPassword().equals(password)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    private Account createDefaultUser() {
//        Student def = new Student();
//        return def;
//    }
//
//    private Account returnUser(String login) {
//
//        Iterator iter = school.getAccounts().iterator();
//
//        while(iter.hasNext()) {
//            Account account = (Account) iter.next();
//            if (account.getLogin().equals(login)) {
//                    return account;
//                }
//            }
//            return createDefaultUser();
//        }

}
