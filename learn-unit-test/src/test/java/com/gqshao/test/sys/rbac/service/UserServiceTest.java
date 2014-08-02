package com.gqshao.test.sys.rbac.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gqshao.test.commons.exception.ServiceException;
import com.gqshao.test.commons.security.shiro.ShiroUser;
import com.gqshao.test.commons.utils.Ids;
import com.gqshao.test.security.shiro.ShiroTestUtils;
import com.gqshao.test.sys.rbac.dao.UserDao;
import com.gqshao.test.sys.rbac.domain.User;
import com.gqshao.test.sys.rbac.service.impl.UserServiceImpl;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao mockUserDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void init() {
        ShiroTestUtils.mockSubject(new ShiroUser.Builder(Ids.uuid(), "foo").name("foo").isAdmin(true)
                .ip("localhost").builder());
    }

    @AfterClass
    public static void destroy() {
        ShiroTestUtils.clearSubject();
    }

    @Test
    public void getById() {
        userService.getById(anyString());
        verify(mockUserDao).findUserById(anyString());
    }

    @Test
    public void getUserByLoginName() {
        User rtnUser = new User();
        rtnUser.setLoginName("admin");
        // Stud 设置方法调用的预期返回
        when(mockUserDao.findUserByLoginName(anyString())).thenReturn(rtnUser);
        User user = userService.getUserByLoginName("admin");
        // Mock 验证方法调用
        verify(mockUserDao, times(1)).findUserByLoginName(anyString());
        assertThat(user).isNotNull();
        assertThat(user.getLoginName()).isEqualTo("admin");
    }

    @Test
    public void save() {
        User user = new User();
        user.setLoginName("admin");
        // 第一次调用findUserByLoginName返回user 第二次调用返回null
        when(mockUserDao.findUserByLoginName(anyString())).thenReturn(user).thenReturn(null);
        try {
            // 测试如果重名会抛出异常
            userService.save(user);
            // 如果没有抛出异常测试不通过
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (ServiceException se) {
        }
        verify(mockUserDao).findUserByLoginName("admin");

        // userService.save(user);
        user.setPassword("123456");
        String userId = userService.save(user);
        // 断言返回结果
        assertThat(userId).isNotEmpty().hasSize(32);

        verify(mockUserDao, times(2)).findUserByLoginName(anyString());
        verify(mockUserDao).save(any(User.class));
    }

    @Test
    public void save2() {
        User user = new User();
        user.setLoginName("admin");
        user.setPassword("123456");
        userService.save(user);

        // 通过ArgumentCaptor(参数捕获器) 对传入参数进行验证
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(mockUserDao).save(argument.capture());
        assertThat("admin").isEqualTo(argument.getValue().getLoginName());

        // stub 调用save方法时抛出异常
        doThrow(new ServiceException("测试抛出异常")).when(mockUserDao).save(any(User.class));
        try {
            userService.save(user);
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (ServiceException se) {

        }
    }

    @Test
    public void resetPassword() {
        doReturn(new User()).when(mockUserDao).findUserById(anyString());
        userService.resetPassword("", "123456");
        verify(mockUserDao).findUserById(anyString());
    }

    @Test
    public void verifyTestTest() {
        List<String> mock = mock(List.class);
        List<String> mock2 = mock(List.class);
        when(mock.get(0)).thenReturn("Hello");
        when(mock.get(1)).thenReturn("World");
        mock.get(0);
        mock.get(1);
        // 验证指定方法被调用一次
        verify(mock).get(0);
        // 验证指定方法没有被调用
        verify(mock, never()).get(3);
        // 验证get方法在100毫秒内被调用两次
        verify(mock, timeout(100).times(2)).get(anyInt());

        // 通过验证方法的执行顺序
        InOrder inOrder = inOrder(mock, mock2);
        inOrder.verify(mock).get(0);
        inOrder.verify(mock).get(1);
        inOrder.verify(mock2, never()).get(1);

        // 查询多余的方法调用 mock所有调用的方法都已经被验证
        verifyNoMoreInteractions(mock);
        // 查询没有交互的mock对象
        verifyZeroInteractions(mock2);

        // 创建ArgumentCaptor（参数捕获器）用于捕获方法参数进行验证
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        // 该方法被调用多次 只能捕捉到最后一次参数
        verify(mock, times(2)).get(argument.capture());
        assertThat(argument.getValue()).isEqualTo(1);
    }

    @Test
    public void spyTest() {

//        List spy = spy(new LinkedList());
//        // IndexOutOfBoundsException (the list is yet empty)
//        when(spy.get(0)).thenReturn("foo");
//        // You have to use doReturn() for stubbing
//        doReturn("foo").when(spy).get(0);
    }
}
