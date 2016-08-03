package com.gqshao.fileupload;


import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.Charset;

public class FileUploadTest {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadTest.class);

    @Test
    public void testFileUpload() {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost/learn-web/bootstrap/fileinput/upload");
            RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).build();
            httpPost.setConfig(config);
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    // 以浏览器兼容模式运行，防止文件名乱码
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    // 设置请求的编码格式
                    .setCharset(CharsetUtils.get("UTF-8"))
                    // uploadFile对应服务端类的同名属性<File类型>
                    .addPart("uploadFile", new FileBody(new File(this.getClass().getResource("/../classes/freemarker/data.txt").toURI())))
                    .addPart("other", new StringBody("123456", ContentType.create("text/plain", Consts.UTF_8)))
                    .build();
            httpPost.setEntity(reqEntity);
            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);
            // 打印响应状态
            logger.info("status : {}", response.getStatusLine());
            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                // 打印响应长度
                logger.info("Response content length: {}", resEntity.getContentLength());
                // 打印响应内容
                logger.info(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
            }
            // 销毁
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            logger.info("异常", e);
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(httpClient);
        }
    }
}
