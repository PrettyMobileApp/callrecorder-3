package com.prettymobileapp.liu.callrecorder;
// this page will show all SMS stats.

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {
    TextView statDetail;
    EditText edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.prettymobileapp.liu.callrecorder.R.layout.statspage);

        statDetail=(TextView) findViewById(com.prettymobileapp.liu.callrecorder.R.id.textViewstatdetail);
        getstat();


    }
    public void clickgetstats(View v) { getstat(); }
    public void clickback(View v) { Intent i = new Intent(getApplicationContext(), HomePageActivity.class); i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);startActivity(i); finish(); }

    protected void getstat(){
        int totalMinute=0,inMinute=0,outMinute=0;
        int totalCall=0,totalMsg=0,inCall=0,inMsg=0,outCall=0,outMsg=0,missCall=0;
        edittext=(EditText) findViewById(com.prettymobileapp.liu.callrecorder.R.id.editText);
        long days= Long.parseLong(edittext.getText().toString());

        Uri calluri = Uri.parse("content://call_log/calls");
        Cursor managedCursor = getContentResolver().query(calluri, null,null, null, null);

        int type=managedCursor.getColumnIndex("type");
        int duration=managedCursor.getColumnIndex("duration");
        int calldatetype=managedCursor.getColumnIndex("date");
        long  currentdate=System.currentTimeMillis();

        long calldate,statday;
        while(managedCursor.moveToNext()){
            calldate= Long.parseLong(managedCursor.getString(calldatetype));
            statday=currentdate-calldate-86400000*days;
           if(statday<0) {
              String callType = managedCursor.getString(type);
              int callTypeNumber = Integer.parseInt(callType);
              String durationSecond = managedCursor.getString(duration);
              int durationSecondInt = Integer.parseInt(durationSecond);
              switch (callTypeNumber) {
                  case CallLog.Calls.INCOMING_TYPE:
                      outCall++;
                      inMinute = inMinute + (int) Math.ceil(durationSecondInt / 60.0);
                      break;
                  case CallLog.Calls.OUTGOING_TYPE:
                      inCall++;
                      outMinute = outMinute + (int) Math.ceil(durationSecondInt / 60.0);
                      break;
                  case CallLog.Calls.MISSED_TYPE:
                      missCall++;
                      break;
              }
            }
        };
        managedCursor.close();
        totalCall=outCall+inCall+missCall;
        totalMinute=inMinute+outMinute;

        Uri message=Uri.parse("content://sms/inbox");

        Cursor smsCursor = getContentResolver().query(message, new String[]{"date"}, null, null, null);
        long insmsdate;
        long statinsmsday;

        while(smsCursor.moveToNext()){ insmsdate=Long.parseLong(smsCursor.getString(smsCursor.getColumnIndex("date"))); statinsmsday=currentdate-insmsdate-86400000*days; if(statinsmsday<0)inMsg++;}
        smsCursor.close();

        Uri message2=Uri.parse("content://sms/sent");

        Cursor smsCursor2 = getContentResolver().query(message2, new String[]{"date"}, null, null, null);
        long outsmsdate;
        long statoutsmsday;
        while(smsCursor2.moveToNext()){outsmsdate=Long.parseLong(smsCursor2.getString(smsCursor2.getColumnIndex("date"))); statoutsmsday=currentdate-outsmsdate-86400000*days;if(statoutsmsday<0)outMsg++;}
        smsCursor2.close();
        totalMsg=inMsg+outMsg;

        statDetail.setText("In the last "+days+" days"+
                "\n Calls in Total \t:\t"+totalCall+"\tcalls / "+totalMinute+"\tminutes"+
                "\n Incoming       \t:\t"+inCall+"\tcalls / "+inMinute+"\tminutes"+
                "\n Outgoing       \t\t:\t"+outCall+"\tcalls / "+outMinute+"\tminutes"+
                "\n Missed         \t\t:\t"+missCall+"\tcalls" +
                "\n\nSMS in Total    \t:\t"+totalMsg+"\tSMS"+
                "\n Incoming       \t:\t"+inMsg+"\tSMS"+
                "\n Outgoing       \t\t:\t"+outMsg+"\tSMS\n");
    }
}