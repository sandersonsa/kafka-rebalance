package xyz.sandersonsa.kafka_sp.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import xyz.sandersonsa.kafka_sp.utils.XmlUtils;

@Service
public class SoapService {
    
    @Value("classpath:template/ipva-request.xml")
    Resource resource;

    @Value("${soap.uri}")
    String uri;

    @Value("${soap.action}")
    String action;

    @Value("${soap.user}")
    String user;

    @Value("${soap.password}")
    String password;

    public String callSoap(final String parteFixa, final String parteVariavel) {
        String msg = String.format(getTemplate(), parteFixa, parteVariavel);

        return execMethod(msg);
    }

    private String execMethod(String payload) {
        HttpPost httpPost = setHttpHeader(payload);
        String response = null;
        HttpClientBuilder httpClient = HttpClientBuilder.create();
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.build().execute(httpPost);
            HttpEntity respEntity = httpResponse.getEntity();
            if (respEntity != null) {
                response = EntityUtils.toString(respEntity);
                response = XmlUtils.getValue(response);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getTemplate() {
        String content = null;
        try {
            content = resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private HttpPost setHttpHeader(final String msg) {
        HttpPost post = new HttpPost(uri);
        post.setHeader("SOAPAction", action);
        post.setHeader("Content-Type", "application/soap+xml;charset=UTF-8");
        post.setHeader("exx-natural-library", "libbrk");
        post.setHeader("exx-rpc-userID", user);
        post.setHeader("exx-rpc-password", password);
        post.setHeader("exx-natural-security", "true");
        post.setEntity(new StringEntity(msg, "UTF-8"));
        return post;
    }

}
