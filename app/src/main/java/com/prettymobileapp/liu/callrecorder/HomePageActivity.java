
/*
record incoming&outgoing calls, save to sd card/external storage,

declare(in manifest.xml) and create broadcastreceiver, to check calls,
create method to record.
broadcastreceiver detected calls -> call record method
when call finished. save audio to sd card.
 */

package com.prettymobileapp.liu.callrecorder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomePageActivity extends AppCompatActivity {

    public static String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO
            ,Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_SMS,Manifest.permission.READ_CALL_LOG,Manifest.permission.PROCESS_OUTGOING_CALLS};
    private static final String TAG = "checkError";
    MediaPlayer mp;
    String PLAY_FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.prettymobileapp.liu.callrecorder.R.layout.homepage);
        //
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,  Manifest.permission.READ_PHONE_STATE)) {
            } else {   ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.READ_PHONE_STATE},     99);    }
        }

        if (ContextCompat.checkSelfPermission(this,  Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            } else {   ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.READ_SMS},     99);        }
        }

        if(android.os.Build.VERSION.SDK_INT>22) {
            requestPermissions(permissions, 99);

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CALL_LOG)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CALL_LOG)) {
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CALL_LOG},
                            99);
                }
            }

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.PROCESS_OUTGOING_CALLS)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.PROCESS_OUTGOING_CALLS)) {
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS},
                            99);
                }
            }


            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            99);
                }
            }

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            99);
                }
            }

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.RECORD_AUDIO)) {
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.RECORD_AUDIO},
                            99);
                }
            }
        }
        //
    }

    public void clickplayback(View v) throws Exception{
        File file=new File(Environment.getExternalStorageDirectory()+"/callrecording01.amr");
        if(file.exists()){
            Toast.makeText(this, com.prettymobileapp.liu.callrecorder.R.string.playcall, Toast.LENGTH_SHORT).show();
            ditchMediaPlayer1();
            mp=new MediaPlayer();
            try{PLAY_FILE= Environment.getExternalStorageDirectory()+"/callrecording01.amr";}catch (Exception e){e.printStackTrace();}
            mp.setDataSource(PLAY_FILE);
            mp.prepare();
            mp.start();}
        else{Toast.makeText(this, com.prettymobileapp.liu.callrecorder.R.string.filenotfound1, Toast.LENGTH_SHORT).show();}
    }

    //ditchMediaPlayer: if MP is playing, stop playing media.
    public void ditchMediaPlayer1(){if(mp!=null){try{mp.release();}catch(Exception e){e.printStackTrace();}}}
    //delete callrecording01.amr from SD card
    public void clickdelete(View v) throws Exception{
        Toast.makeText(this, com.prettymobileapp.liu.callrecorder.R.string.filedeleted, Toast.LENGTH_SHORT).show();
        File file=new File(Environment.getExternalStorageDirectory()+"/callrecording01.amr");
        if(file.exists()) {file.delete();}
    }
    //save file to SD card, folder name: CallRecording, file name is date&time
    public void clicksave(View v) throws Exception{
        File file=new File(Environment.getExternalStorageDirectory()+"/callrecording01.amr");
        if(file.exists()){
            Toast.makeText(this, com.prettymobileapp.liu.callrecorder.R.string.filesaved, Toast.LENGTH_SHORT).show();
            DateFormat dateformat=new SimpleDateFormat("yy-MM-dd-HH-mm-ss");  //use date&time as file name to save in /CallRecording folder.
            Date today= Calendar.getInstance().getTime();
            String datestring=dateformat.format(today);

            String foldername="CallRecording";
            File folder=new File(Environment.getExternalStorageDirectory(),foldername);
            if(!folder.exists()) {folder.mkdirs();}
            try{PLAY_FILE= Environment.getExternalStorageDirectory()+"/callrecording01.amr";}catch (Exception e){e.printStackTrace();}
            File play_file=new File(Environment.getExternalStorageDirectory()+"/callrecording01.amr");
            File save_file=new File(Environment.getExternalStorageDirectory()+"/CallRecording/"+datestring+".amr");
            play_file.renameTo(save_file);        }                 //move file to /CallRecording folder.
        else {Toast.makeText(this, com.prettymobileapp.liu.callrecorder.R.string.filenotfound2, Toast.LENGTH_SHORT).show();}
    }
//----------------------------------------------------------------------------------
    //click load
    public void clickload(View v) throws Exception{
        Intent i= new Intent(getApplicationContext(),LoadActivity.class);
        startActivity(i);
        }
    //click stat
    public void clickstat(View v) throws Exception{
       // Toast.makeText(this, com.prettymobileapp.liu.callrecorder.R.string.stat, Toast.LENGTH_SHORT).show();
        Intent i= new Intent(getApplicationContext(),StatsActivity.class);
        startActivity(i);
        }

//----------------------------------------------------------------------------------
    //exit app
    public void clickexit(View v) throws Exception{
       finishAffinity();}

}
