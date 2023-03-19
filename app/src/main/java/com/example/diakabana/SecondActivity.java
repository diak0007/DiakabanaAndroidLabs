package com.example.diakabana;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    TextView myText;
    EditText phoneNumber;

    Button callPhone;
    ImageView profileImage;

    Button setProfile;
    SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        myText = findViewById(R.id.textView3);
        phoneNumber = findViewById(R.id.phoneNumber);
        callPhone = findViewById(R.id.call);
        profileImage = findViewById(R.id.profileImage);
        setProfile = findViewById(R.id.button6);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        myText.setText("Welcome Back " + emailAddress);


        callPhone.setOnClickListener(clk -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("PhoneNumber", phoneNumber.getText().toString());
            editor.apply();
            String number = phoneNumber.getText().toString();
            if (!number.isEmpty()) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:" + number));
                startActivity(call);
            }

        });

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        setProfile.setOnClickListener(clk ->{
            ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>(){
                        @Override
                        public void onActivityResult(ActivityResult result){
                            if (result.getResultCode() == Activity.RESULT_OK){
                                Intent data = result.getData();
                                Bitmap thumbnail = data.getParcelableExtra("data");
                                profileImage.setImageBitmap(thumbnail);

                                FileOutputStream fOut = null;

                                try { fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);

                                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                                    fOut.flush();

                                    fOut.close();

                                }

                                catch (IOException e)

                                { e.printStackTrace();

                                }
                            }

                        }
                    }

            );
            cameraResult.launch(cameraIntent);

        });

        File file = new File( getFilesDir(), "Picture.png");

        if(file.exists())

        {
            Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            profileImage.setImageBitmap( theImage );

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        String phone = prefs.getString("PhoneNumber", "");
        phoneNumber.setText(phone);

    }
}