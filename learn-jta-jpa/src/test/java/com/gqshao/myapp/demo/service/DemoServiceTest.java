package com.gqshao.myapp.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@ActiveProfiles("development")
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
//@Transactional
public class DemoServiceTest {
    @Autowired
    private DemoService demoService;

    @Test
    public void save() {
        demoService.save();
    }

    @Test
    public void test(){
        System.out.println(1);
    }
}
