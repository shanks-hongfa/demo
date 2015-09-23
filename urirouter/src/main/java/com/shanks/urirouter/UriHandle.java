package com.shanks.urirouter;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.shanks.urirouter.base.Dns;
import com.shanks.urirouter.base.Response;
import com.shanks.urirouter.base.Router;
import com.shanks.urirouter.base.UriProxy;

/**
 * Created by shanksYao on 9/9/15.
 */
public class UriHandle {


    public static void init() {

        Router router = new Router(new Router.Render() {
            @Override
            public boolean render(Context context, Uri uri) {
                Toast.makeText(context, uri.toString() + "*****default", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        Dns.register("h5.m.taobao.com", router);

        router.addRouter("/paimai/v3/index.html", new Router.Render() {
            @Override
            public boolean render(Context context, Uri uri) {
                Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public static Response open(Context context, String uriStr) {
        return UriProxy.openUri(context, uriStr);
    }

}
