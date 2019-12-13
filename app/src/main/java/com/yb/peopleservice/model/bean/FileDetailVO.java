package com.yb.peopleservice.model.bean;

/**
 * 类描述:
 * 创建人:yangbo_ QQ:819463350
 * 创建时间: 2019/7/11  17:16
 * 修改人:
 * 修改时间:
 * 修改描述:
 */
public class FileDetailVO {


    /**
     * fileExtension : jpg
     * fileSize : 241240
     * imgHeight : 1304
     * imgWidth : 980
     * name : IMG_20190712_103749.jpg
     * smailUrl : https://stsflower.oss-cn-shenzhen.aliyuncs.comupload/flower/2019/07/12/sm7215c5e9-b6da-4155-801b-d23663f4d957.jpg
     * url : https://stsflower.oss-cn-shenzhen.aliyuncs.comupload/flower/2019/07/12/7215c5e9-b6da-4155-801b-d23663f4d957.jpg
     */

    private String fileExtension;
    private int fileSize;
    private int imgHeight;
    private int imgWidth;
    private String name;
    private String smailUrl;
    private String url;

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmailUrl() {
        return smailUrl;
    }

    public void setSmailUrl(String smailUrl) {
        this.smailUrl = smailUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
