package cn.sts.base.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 网络工具
 * Created by weilin on 2018/2/27.
 */

public class NetworkUtil {

    private static final String TAG = "NetworkUtil";

    /**
     * ping服务器是否在线
     *
     * @param ip   ip
     * @param port 端口
     * @return true在线，false离线
     */
    public static boolean pingServer(String ip, int port) {
        Socket mSocket = new Socket();
        boolean status = true;
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
            mSocket.connect(inetSocketAddress, 3000);
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        } finally {
            try {
                mSocket.close();
            } catch (IOException io) {
                io.printStackTrace();
                status = false;
            }
        }
        Logs.i(TAG, "当前服务器状态=" + status);
        return status;
    }
}
