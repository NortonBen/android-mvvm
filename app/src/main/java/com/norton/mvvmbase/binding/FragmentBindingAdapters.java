package com.norton.mvvmbase.binding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.norton.mvvmbase.factory.HtmlLoaderFactory;

import javax.inject.Inject;

/**
 * Binding adapters that work with a fragment instance.
 */
public class FragmentBindingAdapters {
    final Fragment fragment;
    final HtmlLoaderFactory htmlLoaderFactory;

    @Inject
    public FragmentBindingAdapters(Fragment fragment) {
        this.fragment = fragment;
        this.htmlLoaderFactory = new HtmlLoaderFactory();
    }
    @BindingAdapter("imageUrl")
    public void bindImage(ImageView imageView, String url) {
        Glide.with(fragment).load(url).into(imageView);
    }

    @BindingAdapter("imageByte")
    public void bindImageToBye(ImageView imageView, byte[] data) {
        if (data != null && data.length > 0) {
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            imageView.setImageBitmap(bmp);
        }

    }

    @BindingAdapter("backgroundImage")
    public void bindBackgroundImage(View view, byte[] data) {
        if (data != null && data.length > 0) {
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(fragment.getResources(), bmp);
            view.setBackground(bitmapDrawable);
        }
    }


    @BindingAdapter({"html"})
    public void bindHtmlWebView(WebView webView, String file, String html) {
        if (html != null && !html.isEmpty()) {
            htmlLoaderFactory.setContext(this.fragment.getContext());
            String content = htmlLoaderFactory.load("temp.html","[html]", html);
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            webView.loadData(content,"text/html","utf-8");
        }
    }
}