package com.axing.demo.stream;

import com.axing.demo.Person;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class StreamGroupingTests {


    public List<Person> personList() {
        List<Person> list = new ArrayList<>();

        list.add(Person.builder()
                .id(2)
                .sex("女")
                .age(10)
                .area("安徽")
                .build());
        list.add(Person.builder()
                .id(4)
                .sex("女")
                .age(12)
                .area("江苏")
                .build());

        list.add(Person.builder()
                .id(1)
                .sex("男")
                .age(10)
                .area("安徽")
                .build());
        list.add(Person.builder()
                .id(3)
                .sex("女")
                .age(11)
                .area("安徽")
                .build());

        return list;
    }

    @Test
    void test_groupingBy() {


        // 分组转list
        Collection<List<Person>> listCollection = personList().stream().collect(Collectors.groupingBy(Person::getSex)).values();
        System.out.println("分组转list = " + listCollection);

        // 分组后排序
        Map<String, List<Person>> sortedGroupedPeople = personList().stream()
                .collect(Collectors.groupingBy(Person::getSex))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        System.out.println("分组后排序 = " + listCollection);


        // 将员工按薪资是否高于2组
        Map<Boolean, List<Person>> part = personList().stream().collect(Collectors.partitioningBy(x -> x.getAge() > 2));
        // 将员工按性别分组
        Map<String, List<Person>> group = personList().stream().collect(Collectors.groupingBy(Person::getSex));

        // 将员工先按性别分组，再按地区分组
        //多重分组
        Map<String, Map<String, List<Person>>> group2 = personList().stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));
        System.out.println("员工按薪资是否大于8000分组情况：" + part);
        System.out.println("员工按性别分组情况：" + group);
        System.out.println("员工按性别、地区：" + group2);


        // 分组求和
        Map<String, Integer> collect = personList().stream()
                .collect(Collectors.groupingBy(
                                Person::getSex,
                                Collectors.summingInt(Person::getAge)
                        )
                );
        System.out.println("分组求和 = " + collect);

        // 分组个数
        Map<String, Long> collect1 = personList().stream()
                .collect(Collectors.groupingBy(
                                Person::getSex,
                                Collectors.counting()
                        )
                );
        System.out.println("分组个数 = " + collect1);

//        .sorted(Comparator.comparing(Person::getAge).reversed())
//                .map(Person::getName).collect(Collectors.toList())
        // map排序成list
        List<Map.Entry<String, Long>> collect2 = collect1.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()).collect(Collectors.toList());

        System.out.println("map排序 = " + collect2);


        List<Map.Entry<String, Long>> collect3 = collect1.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(1)
                .collect(Collectors.toList());

        System.out.println("map排序top = " + collect3);
    }

    @Test
    void test_groupingBy2() {

        // 分组转list
        Collection<List<Person>> listCollection = personList().stream().collect(Collectors.groupingBy(Person::getSex)).values();
        System.out.println("分组转list = " + listCollection);

        // 分组后排序
        Map<Integer, List<Person>> sortedGroupedPeople = personList().stream()
                .sorted(Comparator.comparing(Person::getId))
                .collect(Collectors.groupingBy(Person::getAge))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        System.out.println("分组后排序map = " + sortedGroupedPeople);

        Collection<List<Person>> listCollection1 = sortedGroupedPeople.values();

        System.out.println("分组后排序转list = " + listCollection1);

    }
}
