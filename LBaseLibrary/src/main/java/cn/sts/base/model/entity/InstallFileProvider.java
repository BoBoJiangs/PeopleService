package cn.sts.base.model.entity;

import androidx.core.content.FileProvider;

/**
 * 在android7.0，修改了对私有存储的限制，导致在获取资源的时候，
 * 不能通过Uri.fromFile（..）来获取uri了，但是在写入数据的时候
 * 是可以通过Uri.fromFile（..）来获取uri的，android 官网给出的
 * 解决办法是通过FileProvider来解决这一问题，我们需要在AndroidManifest.xml 配制provider节点
 * 我们项目中可能会用到其他一些第三方sdk有用到拍照或者其他功能的话，他也为了适配android7.0也添加
 * 了这个节点，所以要重写一个类 继承自FileProvider
 * Created by weilin on 2019-07-03.
 */
public class InstallFileProvider extends FileProvider {
}
