package com.exam.helper;

public class UserFoundExpection extends Exception{

    public UserFoundExpection()
    {
        super("user with this username already present in DB found!!try with another one");
    }

    public UserFoundExpection(String msg){
        super(msg);
    }
}
