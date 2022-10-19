package com.aq.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends  AppCompatActivity implements DateDialog.DateDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fab = (Button) findViewById(R.id.HW);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateDialog dateDialog = new DateDialog(MainActivity.this);
                dateDialog.setDateDialogListener(MainActivity.this);
                dateDialog.show();
            }
        });
    }

    @Override
    public void OnDateValidate(String dateTime) {
        Toast.makeText(this, dateTime, Toast.LENGTH_SHORT).show();

    }
}