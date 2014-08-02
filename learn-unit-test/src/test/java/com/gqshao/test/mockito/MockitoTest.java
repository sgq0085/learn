package com.gqshao.test.mockito;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Iterator;

import org.junit.Test;

public class MockitoTest {
    @Test
    public void simpleTest() {

        Iterator i = mock(Iterator.class);
        when(i.next()).thenReturn("Hello").thenReturn("World");
        String result = i.next() + " " + i.next();
        verify(i, times(2)).next();
        assertThat(result).isEqualTo("Hello World");

        // static和final 修饰的方法
    }
}
