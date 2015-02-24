package com.gqshao.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    private int initialDelay = 5;

    private int period = 30;

    public TimerListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        Runnable task = new Runnable() {
            private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            @Override
            public void run() {
                System.out.println(format.format(new Date()));
            }
        };
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        System.out.printf("启动任务，每隔%s秒打印当前时间\n", period);
        scheduledExecutorService.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);

    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {
    }

    public void attributeAdded(HttpSessionBindingEvent sbe) {
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
    }
}
