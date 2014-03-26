package oahtu2;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by xiaofei.wxf on 2014/3/26.
 */
public class TecentOauth2 {
    String url;

    final HttpClient client = new HttpClient();

    @Before
    public void setup(){
        url = "https://graph.qq.com/oauth2.0/authorize";
    }

    //https://graph.qq.com/oauth2.0/authorize?response_type=token&client_id=1101264463&redirect_uri=10.68.173.63

    @Test
    public void oauth() throws IOException {

        GetMethod method = new GetMethod(url);
        HttpMethodParams params = new HttpMethodParams();
        params.setParameter("response_type", "token");
        params.setParameter("client_id", "1101264463");
        params.setParameter("redirect_uri", "10.68.173.63");
        method.setParams(params);
        client.executeMethod(method);

        String responseBody = new String(method.getResponseBody());
        System.out.println(responseBody);
    }


}
