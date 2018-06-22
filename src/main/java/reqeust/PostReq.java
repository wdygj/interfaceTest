package reqeust;

import common.requestInf;
import data.Data;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Guojun
 * */
public class PostReq
{
    /**
      *  application/x-www.form-urlencoded
     * */
    public  void httpClientPost2(String url,Map<String,Object> params)
    {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        //定义键值对列表，用于存放向url发送post请求的数据。
        List<BasicNameValuePair> paramsForList = getPostParam2(params);
        //定义HttpPost对象并初始化它
        HttpPost post = new HttpPost(url);
        //用UrlEncodedFormEntity对象包装请求体数据
        CloseableHttpResponse response = null;
        try
        {
            HttpEntity reqEntity = new UrlEncodedFormEntity(paramsForList);
            //设置post请求实体
            post.setEntity(reqEntity);
            //发送http请求
            response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            // 处理http返回码302的情况
            HttpEntity entity = response.getEntity();

            if ( statusCode== Data.redirect_Code)
            {
                //跳转到重定向的url
                String locationUrl = response.getLastHeader("Location").getValue();
                System.out.println(locationUrl);
            }
            else
            {
                System.out.println(EntityUtils.toString(reqEntity));
                //打印http请求返回码
                System.out.println(statusCode);
                //打印出响应内容长度
                System.out.println(entity.getContentLength());
                //打印出响应实体
                System.out.println("the response body is:" + EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
                response.close();
                client.close();
            }catch (Exception e)
            {
                System.out.println("关闭发生异常");
                e.printStackTrace();
            }
        }
    }
    /**
     * Content-type:application/json
     * */
    public  requestInf httpClientPost1(String url,Map<String,Object> header , Map<String,Object> params)
    {
        requestInf requestInf = null;
        HttpClient client = HttpClientBuilder.create().build();
        //定义一个JSON数据格式对象，用其保存请求主体数据。
        JSONObject js = getPostParam1(params);
        HttpPost post = new HttpPost(url);
        try
        {
            //用StringEntity对象包装请求体数据
            StringEntity reqEntity = new StringEntity(js.toString());
            //设置请求头数据传输格式
            reqEntity.setContentType("application/json");
            //设置请求头
            post.setHeaders(getHeaders(params));
            //设置post请求实体
            post.setEntity(reqEntity);
            //发送http请求
            HttpResponse response = client.execute(post);
            HttpEntity responseEntity = response.getEntity();
            requestInf.setReqHeader(header.toString());
            System.out.println("the request body is:" + EntityUtils.toString(reqEntity));            //打印出请求实体
            System.out.println(EntityUtils.toString(responseEntity)); //打印响应实体
            System.out.println(response.getStatusLine().getStatusCode());                          //打印http请求返回码
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *@Param:
     *@Return:
     *@Description:将map参数里的键值对取出用于初始化JSONObject并返回
     *@Author:44244
     *Date:2018/6/11
    */
    public  JSONObject getPostParam1(Map<String,Object> mapParam)
    {
       JSONObject postParams = new JSONObject();
        for (Map.Entry<String, Object> entry : mapParam.entrySet())
        {
            //向postParams设置数据
            postParams.accumulate(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return postParams;
    }
    /**
     *@Param:
     *@Return:
     *@Description:将map参数里的键值对取出用于初始化BasicNameValuePair并返回
     *@Author:44244
     *Date:2018/6/11
     */
    public  ArrayList<BasicNameValuePair> getPostParam2(Map<String,Object> mapParam)
    {
        ArrayList<BasicNameValuePair> postParams = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, Object> entry : mapParam.entrySet())
        {
            postParams.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }
        return postParams;
    }
    /**
     *@Param:
     *@Return:
     *@Description:将map参数里的键值对取出用于初始化Header数组并返回
     *@Author:44244
     *Date:2018/6/11
    */
    public Header[] getHeaders(Map<String,Object> mapParam)
    {
        ArrayList<Header> postParams = new ArrayList<Header>();
        for (Map.Entry<String, Object> entry : mapParam.entrySet())
        {
            postParams.add(new BasicHeader(entry.getKey(), String.valueOf(entry.getValue())));
        }
        return (Header[]) postParams.toArray();
    }

}
