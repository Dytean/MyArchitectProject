package com.dan.myarchitectproject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dan.myarchitectproject.db.DbUtils;
import com.dan.myarchitectproject.db.entity.Person;
import com.dan.myarchitectproject.http.CommonResultBean;
import com.dan.myarchitectproject.http.CommonResultListBean;
import com.dan.myarchitectproject.http.HttpCallBack;
import com.dan.myarchitectproject.test.TestDataHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends Activity {

    @BindView(R.id.image_view)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Person person = new Person();
        person.setName("test");
        person.setHeight("170");
        person.setSex("girl");
//        BaseApplication.getDaoSession().getPersonDao().insert(person);
        DbUtils.getInstance().insert(person);
        TestDataHandler.getInstance().get(getApplicationContext(), new HttpCallBack<CommonResultBean<String>>() {
            @Override
            public void onSuccess(Call call, Response response, CommonResultBean<String> stringCommonResultBean) {
                String str = stringCommonResultBean.getResult();
            }

            @Override
            public void onFailed(Call call, Exception e) {

            }
        });


//        Glide.with(MainActivity.this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492170704443&di=8a34a426b6a99d3568c75154650270d7&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3Df9d33004c05c1038242bc6c68721bf25%2F060828381f30e924a81aac2f4a086e061d95f775.jpg")
//                .asBitmap().transform(new RoundRectTransformation(MainActivity.this,30)).into(mImageView);

        Glide.with(MainActivity.this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492170704443&di=8a34a426b6a99d3568c75154650270d7&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3Df9d33004c05c1038242bc6c68721bf25%2F060828381f30e924a81aac2f4a086e061d95f775.jpg")
                .centerCrop().into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                mImageView.setImageDrawable(resource);

            }
        });
    }

    private Bitmap readBitmapFromFileDescriptor(String filePath, int width, int height) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);
            int srcWidth = options.outWidth;
            int srcHeight = options.outHeight;
            int inSampleSize = 1;
            if (srcWidth > width || srcHeight > height) {
                if (srcWidth > srcHeight) {
                    inSampleSize = Math.round(srcHeight / height);
                } else {
                    inSampleSize = Math.round(srcWidth / width);
                }
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = inSampleSize;
            return BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap readFromInputStream(InputStream is, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        int inSampleSize = 1;
        if (srcWidth > width || srcHeight > height) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeStream(is, null, options);
    }

    private Bitmap readFromResources(Resources resources, int resourceId, int width, int height) {
        InputStream is = resources.openRawResource(resourceId);
        return readFromInputStream(is, width, height);
    }

    //图片透明度
    class MyTransformation extends BitmapTransformation{

        public MyTransformation(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap bitmap = pool.get(outWidth,outHeight, Bitmap.Config.ARGB_8888);
            if (bitmap == null)
                bitmap = Bitmap.createBitmap(outWidth,outHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setAlpha(128);
            canvas.drawBitmap(toTransform,0,0,paint);
            return bitmap;
        }

        @Override
        public String getId() {
            return "com.dan.myarchitectproject.MainActivity.MyTransformation";
        }
    }

    //图片圆角
    class RoundRectTransformation implements Transformation<Bitmap>{

        private BitmapPool pool;
        private int radius;

        public RoundRectTransformation(Context context, int radius){
            this(Glide.get(context).getBitmapPool(),radius);
        }

        public RoundRectTransformation(BitmapPool pool, int radius){
            this.pool = pool;
            this.radius = radius;
        }

        @Override
        public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
            Bitmap source = resource.get();
            int width = source.getWidth();
            int height = source.getHeight();
            Bitmap result = pool.get(width,height, Bitmap.Config.ARGB_8888);
            if (result == null){
                result = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawRoundRect(new RectF(0,0,width,height),radius,radius,paint);
            return BitmapResource.obtain(result,pool);
        }

        @Override
        public String getId() {
            return "RoundRectTransformation(radius=" + radius + ")";
        }
    }

}
