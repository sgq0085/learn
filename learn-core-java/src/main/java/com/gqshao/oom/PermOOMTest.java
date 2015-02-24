package com.gqshao.oom;

public class PermOOMTest {

    public static void main(String[] args) {

        String[] arrays = new String[10000000];

        for (int i = 0; i < arrays.length; i++) {
            String str = String.valueOf(i).intern();
            arrays[i] = str;
            System.out.println(str);
        }

    }
}
