package cn.sts.base.util;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.provider.Settings;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 位置工具
 * Created by weilin on 2018/6/13.
 */
public class LocationUtil {

    /**
     * 检查GPS是否打开
     *
     * @param context context
     * @return true打开，false关闭
     */
    public static boolean checkGPSIsOpen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return false;
        }
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 检查WLAN或移动网络(3G/2G)定位（也称作AGPS，辅助GPS定位)是否打开
     *
     * @param context context
     * @return true打开，false关闭
     */
    public static boolean checkAGPSIsOpen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return false;
        }
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * 检查是否打开了模拟位置
     *
     * @param context context
     * @return true打开，false关闭
     */
    public static boolean checkMockLocationIsOpen(Context context) {
        boolean isOpen = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                // Android 6.0 及以上：没有【允许模拟位置】选项，同时弃用了Settings.Secure.ALLOW_MOCK_LOCATION，
                // 无法通过上面的方法判断。增加了【选择模拟位置信息应用】的方法，需要选择使用模拟位置的应用。
                // 但是不知道怎么获取当前选择的应用，因此通过是否能够成功执行addTestProvider方法来进行判断，
                // 如果没有选择当前的应用，则addTestProvider会抛出异常。
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                String providerStr = LocationManager.GPS_PROVIDER;
                if (locationManager != null) {
                    LocationProvider provider = locationManager.getProvider(providerStr);
                    if (provider != null) {
                        locationManager.addTestProvider(
                                provider.getName(),
                                provider.requiresNetwork(),
                                provider.requiresSatellite(),
                                provider.requiresCell(),
                                provider.hasMonetaryCost(),
                                provider.supportsAltitude(),
                                provider.supportsSpeed(),
                                provider.supportsBearing(),
                                provider.getPowerRequirement(),
                                provider.getAccuracy());
                    } else {
                        locationManager.addTestProvider(
                                providerStr,
                                true,
                                true,
                                false,
                                false,
                                true,
                                true,
                                true,
                                Criteria.POWER_HIGH,
                                Criteria.ACCURACY_FINE);
                    }
                    locationManager.setTestProviderEnabled(providerStr, true);
                    locationManager.setTestProviderStatus(providerStr, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
                    // 模拟位置可用
                    isOpen = true;
                }
            } catch (Exception e) {
                isOpen = false;
            }
        } else {
            // 打开=1，关闭=0
            isOpen = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, 0) == 1;
        }
        return isOpen;
    }

    /**
     * 获取LAC和CID
     *
     * @return LAC和CIDs
     */
    public static int[] getLACAndCID(Application application) {

        int[] lacCID = new int[2];

        TelephonyManager mTelephonyManager = (TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);

        if (mTelephonyManager != null && ContextCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            CellLocation cellLocation = mTelephonyManager.getCellLocation();
            if (cellLocation != null) {
                if (cellLocation instanceof GsmCellLocation) {
                    // 中国移动和中国联通获取LAC、CID的方式
                    GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                    lacCID[0] = gsmCellLocation.getLac();
                    lacCID[1] = gsmCellLocation.getCid();

                } else if (cellLocation instanceof CdmaCellLocation) {
                    // 中国电信获取LAC、CID的方式
                    CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                    lacCID[0] = cdmaCellLocation.getNetworkId();
                    lacCID[1] = cdmaCellLocation.getBaseStationId();
                }
            }
        }

        if (lacCID[0] == 0 || lacCID[1] == 0) {
            return null;
        }

        return lacCID;

    }

    private static String getGpsLoaalTime(long gpsTime) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(gpsTime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String datestring = df.format(calendar.getTime());

        return datestring;
    }

}
