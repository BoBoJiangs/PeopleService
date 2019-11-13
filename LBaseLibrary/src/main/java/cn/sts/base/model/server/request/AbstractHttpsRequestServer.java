package cn.sts.base.model.server.request;


import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * https
 * Created by weilin on 2018/4/24.
 */
public abstract class AbstractHttpsRequestServer<T> extends AbstractRequestServer<T> {

    private static final String TAG = "AbstractHttpsRequestServer";

    /**
     * 使用协议
     */
    private static final String CLIENT_AGREEMENT = "TLS";
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";


    @Override
    public OkHttpClient.Builder getOkHttpClientBuilder() {
        SSLSocketFactory sslSocketFactory = getSSLSocketFactory();
        if (sslSocketFactory == null) {
            return super.getOkHttpClientBuilder();
        }
        return super.getOkHttpClientBuilder().sslSocketFactory(sslSocketFactory, new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        });
    }

    /**
     * 获取bks文件的SSLSocketFactory
     */
    private SSLSocketFactory getSSLSocketFactory() {
        InputStream inputStream = null;
        try {
            //取得SSL的SSLContext实例
            SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
            //取得TrustManagerFactory的X509密钥管理器实例
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            //取得BKS密库实例
            KeyStore keyStore = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
            inputStream = getCertificateResource();
            keyStore.load(inputStream, getCertificatePassword().toCharArray());
            //初始化密钥管理器
            trustManager.init(keyStore);
            //初始化SSLContext
            sslContext.init(null, trustManager.getTrustManagers(), null);
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取证书资源,通过 context.getResources().openRawResource("") 获取
     *
     * @return 证书id
     */
    public abstract InputStream getCertificateResource();

    /**
     * 获取信任证书密码
     */
    public abstract String getCertificatePassword();

}
