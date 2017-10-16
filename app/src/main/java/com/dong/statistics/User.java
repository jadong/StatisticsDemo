package com.dong.statistics;

/**
 * Created by kayo on 17/9/18.
 */
public class User {

    public String name;
    public int age;
    public A a = new A();
    public String tagname;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static class A{
        public String a_name="内部类A";
        public B b=new B();

    }

    public static class B{
        public String bbbbbName = "内部类B";
    }
}
