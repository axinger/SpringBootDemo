package com.ax.master;

public class Test {
    public static void main(String[] args) {
//        System.out.println(Color.RED.toString());


    }


    public enum Color {
        RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLO("黄色", 4);
        // 成员变量
        private final String name;
        private final int index;

        // 构造方法
        Color(String name, int index) {
            this.name = name;
            this.index = index;
        }

        // 覆盖方法
        @Override
        public String toString() {
            return this.index + "_" + this.name;
        }
    }
}
