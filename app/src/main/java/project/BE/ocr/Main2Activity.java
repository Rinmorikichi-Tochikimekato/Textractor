package project.BE.ocr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private TextView detectedTextViewe;

    private static final String TAG2 = Main2Activity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String musi = intent.getExtras().getString("data");

        Toast.makeText(Main2Activity.this, musi,
                Toast.LENGTH_LONG).show();

        Log.i(TAG2, "onCreate: data thevla re");
        detectedTextViewe = ((TextView) findViewById(R.id.detected_texter));
//        detectedTextView.setMovementMethod(new ScrollingMovementMethod());

       detectedTextViewe.setText(musi+"");

//        detectedTextViewe.setText(R.string.my_love_text);

    }
}
