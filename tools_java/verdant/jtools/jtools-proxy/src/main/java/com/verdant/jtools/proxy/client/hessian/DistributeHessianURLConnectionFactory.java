package com.verdant.jtools.proxy.client.hessian;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianURLConnectionFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DistributeHessianURLConnectionFactory extends HessianURLConnectionFactory {

    private static final Logger log = Logger.getLogger(DistributeHessianURLConnectionFactory.class.getName());

    private HessianProxyFactory _proxyFactory;

    public void setHessianProxyFactory(HessianProxyFactory factory) {
        _proxyFactory = factory;
    }

    /**
     * Opens a new or recycled connection to the HTTP server.
     */
    public HessianConnection open(URL url)
            throws IOException {
        if (log.isLoggable(Level.FINER))
            log.finer(this + " open(" + url + ")");

        URLConnection conn = url.openConnection();

        conn.setUseCaches(false);

        // HttpURLConnection httpConn = (HttpURLConnection) conn;
        // httpConn.setRequestMethod("POST");
        // conn.setDoInput(true);

        long connectTimeout = _proxyFactory.getConnectTimeout();

        if (connectTimeout >= 0)
            conn.setConnectTimeout((int) connectTimeout);

        conn.setDoOutput(true);

        long readTimeout = _proxyFactory.getReadTimeout();

        if (readTimeout > 0) {
            try {
                conn.setReadTimeout((int) readTimeout);
            } catch (Throwable e) {
            }
        }

    /*
    // Used chunked mode when available, i.e. JDK 1.5.
    if (_proxyFactory.isChunkedPost() && conn instanceof HttpURLConnection) {
      try {
        HttpURLConnection httpConn = (HttpURLConnection) conn;

        httpConn.setChunkedStreamingMode(8 * 1024);
      } catch (Throwable e) {
      }
    }
    */

        return new DistributeHessianURLConnection(url, conn);
    }
}
