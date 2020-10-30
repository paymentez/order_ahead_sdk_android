package ar.com.fennoma.paymentezsdk.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;

public class ImageUtils {

    public interface ImageLoadListener{
        void onImageLoaded(ImageView imageView, DrawableImageViewTarget image);
        void onImageLoadFail(ImageView imageView);
    }

    public static void loadStoreImage(Activity activity, ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions();
        //requestOptions = requestOptions.placeholder(R.drawable.slider_placeholder);
        //requestOptions = requestOptions.error(R.drawable.slider_placeholder);
        doLoad(activity, imageView, url, null, requestOptions);
    }

    public static void loadProductImage(Activity activity, ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions();
        //requestOptions = requestOptions.placeholder(R.drawable.slider_placeholder);
        //requestOptions = requestOptions.error(R.drawable.slider_placeholder);
        doLoad(activity, imageView, url, null, requestOptions);
    }

    public static void doLoad(final Activity activity, final ImageView imageView, final String url, final ImageLoadListener listener, RequestOptions requestOptions){
        if(requestOptions == null) {
            requestOptions = new RequestOptions();
        }
        Glide.with(activity)
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(listener != null) {
                            listener.onImageLoadFail(imageView);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(listener != null){
                            listener.onImageLoaded(imageView, (DrawableImageViewTarget) target);
                        }
                        return false;
                    }
                })
                .into(imageView);
    }
}
