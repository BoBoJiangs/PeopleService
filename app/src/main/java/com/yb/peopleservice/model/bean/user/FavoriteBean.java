package com.yb.peopleservice.model.bean.user;

import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;

import java.io.Serializable;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/3 16:04
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class FavoriteBean implements Serializable {

    /**
     * id : 0f2e37b7-bb91-464c-8e1a-d869c692d857
     * customerId : 9541869a-5c14-4b0f-b10d-4df0bc36c6f0
     * type : 2
     * targetId : 2aee461e-eccc-4f66-b80a-ddf1bf9b0f37
     * timestamp : 2019-12-21 17:56:20
     */

    private String id;
    private String customerId;
    private int type;
    private String targetId;
    private ShopInfo shop;
    private ServiceListBean data;

    public ShopInfo getShop() {
        return shop;
    }

    public void setShop(ShopInfo shop) {
        this.shop = shop;
    }

    public ServiceListBean getData() {
        return data;
    }

    public void setData(ServiceListBean data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
}
