package com.labs.coursera.modernartui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


import java.net.URI;


public class MainActivity extends Activity {
    private DialogFragment mDialog;
    private SeekBar seekBar;
    static private final String URL = "http://www.moma.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.findViewById(android.R.id.content).setBackgroundColor(Color.BLACK);
        seekBar = (SeekBar)findViewById(R.id.SeekbarProgress);

        //Change the color of progress to red
        seekBar.setProgressDrawable(new ColorDrawable(Color.rgb(255, 0, 0)));

        //Change the thumb color to red too
       seekBar.getThumb().setColorFilter(Color.rgb(255,0,0), PorterDuff.Mode.SRC_ATOP);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int Rightprogress = 0;
            int Leftprogress = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                Rightprogress = progress;
                Leftprogress = progress;

                if (Rightprogress > 153)
                    Rightprogress = 153;

                if (Leftprogress > 204)
                    Leftprogress = 204;

                int maxColorValue = 153 + Rightprogress;
                // if it exceeds 255 then set it to 255
                if (maxColorValue>255)
                    maxColorValue = 255;

                int leftMaxColorValue = 76 + Leftprogress;
                // if it exceeds 255 then set it to 255
                if (leftMaxColorValue>255)
                    leftMaxColorValue = 255;

                //Change color of top right tile
                TextView topRight = (TextView)findViewById(R.id.FirstRightTile);
                topRight.setBackgroundColor(Color.rgb(maxColorValue,Rightprogress,0));

                //Change color of bottom right tile
                TextView bottomRight = (TextView)findViewById(R.id.ThirdRightTile);
                bottomRight.setBackgroundColor(Color.rgb(0,Rightprogress,153 - Rightprogress));

                //Change color of top left tile
                TextView topLeft = (TextView)findViewById(R.id.FirstLeftTile);
                topLeft.setBackgroundColor(Color.rgb(Leftprogress,leftMaxColorValue,153));

                //Change color of bottom left tile
                TextView bottomLeft = (TextView)findViewById(R.id.SecondLeftTile);
                bottomLeft.setBackgroundColor(Color.rgb(maxColorValue,Leftprogress,153));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.info) {
            showDialogFragment();
        }

        return super.onOptionsItemSelected(item);
    }

    // Show desired Dialog
    void showDialogFragment() {


        // Create a new AlertDialogFragment
        mDialog = AlertDialogFragment.newInstance();

        // Show AlertDialogFragment
        mDialog.show(getFragmentManager(), "ALERT");


    }

    // Abort or navigate to MOMA website
    private void navigateToMoma(boolean shouldContinue) {
        if (shouldContinue) {

           Intent navigate = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
           startActivity(navigate);
            mDialog.dismiss();

        } else {

            // Abort ShutDown and dismiss dialog
            mDialog.dismiss();
        }
    }

    // Class that creates the AlertDialog
    public static class AlertDialogFragment extends DialogFragment {

        public static AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }

        // Build AlertDialog using AlertDialog.Builder
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.alert_dialog, null);
            view.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity())
                            .navigateToMoma(false);
                }
            });
            view.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity())
                            .navigateToMoma(true);
                }
            });
            return new AlertDialog.Builder(getActivity(),android.R.style.Theme_DeviceDefault_Dialog_MinWidth)
                    .setView(view)

                     // User cannot dismiss dialog by hitting back button
                    .setCancelable(false)
                    .create();


        }
    }
}
