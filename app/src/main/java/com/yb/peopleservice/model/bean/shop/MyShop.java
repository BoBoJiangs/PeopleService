package com.yb.peopleservice.model.bean.shop;

import java.io.Serializable;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/12/20 15:53
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class MyShop implements Serializable {
    private String status;//状态: 1正常服务，店铺可以派单给他 3服务人员申请入驻店铺 4申请入驻拒绝 5申请离职
    private String message;
    private ShopInfo shop;

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShopInfo getShop() {
        return shop;
    }

    public void setShop(ShopInfo shop) {
        this.shop = shop;
    }
}
