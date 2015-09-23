package com.shanks.urirouter.base;

import android.content.Context;
import android.net.Uri;

/**
 * Created by shanksYao on 9/1/15.
 */
public class UriProxy {


    public static Response openUri(Context context,String uriString) {
        Response response;
        Uri uri = Uri.parse(uriString);
        if (uri == null||uri.getHost()==null){
            response=new Response();
            response.code= Response.URL_INVALID;
            return response;
        }
        /////schema 要注意一下
        Router router = Dns.lookup(uri.getHost());
        if (router == null){
            response=new Response();
            response.code= Response.ROUTER_NOT_FOUND;
            return response;
        }

        return router.handle(context,uri);
    }

}
