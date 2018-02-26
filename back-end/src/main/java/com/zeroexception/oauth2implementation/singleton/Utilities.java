package com.zeroexception.oauth2implementation.singleton;

import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


/**
 * The purpose of this class is to help application run more effective by creating
 * some static instance of classes to use globally.
 */

@Component
public class Utilities {

    /**
     * @see <a href="https://google.github.io/google-http-java-client/releases/1.21.0/javadoc/com/google/api/client/http/HttpTransport.html?is-external=true"></a>
     */

    private static Utilities instance = null;

    private static HttpEntity<String> httpEntityString;

    public final static NetHttpTransport NET_HTTP_TRANSPORT = new NetHttpTransport();

    public Utilities() {
        httpEntityString = new HttpEntity<>(new HttpHeaders());
    }

    public HttpEntity<String> getHttpEntityString() {
        if (httpEntityString == null) {
            httpEntityString = new HttpEntity<>(new HttpHeaders());
        }
        return httpEntityString;
    }

    public static Utilities getInstance() {
        if (instance == null)
            instance = new Utilities();
        return instance;
    }

}
