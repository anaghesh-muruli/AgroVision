package com.anaghesh.agrovision;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Control extends AppCompatActivity {
    private ListView sprinkler_listview;
    private Control_Adapter control_adapter;
    ArrayList<String> sprinkler_list = new ArrayList<>();
    ArrayList<String> status_list = new ArrayList<>();
    private TextView status;
    private Bluetooth bt;
    public final String TAG = "Main";
    public static LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sprinkler_listview = (ListView) findViewById(R.id.control_list_view);
        status = (TextView) findViewById(R.id.control_status);
        sprinkler_list.add("Sprinkler 1");
        status_list.add("Functioning Normally");
        sprinkler_list.add("Sprinkler 2");
        status_list.add("Not connected");
        sprinkler_list.add("Pump 1");
        status_list.add("Not connected");
        sprinkler_list.add("Pump 2");
        status_list.add("Not connected");
        bt = new Bluetooth(this, mHandler);
        connectService();
        control_adapter = new Control_Adapter(sprinkler_list, status_list, this, bt);
        sprinkler_listview.setAdapter(control_adapter);
        control_adapter.notifyDataSetChanged();
        inflater = getLayoutInflater();


        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectService();
                control_adapter.notifyDataSetChanged();
            }
        });


    }
    public void connectService(){
        try {
            status.setText("Status : Connecting...");
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter.isEnabled()) {
                bt.start();
                bt.connectDevice("HC-05");
                status.setText("Status : Connected");
            } else {
                status.setText("Status : Bluetooth Not enabled");
            }
        } catch(Exception e){
            status.setText("Status : Unable to connect " +e);
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Bluetooth.MESSAGE_STATE_CHANGE:
                    Log.d(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    break;
                case Bluetooth.MESSAGE_WRITE:
                    Log.d(TAG, "MESSAGE_WRITE ");
                    break;
                case Bluetooth.MESSAGE_READ:
                    Log.d(TAG, "MESSAGE_READ ");
                    break;
                case Bluetooth.MESSAGE_DEVICE_NAME:
                    Log.d(TAG, "MESSAGE_DEVICE_NAME "+msg);
                    break;
                case Bluetooth.MESSAGE_TOAST:
                    Log.d(TAG, "MESSAGE_TOAST "+msg);
                    break;
            }
        }
    };

}
