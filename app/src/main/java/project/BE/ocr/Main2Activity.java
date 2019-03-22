package project.BE.ocr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private TextView detectedTextViewe;
    private static final int MY_PERMISSIONS_REQUESTS = 0;
    private static final String TAG2 = Main2Activity.class.getSimpleName();
    private static final String API_KEY = "AIzaSyD5mwCRzfkx2cgGwXb8M_jEp3SkIUpDIjs";

    private static final String TAG3 = Main2Activity.class.getSimpleName();
    private static final String TAG4 = Main2Activity.class.getSimpleName();
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUESTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    // FIXME: Handle this case the user denied to grant the permissions
                }
                break;
            }
            default:
                // TODO: Take care of this case later
                break;
        }
    }

    private void requestPermissions()
    {
        List<String> requiredPermissions = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requiredPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


        if (!requiredPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    requiredPermissions.toArray(new String[]{}),
                    MY_PERMISSIONS_REQUESTS);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        requestPermissions();

        Intent intent = getIntent();
        final String musi = intent.getExtras().getString("data");
        final String Filenamenew = intent.getExtras().getString("filename");
       Toast.makeText(Main2Activity.this, Filenamenew,
                Toast.LENGTH_LONG).show();

        Log.i(TAG2, "onCreate: data thevla re");
        detectedTextViewe = ((TextView) findViewById(R.id.detected_texter));
     detectedTextViewe.setMovementMethod(new ScrollingMovementMethod());

       detectedTextViewe.setText(musi+"");



       findViewById(R.id.button_saving).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Toast.makeText(Main2Activity.this, "button clicked",
                       Toast.LENGTH_LONG).show();

               File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), Filenamenew);


               try {
                   FileOutputStream fos = new FileOutputStream(file);
                   fos.write(musi.getBytes());
                   fos.close();
                   Toast.makeText(Main2Activity.this, "saved", Toast.LENGTH_LONG).show();
               } catch (FileNotFoundException e) {
                   e.printStackTrace();
                   Toast.makeText(Main2Activity.this, "File not found", Toast.LENGTH_LONG).show();

               }catch (IOException e)
               {
                   e.printStackTrace();
                   Toast.makeText(Main2Activity.this, "Error in saving", Toast.LENGTH_LONG).show();


               }
           }
       });

               findViewById(R.id.button_translate).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       final Handler textViewHandler = new Handler();
                       Log.i(TAG3, "here after translation");

                       new AsyncTask<Void, Void, Void>() {
                           @Override
                           protected Void doInBackground(Void... params) {
                               TranslateOptions options = TranslateOptions.newBuilder()
                                       .setApiKey(API_KEY)
                                       .build();
                               Translate translate = options.getService();
                               final Translation translation =
                                       translate.translate(musi,
                                               Translate.TranslateOption.targetLanguage("hi"));

                               textViewHandler.post(new Runnable() {
                                   @Override
                                   public void run() {
                                       if (detectedTextViewe != null) {
                                           Log.i(TAG4, "here at display");
                                           detectedTextViewe.setText(translation.getTranslatedText());
                                       }
                                   }
                               });
                               return null;
                           }
                       }.execute();
                   }

               });





    }
}
