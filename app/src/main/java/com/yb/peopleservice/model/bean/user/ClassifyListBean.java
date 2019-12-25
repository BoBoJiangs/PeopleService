package com.yb.peopleservice.model.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.yb.peopleservice.model.bean.shop.ShopInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:PeopleService
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/11/25 10:35
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class ClassifyListBean implements Parcelable {
    private List<ClassifyListBean> categories;
    private List<ShopInfo> commodities;
    private List<ClassifyListBean> classList;
    /**
     * id : bcb7282d-25d4-4bb6-8c27-175928d250b8
     * name : 代驾服务
     * parentId : ed6c4b65-af71-4008-9c46-a0a6deed83f9
     * rank : 2
     * royalty : 0
     * img : /imgs/2019/12/22/dcd8cc26-cbed-4b5d-984b-0c1ac8673a9e.png
     * icon : /imgs/2019/12/22/f694c8c1-d83f-4284-88d7-29d9e8004f9b.png
     * serialNumber : 1
     * onMainPage : 1
     * calculatedDistance : 1
     */

    private String id;
    private String name;
    private String parentId;
    private int rank;
    private int royalty;
    private String img;
    private String icon;
    private int serialNumber;
    private int onMainPage;
    private int calculatedDistance;

    public List<ClassifyListBean> getClassList() {
        if (classList == null) {
            return new ArrayList<>();
        }
        return classList;
    }

    public void setClassList(List<ClassifyListBean> classList) {
        this.classList = classList;
    }

    public List<ShopInfo> getCommodities() {
        if (commodities == null) {
            return new ArrayList<>();
        }
        return commodities;
    }

    public void setCommodities(List<ShopInfo> commodities) {
        this.commodities = commodities;
    }

    /**
     * categoryId : 123
     * parentId : 0
     * categoryName : 服饰鞋包
     * pic : http://123.56.249.114/2019/09/1414ce410f484820943df75e8d6c8337.jpg
     * categoryIcon : null
     */





    public List<ClassifyListBean> getChildList() {
        if (categories == null) {
            return new ArrayList<>();
        }
        return categories;
    }

    public void setChildList(List<ClassifyListBean> childList) {
        this.categories = childList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRoyalty() {
        return royalty;
    }

    public void setRoyalty(int royalty) {
        this.royalty = royalty;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getOnMainPage() {
        return onMainPage;
    }

    public void setOnMainPage(int onMainPage) {
        this.onMainPage = onMainPage;
    }

    public int getCalculatedDistance() {
        return calculatedDistance;
    }

    public void setCalculatedDistance(int calculatedDistance) {
        this.calculatedDistance = calculatedDistance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.categories);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.parentId);
        dest.writeInt(this.rank);
        dest.writeInt(this.royalty);
        dest.writeString(this.img);
        dest.writeString(this.icon);
        dest.writeInt(this.serialNumber);
        dest.writeInt(this.onMainPage);
        dest.writeInt(this.calculatedDistance);
    }

    public ClassifyListBean() {
    }

    protected ClassifyListBean(Parcel in) {
        this.categories = in.createTypedArrayList(ClassifyListBean.CREATOR);
        this.id = in.readString();
        this.name = in.readString();
        this.parentId = in.readString();
        this.rank = in.readInt();
        this.royalty = in.readInt();
        this.img = in.readString();
        this.icon = in.readString();
        this.serialNumber = in.readInt();
        this.onMainPage = in.readInt();
        this.calculatedDistance = in.readInt();
    }

    public static final Creator<ClassifyListBean> CREATOR = new Creator<ClassifyListBean>() {
        @Override
        public ClassifyListBean createFromParcel(Parcel source) {
            return new ClassifyListBean(source);
        }

        @Override
        public ClassifyListBean[] newArray(int size) {
            return new ClassifyListBean[size];
        }
    };
}
