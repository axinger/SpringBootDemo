package com.ax.demo.impl;

import com.ax.demo.AnimalAction;
import com.ax.demo.Dog;

/**
 * @author xing
 * @version 1.0.0
 * @ClassName DogActionImpl.java
 * @description TODO
 * @createTime 2022年06月23日 13:57:00
 */

public class DogActionImpl implements AnimalAction<Dog> {
    @Override
    public Dog show(Dog dog) {
        return null;
    }

    @Override
    public Dog show1(Class<Dog> dogClass) {
        return null;
    }

    @Override
    public Dog show2() {
        return new Dog();
    }
}
