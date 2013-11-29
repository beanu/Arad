package com.beanu.arad.support;

import com.beanu.arad.error.AradException;
import com.beanu.arad.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Created by beanu on 13-11-29.
 */
public class DefaultHttpConfig implements IHttpConfig {

    @Override
    public String requestUrl() {
        return null;
    }

    @Override
    public JsonNode handleResult(String result) throws AradException {

        try {
            JsonNode node = JsonUtil.json2node(result);
            String statue = node.findValue("succeed").asText();
            if (statue != null && statue.equals("000")) {
                return node;
            } else {
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
