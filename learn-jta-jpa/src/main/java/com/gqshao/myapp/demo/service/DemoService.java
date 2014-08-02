package com.gqshao.myapp.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.gqshao.myapp.common.mapper.BeanMapper;
import com.gqshao.myapp.demo.dao.PU1Dao;
import com.gqshao.myapp.demo.entity.PU1;
import com.gqshao.myapp.demo.po.PU2;
import com.gqshao.myapp.demo.repository.PU1Repository;
import com.gqshao.myapp.demo.repository.PU2Repository;

@Transactional
@Service
public class DemoService {
    @Autowired
    private PU1Dao pu1Dao;

    @Autowired
    PU1Repository pu1Repository;

    @Autowired
    PU2Repository pu2Repository;

    public String save() {
        List<String> ids = Lists.newArrayList();
        PU1 pu1 = new PU1();
        pu1.setDescription("desc");
        // 注意, 同一个PO存储到两个数据库中, 在数据库中的ID不同
        // 所以应该在保存之前深拷贝一份pu1的副本, 分别保存到两个数据库中
        pu1Dao.save(pu1);
        pu1Repository.save(pu1);
        // PU2继承不同的基类, 保存的ID与PU1相同
        PU2 pu2 = new PU2();
        BeanMapper.copy(pu1, pu2);
        pu2Repository.save(pu2);
        return pu1.getId();
    }

    /**
     * 测试两个数据库同时回滚
     */
    public void exception() {
        PU1 pu1 = new PU1();
        pu1.setDescription("desc");
        PU1 pu2 = new PU1();
        BeanMapper.copy(pu1, pu2);
        pu1Dao.save(pu1);
        pu1Repository.save(pu2);
        throw new RuntimeException("测试回滚异常");
    }
}
