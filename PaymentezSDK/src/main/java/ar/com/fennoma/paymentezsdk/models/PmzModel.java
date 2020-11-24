package ar.com.fennoma.paymentezsdk.models;

import android.net.Uri;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public abstract class PmzModel {

    protected static String decode(String toDecode) {
        try {
            return URLDecoder.decode(toDecode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return toDecode;
        }
    }

    protected static String encode(String toEncode) {
        return Uri.encode(toEncode, "UTF-8");
    }
}
