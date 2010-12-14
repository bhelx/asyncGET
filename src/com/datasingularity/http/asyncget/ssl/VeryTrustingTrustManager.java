package com.datasingularity.http.asyncget.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;


/**
 * Very trusting manager, just trust any ssl connection
 *
 * @author jpenton
 * @author bhelx
 */
public class VeryTrustingTrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}

