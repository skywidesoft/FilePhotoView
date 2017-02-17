package com.skywidesoft.filephotoview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;

public class FilePhotoViewActivity extends AppCompatActivity {

    Button closeBtn;
    ImageView imageView;
    PhotoViewAttacher mAttacher;
    String imageFilePath;

    /**
     * RequestListener for Glide image processing library
     */
    private RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {

        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            Log.e("FilePhotoViewActivity", "Glide error with exception: " + e.getMessage());
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            Log.e("FilePhotoViewActivity", "Glide - image resource is ready");

            // Attach PhotoView to imageView upon resource ready
            mAttacher = new PhotoViewAttacher(imageView);

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_photo_view);

        // Get image file path
        Bundle b = getIntent().getExtras();
        imageFilePath = b.getString("imageFilePath");

        Log.d("FilePhotoViewActivity", "imageFilePath: " + imageFilePath);

        // Retrieve file and display
        imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this)
                .load(imageFilePath)
                .listener(requestListener)
                .into(imageView);

        closeBtn = (Button) findViewById(R.id.closeBtn);

        // Close activity
        closeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Delete photo file
                File imageFile = new File(imageFilePath);
                boolean deleteResult = imageFile.delete();

                if (deleteResult) {
                    Log.d("FilePhotoViewActivity", "Image file deleted successfully");
                } else {
                    Log.e("FilePhotoViewActivity", "Fail deleting image file");
                }

                FilePhotoViewActivity.this.finish();

            }

        });

    }
}
