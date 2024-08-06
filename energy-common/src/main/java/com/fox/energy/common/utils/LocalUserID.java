package com.fox.energy.common.utils;

public class LocalUserID {
    private static final ThreadLocal<String> LOCAL = new ThreadLocal<>();

    public static void put(String uid) {
        LOCAL.set(uid);
    }

    public static void put(Integer uid) {
        LOCAL.set(uid.toString());
    }

    public static Integer get() {
        return Integer.parseInt(LOCAL.get());
    }

    public static Long getL() {
        return Long.parseLong(LOCAL.get());
    }

    public static String getStr() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }

}
