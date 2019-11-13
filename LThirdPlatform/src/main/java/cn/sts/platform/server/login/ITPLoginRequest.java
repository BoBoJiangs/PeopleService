package cn.sts.platform.server.login;


import java.util.Map;

import cn.sts.platform.server.login.bean.WXUser;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 第三方登录接口
 * Created by weilin on 2019/7/1.
 */
public interface ITPLoginRequest {

    /**
     * 微信API调用地址
     */
    String URL_WX_API = "https://api.weixin.qq.com/";

    /**
     * 微信登录通过code获取access_token
     */
    @GET("sns/oauth2/access_token")
    Observable<Map> getWXAccessToken(@QueryMap Map<String, String> parameter);


    /**
     * 微信登录通过access_token,openid获取用户信息
     */
    @GET("sns/userinfo")
    Observable<WXUser> getUseInfo(@QueryMap Map<String, String> parameter);

}
