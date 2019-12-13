package com.yb.peopleservice.model.bean;

import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述:登录
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/10 15:58
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class LoginBean {

    public static final int USER_TYPE = 3;//用户
    public static final int SERVICE_TYPE = 2;//服务人员
    public static final int SHOP_TYPE = 1;//商家
    /**
     * access_token : ea346196c58868472429d564e91635ff4e48efc21e776a19039b48f8b6cbbd59
     * refresh_token : a482d18ff153ed587ee891d02de8e0297040e045823dbc9b4574e51fba4892a8
     * token_type : Bearer
     * expires_in : 7200
     * scope : [3]
     * timestamp : 1575964853310
     */

    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
    private long timestamp;
    private List<Integer> scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Integer> getScope() {
        return scope;
    }

    public void setScope(List<Integer> scope) {
        this.scope = scope;
    }
}
