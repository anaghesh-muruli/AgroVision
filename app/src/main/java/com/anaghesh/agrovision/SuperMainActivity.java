package com.anaghesh.agrovision;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SuperMainActivity extends AppCompatActivity {
    private TextView control;
    private Intent intent;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LayoutInflater inflater = getLayoutInflater();
        builder = new AlertDialog.Builder(this);
        final View view = inflater.inflate(R.layout.mainpage_popup,null);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((ViewGroup)view.getParent()).removeView(view);
            }
        });
        control = (TextView) findViewById(R.id._mainpage_control);
        if(view.getParent()!=null)
        {
            ((ViewGroup)view.getParent()).removeView(view);
        }
        builder.show();

        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SuperMainActivity.this, Control.class);
                startActivity(intent);
            }
        });

    }

}
