package com.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTest {


    static class ZZ {

        private final int value =  1;

        public int getValue() {

            return value;

        }

    }


    public static void main(String args[]) {

        try {

            Class c = null;

            c = ZZ.class;

            Object obj = c.newInstance();

            Field f = c.getDeclaredField("value");

            f.setAccessible(true);

            f.setInt(obj, 5);

            Method m = c.getDeclaredMethod("getValue", null);

            System.out.println("lyn Field.get=====》" + f.get(obj));

            System.out.println("lyn Method getValue====》" + m.invoke(obj, null));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("lyn Exception" + e.getLocalizedMessage());

        }

    }


}
