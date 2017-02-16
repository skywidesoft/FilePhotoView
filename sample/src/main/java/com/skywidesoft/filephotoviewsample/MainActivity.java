package com.skywidesoft.filephotoviewsample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.skywidesoft.filephotoview.FilePhotoViewActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button viewBtn;
    EditText imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageUrl = (EditText) findViewById(R.id.imageUrl);

        viewBtn = (Button) findViewById(R.id.viewBtn);

        viewBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Download image from Internet and save to application cache
                Glide.with(MainActivity.this)
                        .load(imageUrl.getText().toString())
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                Log.d("MainActivity", "Image downloaded successfully");

                                // Save file to application cache
                                String imageFileName = UUID.randomUUID().toString() + ".jpg";
                                String imageFilePath = saveBitmapToCache(resource, imageFileName, MainActivity.this);

                                // Start photo view activity
                                Intent i = new Intent(MainActivity.this, FilePhotoViewActivity.class);
                                i.putExtra("imageFilePath", imageFilePath);

                                startActivity(i);
                            }
                        });


            }

        });
    }

    private String saveBitmapToCache(Bitmap resource, String fileName, Context context) {

        Log.d("MainActivity", "Saving image file to application cache");

        Log.d("MainActivity", "Cache directory: " + context.getCacheDir().getAbsolutePath());

        File imageFile = new File(context.getCacheDir().getAbsolutePath() + "/" + fileName);

        Log.d("MainActivity", "Saving file: " + imageFile.getAbsolutePath());

        String fileExt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

        Bitmap.CompressFormat format;

        if (fileExt.equals("png")) {

            format = Bitmap.CompressFormat.PNG;

        } else {

            format = Bitmap.CompressFormat.JPEG;

        }

        OutputStream outputStream;

        try {

            outputStream = new FileOutputStream(imageFile);

            resource.compress(format, 100, outputStream);

            Log.d("MainActivity", "Image file saved successfully");

        } catch (Exception ex) {

            Log.e("MainActivity", "Error saving image file: " + ex.getMessage());

        }

        return imageFile.getAbsolutePath();

    }
}
