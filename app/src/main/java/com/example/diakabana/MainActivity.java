package com.example.diakabana;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Harmonie Diakabana
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /** This holds the text at the center of the screen*/
    private TextView tv = null;
    /** This holds the password*/
    private EditText et = null;
    /** This holds the login button*/
    private Button bt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        et = findViewById(R.id.editTextTextPassword);
        bt = findViewById(R.id.login);

        bt.setOnClickListener(clk ->{
            String password = et.getText().toString();

            if (checkPasswordComplexity( password )){
                tv.setText("Your password is complex enough");
            } else {
                tv.setText("You shall not pass!");
            }

        });
    }

    /**This function checks if the password is complex enough
     *
     * @param pw The String object we are testing
     * @return Returns true if the password is complex enough
     */
    boolean checkPasswordComplexity(String pw){
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);

            if (Character.isDigit(c)) {
                foundNumber = true;
            } else if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else {
                foundSpecial = true;
            }
        }

        if (!foundUpperCase) {
            Toast.makeText(this, "Your password doesn't contain an uppercase letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this, "Your password doesn't contain a lowercase letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(this, "Your password doesn't contain a digit", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this, "Your password doesn't contain a special character", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }
}