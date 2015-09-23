package com.paimai.reactnative;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by moxun on 15/9/18.
 */
public class JSDownloader {
    public static void download(Context context,String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("downloader","success, total" + response.length()/1000 +" K");
                try {
                    String path = "/data/data/com.paimai.auctiondemo/files/ReactNativeDevBundle.js";
                    File file = new File(path);
                    if (file.exists()) {
                        Log.e("old js",file.getPath() + ", " + file.length() / 1024 + " KB");
                    }
                    FileOutputStream outputStream = new FileOutputStream(file);
                    byte [] bytes = response.getBytes();
                    outputStream.write(bytes);
                    outputStream.close();
                    Log.e("new js", file.getPath() + ", " + file.length() / 1024 + " KB");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response",error.toString());
            }
        });
        queue.add(stringRequest);
    }
}
