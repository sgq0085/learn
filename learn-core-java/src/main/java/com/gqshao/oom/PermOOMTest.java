/**
 * PermOOMTest.java Create on 2014/12/2
 * Copyright(c) Gener-Tech Inc.
 * ALL Rights Reserved.
 */
package com.gqshao.oom;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author <a href="mailto:shao.gq@gener-tech.com">shaogq</a>
 * @version 1.0
 */
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
