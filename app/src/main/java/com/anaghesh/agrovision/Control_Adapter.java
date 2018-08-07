package com.anaghesh.agrovision;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import static com.anaghesh.agrovision.R.id.time_set;

/**
 * Created by Allan Akshay on 10-10-2017.
 */

public class Control_Adapter extends BaseAdapter  implements ListAdapter {
    private ArrayList<String> sprinkler_list = new ArrayList<>();
    private ArrayList<String> status_list = new ArrayList<>();
    private Context context;
    private Bluetooth bluetooth;
    private View view;

    public Control_Adapter(ArrayList<String> sprinkler_list, ArrayList<String> status_list, Context context, Bluetooth bluetooth)
    {
        this.sprinkler_list = sprinkler_list;
        this.status_list = status_list;
        this.context = context;
        this.bluetooth = bluetooth;
    }
    @Override
    public int getCount() {
        return sprinkler_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = convertView;
        final View newview = Control.inflater.inflate(R.layout.timer_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(newview);
        builder.setCancelable(true);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sprinkler_list, null);
        }
        final EditText timer_minutes = (EditText) newview.findViewById(R.id.timer_minutes);
        final EditText timer_seconds = (EditText) newview.findViewById(R.id.timer_seconds);
        Button timer_set = (Button) newview.findViewById(time_set);
        TextView sprinkler_number = (TextView) view.findViewById(R.id.sprinkler_number);
        TextView sprinkler_status = (TextView) view.findViewById(R.id.sprinkler_status);
        ToggleButton sprinkler_toggle = (ToggleButton) view.findViewById(R.id.sprinkler_toggle);

        sprinkler_number.setText(sprinkler_list.get(position));
        sprinkler_status.setText("Status : " + status_list.get(position));
        if(!status_list.get(position).equals("Not connected")) {
            sprinkler_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                           //bluetooth.sendMessage("5");
                            bluetooth.sendMessage("3");
                        } else {
                           bluetooth.sendMessage("0");
                            //bluetooth.sendMessage("3");
                        }

                }
            });
        }
        else
            sprinkler_toggle.setEnabled(false);

        sprinkler_toggle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(newview.getParent()!=null)
                {
                    ((ViewGroup)newview.getParent()).removeView(newview);
                }
                builder.show();
                return true;
            }
        });
        timer_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetooth.sendMessage("5");
                int milliseconds;
                milliseconds = (Integer.parseInt(timer_minutes.getText().toString()) * 60 * 1000 + Integer.parseInt(timer_seconds.getText().toString()) * 1000);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bluetooth.sendMessage("5");
                    }
                }, milliseconds );
            }
        });
        return view;
    }
}
