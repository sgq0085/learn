/**
 * MongoDBBasic.java Create on 2015/2/4
 * Copyright(c) Gener-Tech Inc.
 * ALL Rights Reserved.
 */
package com.gqshao.mongo.service;

import com.mongodb.*;

import java.util.Arrays;

public class MongoDBBasic {

    public static void main(String[] args) {
        try {
            /*****1. 填写数据库相关信息(请查找数据库详情页)*****/
            String databaseName = "eiZrxDYAgZRYcJuTRljT";
            String host = "mongo.duapp.com";
            String port = "8908";
            String username = "urtgxzMPVigNEtOQF7yzg7C9";//用户名(api key);
            String password = "1e0jDqkZ7fUwNgFD5LzwPY4YAQURFGYM";//密码(secret key)
            String serverName = host + ":" + port;

            /******2. 接着连接并选择数据库名为databaseName的服务器******/
            MongoClient mongoClient = new MongoClient(new ServerAddress(serverName)
                    , Arrays.asList(MongoCredential.createMongoCRCredential(username, databaseName, password.toCharArray()))
                    , new MongoClientOptions.Builder().cursorFinalizerEnabled(false).build());
            DB mongoDB = mongoClient.getDB(databaseName);
            mongoDB.authenticate(username, password.toCharArray());
            /*至此连接已完全建立，就可对当前数据库进行相应的操作了*/
            /**
             * 3. 接下来就可以使用mongo数据库语句进行数据库操作,详细操作方法请参考java-mongodb官方文档
             */

            //集合并不需要预先创建
            DBCollection mongoCollection = mongoDB.getCollection("test_mongo");

            //插入数据
            DBObject data1 = new BasicDBObject();
            data1.put("no", 2007);
            data1.put("name", "this is a test message");
            mongoCollection.insert(data1);
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}