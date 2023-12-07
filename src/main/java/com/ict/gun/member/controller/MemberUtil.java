package com.ict.gun.member.controller;

public class MemberUtil {
    public static String emailToFolderName(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
