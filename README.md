File Photo Viewer
------------------

A simple library for viewing an image file on Android from local storage.

[![Release](https://jitpack.io/v/skywidesoft/FilePhotoView.svg)]
(https://jitpack.io/#skywidesoft/FilePhotoView)

### Libraries used
* [Glide](https://github.com/bumptech/glide): for image photo loading
* [PhotoView](https://github.com/chrisbanes/PhotoView): for image viewing with zoom functions

### Gradle dependency
Add it to your build.gradle with:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
and:

```gradle
dependencies {
    compile 'com.github.skywidesoft:FilePhotoView:{latest version}'
}
```

### Usage
Use this library is very simple, you just need to invoke the class "com.skywidesoft.filephotoview.FilePhotoViewActivity" and pass in the absolute path of the file to view.

### Sample
The following is a sample Activity class that do the following:
* Download a file from Internet and turn into a Bitmap image
* Generate a uuid for the file name
* Save the file to application cache
* Create a new Intent for the FilePhotoViewActivity, and pass the file name (with the argument name "imageFilePath")

### Sample code snippet (from sample folder)
```
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
```


