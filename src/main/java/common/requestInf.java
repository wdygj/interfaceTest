package common;

public class requestInf
{
    String reqHeader;
    String reqEntity;
    String responseHeader;
    String statusCode;
    String responseEntity;

    public requestInf(String reqHeader, String reqEntity, String responseHeader, String statusCode, String responseEntity)
    {
        this.reqHeader = reqHeader;
        this.reqEntity = reqEntity;
        this.responseHeader = responseHeader;
        this.statusCode = statusCode;
        this.responseEntity = responseEntity;
    }
    public String getReqHeader()
    {
        return reqHeader;
    }

    public void setReqHeader(String reqHeader)
    {
        this.reqHeader = reqHeader;
    }

    public String getReqEntity()
    {
        return reqEntity;
    }

    public void setReqEntity(String reqEntity)
    {
        this.reqEntity = reqEntity;
    }

    public String getResponseHeader()
    {
        return responseHeader;
    }

    public void setResponseHeader(String responseHeader)
    {
        this.responseHeader = responseHeader;
    }

    public String getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(String statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getResponseEntity()
    {
        return responseEntity;
    }

    public void setResponseEntity(String responseEntity)
    {
        this.responseEntity = responseEntity;
    }
}
