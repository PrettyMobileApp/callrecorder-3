package com.prettymobileapp.liu.callrecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import java.io.File;

/** * Created by m on 03/07/2016.
 * when call coming in or outgoing, call_state changed, and callreceiver was called by Manifests file.
 * record call, and end recording when call_state changed to IDLE. */

public class CallReceiver extends BroadcastReceiver {

   public static MediaRecorder recorder_down;
    public static Boolean recordstarted=false;
    private static final String TAG = "checkError";
    String CALLRECORDINGFILE_DOWN;

    @Override
    public void onReceive(Context context,Intent intent){
       TelephonyManager telephonyManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        CALLRECORDINGFILE_DOWN= Environment.getExternalStorageDirectory()+"/callrecording01.amr";
        File outFile_down=new File(CALLRECORDINGFILE_DOWN);
        int phonestate=telephonyManager.getCallState();

        switch(phonestate){
            case 0:
                Log.v(TAG,"callreceiver -IDLE-");
                if(recordstarted && recorder_down!=null) {Log.v(TAG,"callreceiver -16-");recorder_down.stop();}
                recordstarted=false;
                Log.v(TAG,"callreceiver -17-");break;
            case 1:
                Log.v(TAG,"callreceiver -RINGING-"); break;
            case 2:
                Log.v(TAG,"callreceiver -OFFHOOK-");

               if(recordstarted!=true) {
                   if (outFile_down.exists()) {
                       outFile_down.delete();
                   }
                   recorder_down = new MediaRecorder();
                   recorder_down.setAudioSource(MediaRecorder.AudioSource.VOICE_DOWNLINK);
                   Log.v(TAG, "callreceiver -11-");
                   recorder_down.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                   recorder_down.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                   recorder_down.setOutputFile(CALLRECORDINGFILE_DOWN);

                   Log.v(TAG, "callreceiver -12-");
                   try {
                       recorder_down.prepare();
                       Log.v(TAG, "callreceiver -recorder down prepared-");
                   } catch (Exception e) {
                       e.printStackTrace();
                       Log.v(TAG, "callreceiver -recorder down NOT prepared-");
                   }
                   recordstarted = true;
                   Log.v(TAG, "callreceiver -13-");
                   recorder_down.start();
                   Log.v(TAG, "callreceiver -14-");
                   Toast.makeText(context, com.prettymobileapp.liu.callrecorder.R.string.startrecording, Toast.LENGTH_SHORT).show();
               }
                break;
        }
        Log.v(TAG,"callreceiver -9-");
    }

}
