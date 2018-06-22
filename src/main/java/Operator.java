import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import reqeust.PostReq;
import java.util.HashMap;

public class Operator
{
    public static void main(String[] args)
    {

        String url = "http://192.168.2.101:8085/flight/flightSchedule/save";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("username","ygj");
        params.put("password","888888");
        params.put("area","");

        PostReq.httpClientPost1(url,headers,params);
    }
}
