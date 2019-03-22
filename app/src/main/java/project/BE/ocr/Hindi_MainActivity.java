package project.BE.ocr;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hindi_MainActivity extends AppCompatActivity {

   Bitmap image;
    private TessBaseAPI mTess;
    public String FilenameH;
    String datapath = "";
    private static final int REQUEST_GALLERY = 0;
    Uri selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindi__main);
        ImageView img = findViewById(R.id.imageView);
        findViewById(R.id.button_chooseH).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_GALLERY);
            }
        });

        image=((BitmapDrawable)img.getDrawable()).getBitmap();

        //init image
        //image = BitmapFactory.decodeResource(getResources(), R.drawable.sample);


        //initialize Tesseract API
//        String language = "eng";
        String language = "hin";
//          String language = "tam";
//        String language = "deu";
        datapath = getFilesDir()+ "/tesseract/";
        mTess = new TessBaseAPI();

        checkFile(new File(datapath + "tessdata/"));

        mTess.init(datapath, language, TessBaseAPI.OEM_TESSERACT_ONLY);
    }

    public void processImage(View view){
        String OCRresult = null;
        mTess.setImage(image);
        OCRresult = mTess.getUTF8Text();
        //Toast.makeText(getBaseContext(),OCRresult, Toast.LENGTH_SHORT).show();

      //  TextView OCRTextView = (TextView) findViewById(R.id.OCRTextView);
       // OCRTextView.setText(OCRresult);

      //  Log.i(TAG, "here at 2nd");
        String timeStampnew = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        FilenameH = timeStampnew + ".txt";
        Intent intent = new Intent(this,Main2Activity.class);
        intent.putExtra("data", (CharSequence) OCRresult);
        intent.putExtra("filename",FilenameH);
        startActivity(intent);
    }

    private void checkFile(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
                copyFiles();
        }
        if(dir.exists()) {
//            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            String datafilepath = datapath+ "/tessdata/hin.traineddata";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
//                    inspect(data.getData());
                    ImageView imageveew=(ImageView) findViewById(R.id.imageView);
                    imageveew.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    selectedImage = data.getData();
                    imageveew.setImageURI(selectedImage);

                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
    private void copyFiles() {
        try {
            String filepath = datapath + "/tessdata/hin.traineddata";
            AssetManager assetManager = getAssets();

            InputStream instream = assetManager.open("tessdata/hin.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }


            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
