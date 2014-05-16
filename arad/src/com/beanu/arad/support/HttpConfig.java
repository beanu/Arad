package com.beanu.arad.support;

import com.beanu.arad.error.AradException;
import com.beanu.arad.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * 默认的HTTP 处理,统一处理接口的错误信息
 * Created by beanu on 13-11-29.
 */
public class HttpConfig {

    private String errorKey = "error_code";

    public HttpConfig() {

    }

    HttpConfig(String errorKey) {
        this.errorKey = errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    public JsonNode handleResult(String result) throws AradException {

        try {
            JsonNode node = JsonUtil.json2node(result);
            JsonNode error = node.findValue(errorKey);
            if (error == null) {
                return node;
            } else {
                String statue = error.asText();
                AradException e = new AradException();
                e.setError_code(statue);
                throw e;
            }
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
            AradException exception = new AradException(e1.getMessage());
            throw exception;
        } catch (IOException e1) {
            e1.printStackTrace();
            AradException exception = new AradException(e1.getMessage());
            throw exception;
        }
    }
}
