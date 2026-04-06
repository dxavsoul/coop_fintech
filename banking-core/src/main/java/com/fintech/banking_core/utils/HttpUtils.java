package com.fintech.banking_core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;


@Slf4j
public class HttpUtils {
    private HttpUtils() {
    }

    public static void checkResponse(HttpResponse response) throws Exception{
        log.debug("http response status code: {}", response.getStatusLine().getStatusCode());

        if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() >= 300) {
            log.error("Error occurred while processing the request. Status code: {}", response.getStatusLine().getStatusCode());
            throw new Exception("Error occurred while processing the request. Status code: " + response.getStatusLine().getStatusCode());

        }
    }

    public static String createBaseUrl(String hostname, boolean isSecure) {
        StringBuilder builder = new StringBuilder();
        if (isSecure) {
            builder.append("https://");
        } else {
            builder.append("http://");
        }

        builder.append(hostname);

        log.debug("createBaseUrl: {}", builder);
        return builder.toString();
    }
}
