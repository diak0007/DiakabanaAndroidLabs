package com.example.diakabana.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diakabana.data.MainViewModel;
import com.example.diakabana.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TextView mytext;
    private Button btn;
    private EditText myedit;
    private ActivityMainBinding variableBinding;
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        model = new ViewModelProvider(this).get(MainViewModel.class);
        mytext = variableBinding.textview;
        btn = variableBinding.mybutton;
        myedit = variableBinding.myedittext;
        btn.setOnClickListener(view -> {
            model.editString.postValue(myedit.getText().toString());
            mytext.setText("Your edit text has: " + model.editString);
        });
        model.editString.observe(this, s ->{
            mytext.setText("Your edit text has: " + model.editString);
        });

        model.isSelected.observe(this, selected ->{

            variableBinding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                model.isSelected.postValue(isChecked);
            });

            variableBinding.radiobutton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                model.isSelected.postValue(isChecked);
            });

            variableBinding.switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
                model.isSelected.postValue(isChecked);
            });

            variableBinding.checkbox.setChecked(selected);
            variableBinding.radiobutton.setChecked(selected);
            variableBinding.switch1.setChecked(selected);
            Toast.makeText(MainActivity.this, "The value is now: " + selected, Toast.LENGTH_SHORT).show();
        });
    }
}