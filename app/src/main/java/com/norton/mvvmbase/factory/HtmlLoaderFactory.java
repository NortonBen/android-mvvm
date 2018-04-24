package com.norton.mvvmbase.factory;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.norton.mvvmbase.BaseApplication;

import java.io.IOException;
import java.io.InputStream;

public class HtmlLoaderFactory {

    public Context context;

    private String _tmp;
    private String _fileName;

    public HtmlLoaderFactory setContext(Context context) {
        this.context = context;
        return this;
    }


    private void loadTemp(String fileName) {
        String htmlText = "";
        AssetManager assetManager = context.getApplicationContext().getResources().getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            _tmp = new String(b);
            inputStream.close();
        } catch (IOException e) {
            Log.e("log", "Couldn't open upgrade-alert.html", e);
        }
    }

    public String load(String fileName, String tag, String text) {
        _fileName = fileName;
        loadTemp(fileName);
        String rs = _tmp.replace(tag, text);
        return  rs;
    }

    public HtmlLoaderFactory load(String fileName) {
        _fileName = fileName;
        loadTemp(fileName);
        return this;
    }

    public HtmlLoaderFactory reload() {
        loadTemp(_fileName);
        return this;
    }

    public HtmlLoaderFactory replace(String tag, String text) {
        this._tmp = this._tmp.replace(tag, text);
        return this;
    }

    public String getTmp() {
        return _tmp;
    }
}
