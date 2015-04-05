package com.gqshao.redis.utils;


import com.gqshao.redis.domin.SerializableBean;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;

public class JedisUtilsTest {

    @Test
    public void testSerialize() {
        SerializableBean bean = new SerializableBean("id", "name", "value", new Date(), true);
        byte[] bytes = JedisUtils.serialize(bean);
        SerializableBean unserialize = JedisUtils.unserialize(bytes);
        // ID 和 Name 所在类没有实现Serializable接口 所以不会被持久化
        assertThat(unserialize.getId()).isNullOrEmpty();
        assertThat(unserialize.getName()).isNullOrEmpty();
        assertThat(unserialize.getValue()).isEqualTo("value");
        assertThat(unserialize.getDate()).isBefore(new Date());
        assertThat(unserialize.getIsTrue()).isEqualTo(true);
    }
}
