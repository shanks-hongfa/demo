package com.shanks.auctiondemo;

import android.content.Context;
import android.widget.Toast;

import com.shanks.urirouter.base.Response;
import com.shanks.urirouter.base.UriProxy;

/**
 * Created by shanksYao on 9/15/15.
 */
public class UriManager {
    public static void open(Context context,String uri){
       Response response= UriProxy.openUri(context, uri);
        if(response.code!=Response.OK){
            Toast.makeText(context,"mylibrary may be uninstalled",Toast.LENGTH_SHORT).show();
        }
    }
}
