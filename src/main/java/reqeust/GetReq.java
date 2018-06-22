package reqeust;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetReq
{
    public static void configureHttpClient(HttpClientBuilder clientBuilder)
    {
        try
        {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
            {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException
                {
                    return true;
                }
            }).build();

            clientBuilder.setSslcontext(sslContext);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @Description:使用HttpClient发送get请求
     * @author:liuyc
     * @time:2016年5月17日 下午3:28:56
     */
    public static String httpClientGet(String url, Map<String, Object> params, String charset)
    {
        HttpClientBuilder builder = HttpClientBuilder.create();
        configureHttpClient(builder);
        CloseableHttpClient client = builder.build();
        StringBuffer resultBuffer = new StringBuffer();
        BufferedReader br = null;
        // 构建请求参数
        url = paramAppen(url, params, charset);
        HttpGet httpGet = new HttpGet(url);
        try
        {
            CloseableHttpResponse response = client.execute(httpGet);
            // 读取服务器响应数据
            HttpEntity entity = response.getEntity();
            resultBuffer.append(EntityUtils.toString(entity));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return resultBuffer.toString();
    }

    public static String paramAppen(String urlParam, Map<String, Object> params, String charset)
    {
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0)
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                try
                {
                    sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), charset));
                }
                catch (UnsupportedEncodingException e)
                {
                    throw new RuntimeException(e);
                }
                sbParams.append("&");
            }
        }
        if (sbParams != null && sbParams.length() > 0)
        {
            return urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
        }
        return urlParam;
    }
}
