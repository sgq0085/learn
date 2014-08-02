package com.gqshao.test.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.util.Dates.parse;
import static org.assertj.core.util.Dates.parseDatetimeWithMs;
import static org.assertj.core.util.Lists.newArrayList;

import java.lang.annotation.*;
import java.util.*;

import org.assertj.core.util.*;
import org.assertj.core.util.Lists;
import org.joda.time.*;
import org.junit.*;

import com.google.common.collect.Maps;

// @RunWith(SpringJUnit4ClassRunner.class)
public class DemoTest {

    @Test
    public void testString() {
        String str = null;
        // 断言null或为空字符串
        assertThat(str).isNullOrEmpty();
        // 断言空字符串
        assertThat("").isEmpty();
        // 断言字符串相等 断言忽略大小写判断字符串相等
        assertThat("Frodo").isEqualTo("Frodo").isEqualToIgnoringCase("frodo");
        // 断言开始字符串 结束字符穿 字符串长度
        assertThat("Frodo").startsWith("Fro").endsWith("do").hasSize(5);
        // 断言包含字符串 不包含字符串
        assertThat("Frodo").contains("rod").doesNotContain("fro");
        // 断言字符串只出现过一次
        assertThat("Frodo").containsOnlyOnce("do");
        // 判断正则匹配
        assertThat("Frodo").matches("..o.o").doesNotMatch(".*d");
    }

    @Test
    public void testNumber() {
        Integer num = null;
        // 断言空
        assertThat(num).isNull();
        // 断言相等
        assertThat(42).isEqualTo(42);
        // 断言大于 大于等于
        assertThat(42).isGreaterThan(38).isGreaterThanOrEqualTo(38);
        // 断言小于 小于等于
        assertThat(42).isLessThan(58).isLessThanOrEqualTo(58);
        // 断言0
        assertThat(0).isZero();
        // 断言正数 非负数
        assertThat(1).isPositive().isNotNegative();
        // 断言负数 非正数
        assertThat(-1).isNegative().isNotPositive();
    }

    @Test
    public void testDate() {
        // 断言与指定日期相同 不相同 在指定日期之后 在指定日期之钱
        assertThat(Dates.parse("2014-02-01")).isEqualTo("2014-02-01").isNotEqualTo("2014-01-01")
                .isAfter("2014-01-01").isBefore(parse("2014-03-01"));
        // 断言 2014 在指定年份之前 在指定年份之后
        assertThat(DateTime.now().toDate()).isBeforeYear(2020).isAfterYear(2013);
        // 断言时间再指定范围内 不在指定范围内
        assertThat(parse("2014-02-01")).isBetween("2014-01-01", "2014-03-01").isNotBetween(
                parse("2014-02-02"), parse("2014-02-28"));

        // 断言两时间相差100毫秒
        Date d1 = new Date();
        Date d2 = new Date(d1.getTime() + 100);
        assertThat(d1).isCloseTo(d2, 100);

        // sets dates differing more and more from date1
        Date date1 = Dates.parseDatetimeWithMs("2003-01-01T01:00:00.000");
        Date date2 = parseDatetimeWithMs("2003-01-01T01:00:00.555");
        Date date3 = parseDatetimeWithMs("2003-01-01T01:00:55.555");
        Date date4 = parseDatetimeWithMs("2003-01-01T01:55:55.555");
        Date date5 = parseDatetimeWithMs("2003-01-01T05:55:55.555");

        // 断言 日期忽略毫秒，与给定的日期相等
        assertThat(date1).isEqualToIgnoringMillis(date2);
        // 断言 日期与给定的日期具有相同的年月日时分秒
        assertThat(date1).isInSameSecondAs(date2);
        // 断言 日期忽略秒，与给定的日期时间相等
        assertThat(date1).isEqualToIgnoringSeconds(date3);
        // 断言 日期与给定的日期具有相同的年月日时分
        assertThat(date1).isInSameMinuteAs(date3);
        // 断言 日期忽略分，与给定的日期时间相等
        assertThat(date1).isEqualToIgnoringMinutes(date4);
        // 断言 日期与给定的日期具有相同的年月日时
        assertThat(date1).isInSameHourAs(date4);
        // 断言 日期忽略小时，与给定的日期时间相等
        assertThat(date1).isEqualToIgnoringHours(date5);
        // 断言 日期与给定的日期具有相同的年月日
        assertThat(date1).isInSameDayAs(date5);
    }

    @Test
    public void testList() {
        // 断言 列表是空的
        assertThat(newArrayList()).isEmpty();
        // 断言 列表的开始 结束元素
        assertThat(newArrayList(1, 2, 3)).startsWith(1).endsWith(3);
        // 断言 列表包含元素 并且是排序的
        assertThat(newArrayList(1, 2, 3)).contains(1, atIndex(0)).contains(2, atIndex(1)).contains(3)
                .isSorted();
        // 断言 被包含与给定列表
        assertThat(newArrayList(3, 1, 2)).isSubsetOf(newArrayList(1, 2, 3, 4));
        // 断言 存在唯一元素
        assertThat(Lists.newArrayList("a", "b", "c")).containsOnlyOnce("a");
    }

    @Test
    public void testMap() {
        Map<String, Object> foo = Maps.newHashMap();
        foo.put("A", 1);
        foo.put("B", 2);
        foo.put("C", 3);

        // 断言 map 不为空 size
        assertThat(foo).isNotEmpty().hasSize(3);
        // 断言 map 包含元素
        assertThat(foo).contains(entry("A", 1), entry("B", 2));
        // 断言 map 包含key
        assertThat(foo).containsKeys("A", "B", "C");
        // 断言 map 包含value
        assertThat(foo).containsValue(3);
    }

    @Test
    public void testClass() {
        // 断言 是注解
        assertThat(Magical.class).isAnnotation();
        // 断言 不是注解
        assertThat(Ring.class).isNotAnnotation();
        // 断言 存在注解
        assertThat(Ring.class).hasAnnotation(Magical.class);
        // 断言 不是借口
        assertThat(Ring.class).isNotInterface();
        // 断言 是否为指定Class实例
        assertThat("string").isInstanceOf(String.class);
        // 断言 类是给定类的父类
        assertThat(Person.class).isAssignableFrom(Employee.class);
    }

    @Magical
    public enum Ring {
        oneRing, vilya, nenya, narya, dwarfRing, manRing;
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Magical {
    }

    public class Person {
    }

    public class Employee extends Person {
    }

    @Test
    public void testFail() {
        try {
            fail("在不检查任何条件的情况下使断言失败。显示一则消息");
        } catch (AssertionError ae) {
            // logger.info("可以通过catch捕获该Error");
        }
    }
}
