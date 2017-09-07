package com.standards.library.glide;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class UiUtil {

    public static void setRoundImage(ImageView imageView, String imageUrl, int loadingRes, int errorRes, int radius) {
        setRoundImage(false, imageView, imageUrl, loadingRes, errorRes, radius);
    }

    public static void setRoundImage(boolean isSkipMemoryCache, ImageView imageView, String imageUrl, int loadingRes, int errorRes, int radius) {
        if (imageView == null) return;

        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        try {
            Glide.with(context).load(imageUrl)
                    .placeholder(loadingRes)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(errorRes)
                    .fitCenter()
                    .skipMemoryCache(isSkipMemoryCache)
                    .bitmapTransform(new RoundedCornersTransformation(context, radius, 0, RoundedCornersTransformation.CornerType.ALL))
                    .into(imageView);
        } catch (Exception e) {
        }
    }


    public static void setImage(ImageView imageView, String imageUrl, int loadingRes, int errorRes) {
        if (imageView == null) return;

        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        try {
            Glide.with(context).load(imageUrl)
                    .placeholder(loadingRes)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(errorRes)
                    .into(imageView);
        } catch (Exception e) {
        }
    }

    public static void setCircleImage(final ImageView imageView, String imageUrl, int length, int loadingRes, int errorRes) {
        if (imageView == null) return;

        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context).load(imageUrl)
                    .placeholder(loadingRes)
                    .error(errorRes)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .override(length, length)
                    .into(imageView);
        } catch (Exception e) {
        }
    }

    public static void setImage(final ImageView imageView, String imageUrl, int errorRes, ResultListener listener) {
        setImageListener(DiskCacheStrategy.ALL, imageView, imageUrl, errorRes, listener);
    }

    public static void setLocalImage(final ImageView imageView, String imageUrl, int errorRes, ResultListener listener) {
        setImageListener(DiskCacheStrategy.NONE, imageView, imageUrl, errorRes, listener);
    }

    public static void setImageListener(DiskCacheStrategy diskCacheStrategy, ImageView imageView, String imageUrl, int errorRes, final ResultListener listener) {
        if (imageView == null) return;

        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context).load(imageUrl)
                    .diskCacheStrategy(diskCacheStrategy)
                    .error(errorRes)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            listener.failed();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            listener.success();
                            return false;
                        }
                    }).into(imageView);
        } catch (Exception e) {
        }

    }

    public interface ResultListener {
        void success();

        void failed();
    }
}
