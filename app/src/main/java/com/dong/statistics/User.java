package com.dong.statistics;

/**
 * Created by kayo on 17/9/18.
 */
public class User {

    public String name;
    public int age;
    public A a = new A();

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static class A{
        public String a="校花";

    }
}
