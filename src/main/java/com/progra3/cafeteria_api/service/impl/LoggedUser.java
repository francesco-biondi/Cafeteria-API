package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.model.entity.Employee;

public class LoggedUser {

    private static Employee currentUser;

    public static void set(Employee employee){
        currentUser = employee;
    }

    public static Employee get(){
        return currentUser;
    }

    public static void clear(){
        currentUser = null;
    }

}
