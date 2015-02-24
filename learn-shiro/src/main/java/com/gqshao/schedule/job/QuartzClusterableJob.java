package com.gqshao.schedule.job;


import com.gqshao.authentication.dao.CachingShiroSessionDao;
import org.apache.shiro.session.Session;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Collection;
import java.util.Map;

/**
 * 被Spring的Quartz JobDetailBean定时执行的Job类, 支持持久化到数据库实现Quartz集群.
 * <p/>
 * 因为需要被持久化, 不能有用XXService等不能被持久化的成员变量,
 * 只能在每次调度时从QuartzJobBean注入的applicationContext中动态取出.
 * <p/>
 * timerJobConfig中配置的参数也可以通过applicationContext中动态取出
 */
public class QuartzClusterableJob extends QuartzJobBean {
    private static Logger logger = LoggerFactory
            .getLogger(QuartzClusterableJob.class.getName() + ".quartz cluster job");

    private ApplicationContext applicationContext;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        CachingShiroSessionDao sessionDao = applicationContext.getBean(CachingShiroSessionDao.class);
        Collection<Session> sessions = sessionDao.getActiveSessions();
        int size = 0;
        if (sessions != null) {
            size = sessions.size();
        }
        Map config = (Map) applicationContext.getBean("timerJobConfig");

        // 该参数配置在application.properties中
        String nodeName = (String) config.get("nodeName");

        logger.info("There are {} user in database, on node {}.", size, nodeName);
    }

    /**
     * 从SchedulerFactoryBean注入的applicationContext.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
