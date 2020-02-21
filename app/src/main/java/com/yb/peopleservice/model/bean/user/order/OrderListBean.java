package com.yb.peopleservice.model.bean.user.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.yb.peopleservice.model.bean.shop.ShopInfo;
import com.yb.peopleservice.model.bean.user.AddressListVO;
import com.yb.peopleservice.model.bean.user.service.ServiceListBean;

import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述: 订单列表
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2020/1/8 9:18
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class OrderListBean implements Parcelable {
    private OrderBean order;
    private ServiceListBean commodity;
    private ShopInfo shop;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public ServiceListBean getCommodity() {
        return commodity;
    }

    public void setCommodity(ServiceListBean commodity) {
        this.commodity = commodity;
    }

    public ShopInfo getShop() {
        return shop;
    }

    public void setShop(ShopInfo shop) {
        this.shop = shop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.order, flags);
        dest.writeParcelable(this.commodity, flags);
        dest.writeParcelable(this.shop, flags);
    }

    public OrderListBean() {
    }

    protected OrderListBean(Parcel in) {
        this.order = in.readParcelable(OrderBean.class.getClassLoader());
        this.commodity = in.readParcelable(ServiceListBean.class.getClassLoader());
        this.shop = in.readParcelable(ShopInfo.class.getClassLoader());
    }

    public static final Creator<OrderListBean> CREATOR = new Creator<OrderListBean>() {
        @Override
        public OrderListBean createFromParcel(Parcel source) {
            return new OrderListBean(source);
        }

        @Override
        public OrderListBean[] newArray(int size) {
            return new OrderListBean[size];
        }
    };
}
