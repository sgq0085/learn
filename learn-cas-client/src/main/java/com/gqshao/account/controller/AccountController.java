package com.gqshao.account.controller;

import com.google.common.collect.Maps;
import com.gqshao.account.entity.ResultDTO;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Value("${cas.server.url}")
    public String baseUrl;

    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> query() {
        Map<String, Object> res = Maps.newHashMap();
        RestTemplate restTemplate = new RestTemplate();
        String uri = baseUrl + "/jaxrs/user/{loginName}/{custom}";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(com.google.common.net.HttpHeaders.AUTHORIZATION,
                encodeHttpBasic("admin", "123456"));
        HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);
        HttpEntity<ResultDTO> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, ResultDTO.class, "admin", "admin");
        ResultDTO dto = response.getBody();
        res.put("success", dto.getSuccess());
        res.put("msg", dto.getMsg());
        return res;

    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] input) {
        return Base64.encodeBase64String(input);
    }


    public static String encodeHttpBasic(String userName, String password) {
        String encode = userName + ":" + password;
        return "Basic " + encodeBase64(encode.getBytes());
    }

}
