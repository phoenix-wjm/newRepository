package com.sfac.geniusdirecruit.common.utile;
/*
 * @projectName: genius-direcruit
 * @documentName: SmsSend
 * @author: WJM
 * @date:2021/1/5 22:44
 */


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public class SmsSend {

    //key
    @Value("${aliyun.key}")
    private String key;
    //scret
    @Value("${aliyun.screct}")
    private String screte;
    //短信签名
    @Value("${aliyun.SignName}")
    private String signName;
    //短信模板
    @Value("${aliyun.TemplateCode}")
    private String templateCode;

    /**
     * 发送短信
     * @param phone 接收人电话
     * @param code  验证码
     * @return
     */
    public String send(String phone,String code){
        String info="";
        //cn-hangzhou 是阿里定义的签名固定值。填写阿里云申请短信签名的key和secret值
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", key, screte);
        /*阿里云提供的短信发送api的近期的相关代码，代码以最新的阿里api提供为准*/
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        /*填写阿里云申请的短信发送需要的相关信息*/
        request.putQueryParameter("PhoneNumbers",phone);
        request.putQueryParameter("SignName", "PHOENIX商城");
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{'code':"+code+"}");
        try {
            //获取发送短信的响应信息，响应的结果参数请参考阿里云提供的api信息
            CommonResponse response = client.getCommonResponse(request);
            System.err.println(response.getData());
            info = response.getData();
            //解析info字符串 ，转换成功HashMap对象
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String,Object> map = mapper.readValue(info, HashMap.class);
            System.err.println(map.get("Message"));
            //获取短信发送的Msssage值
            info = map.get("Message")+"";

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return info;
    }



}
