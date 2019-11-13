package cn.sts.base.util;

/**
 * Glide常用工具类
 * Created by daichao on 17/9/2.
 */

public class GlideUtil {

    private static final String TAG = "GlideUtil";

//    /**
//     * 加载本地圆形图片
//     *
//     * @param context      上下文
//     * @param url          本地图片地址
//     * @param imageView    view
//     * @param defaultResId 默认图片id
//     */
//    public static void loadLocalCircleImage(final Context context, String url, final ImageView imageView, Integer defaultResId) {
//
//        Glide.with(context)
//                .load(url)
//                .asBitmap()
//                .centerCrop()
//                .placeholder(defaultResId)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//是将图片原尺寸缓存到本地。
//                .into(new BitmapImageViewTarget(imageView) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);
//                        imageView.setImageDrawable(circularBitmapDrawable);
//                    }
//                });
//
//
//    }
//
//    /**
//     * 加载服务器圆形图片
//     *
//     * @param context      上下文
//     * @param url          图片地址
//     * @param imageView    view
//     * @param defaultResId 默认图片id
//     */
//    public static void loadServerCircleImage(final Context context, String url, final ImageView imageView, Integer defaultResId) {
//
//        Glide.with(context)
//                .load(url)
//                .asBitmap()
//                .centerCrop()
//                .placeholder(defaultResId)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//是将图片原尺寸缓存到本地。
//                .into(new BitmapImageViewTarget(imageView) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);
//                        imageView.setImageDrawable(circularBitmapDrawable);
//                    }
//
//                });
//
//
//    }
//
//
//    /**
//     * 加载服务器图片
//     *
//     * @param context   上下文
//     * @param url       图片地址
//     * @param imageView view
//     */
//    public static void loadServerImage(Context context, String url, ImageView imageView) {
//        loadServerImage(context, url, imageView, R.drawable.base_icon_default);
//    }
//
//    /**
//     * 加载服务器图片
//     *
//     * @param context      上下文
//     * @param url          图片地址
//     * @param imageView    view
//     * @param defaultResId 默认图片id
//     */
//    public static void loadServerImage(Context context, String url, ImageView imageView, Integer defaultResId) {
//        Glide.with(context)
//                .load(url)
//                .placeholder(defaultResId)
//                .into(imageView)
//        ;
//    }
//
//    /**
//     * 加载服务器图片但是没有默认图片
//     *
//     * @param context   上下文
//     * @param url       图片地址
//     * @param imageView view
//     */
//    public static void loadServerImageForNoDefault(Context context, String url, ImageView imageView) {
//        Glide.with(context)
//                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//是将图片原尺寸缓存到本地。
//                .into(imageView)
//        ;
//    }
//
//    /**
//     * 加载本地图片
//     *
//     * @param context      上下文
//     * @param url          本地图片地址
//     * @param imageView    view
//     * @param defaultResId 默认图片id
//     */
//    public static void loadLocalImage(Context context, String url, ImageView imageView, Integer defaultResId) {
//        Glide.with(context)
//                .load(url)
//                .placeholder(defaultResId)
//                .into(imageView);
//    }

}
