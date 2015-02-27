package com.gqshao.mongo.service;

import com.google.common.collect.Lists;
import com.gqshao.mongo.domain.MorphiaBean;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.joda.time.DateTime;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MorphiaTest {


    public static void main(String[] args) {
        MongoClient mongoClient = null;
        try {
            try {
                // Mongo
                MongoCredential credential = MongoCredential.createMongoCRCredential("root", "admin", "123456".toCharArray());
                mongoClient = new MongoClient(new ServerAddress("192.168.3.106", 27017), Arrays.asList(credential));
                // 创建Morphia datastore
                Datastore datastore = new Morphia().createDatastore(mongoClient, "test");


                MorphiaBean bean1 = MorphiaBean.newInstance();
                String beanId = UUID.randomUUID().toString();
                bean1.setId(beanId);
                MorphiaBean bean2 = MorphiaBean.newInstance();
                String beanId2 = UUID.randomUUID().toString();
                bean2.setId(beanId2);
                MorphiaBean bean3 = MorphiaBean.newInstance();

                // 保存
                List<MorphiaBean> beanList = Lists.newArrayList(bean1, bean2, bean3);
                datastore.save(beanList);

                // 查询
                // 创建查询条件
                Query beanQuery = datastore.createQuery(MorphiaBean.class)
                        .field("id").equal(bean1)
                        .retrievedFields(true, "id", "name", "value", "date", "isTrue");

                // 查询列表 find()
                List<MorphiaBean> beans = datastore.createQuery(MorphiaBean.class)
                        .field("id").equal(bean1).asList();

                // 取一个条件 findOne()
                MorphiaBean oneBean = datastore.createQuery(MorphiaBean.class)
                        .field("id").equal(bean1).get();


                // 修改
                UpdateOperations<MorphiaBean> ops = datastore.createUpdateOperations(MorphiaBean.class).set("val", "value is change");
                Query<MorphiaBean> updateQuery = datastore.createQuery(MorphiaBean.class).filter("id in", Lists.newArrayList(beanId));
                datastore.update(updateQuery, ops);

                // 按条件删除
                datastore.delete(datastore.createQuery(MorphiaBean.class).filter("id", beanId2));
            } finally {
                mongoClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}