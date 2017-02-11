package com.prettymobileapp.liu.callrecorder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;

public class LoadActivity extends AppCompatActivity {

    ListView listview;
    ArrayAdapter<String> calladapter;
    String filename[];
    String path;
    File f2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.prettymobileapp.liu.callrecorder.R.layout.loadpage);

        path = Environment.getExternalStorageDirectory().toString()+"/CallRecording";
        File f = new File(path);
        File file[] = f.listFiles();
        if(file!=null) {

            int l = file.length;
            filename = new String[l];
            for (int i = 0; i < l; i++) {
                filename[i] = file[i].getName();
            }
            calladapter = new ArrayAdapter<String>(this, R.layout.list_white_text, filename);
            listview = (ListView) findViewById(com.prettymobileapp.liu.callrecorder.R.id.listView);
            listview.setAdapter(calladapter);
            listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long arg3) {
                    f2 = new File(path + "/" + filename[position]);
                    final int pos = position;
                    final String posi = filename[position];
                    //AlertDialog ref:http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LoadActivity.this);
                    builder1.setTitle("Delete File");
                    builder1.setMessage("Are you sure to delete?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    if (f2.exists()) {
                                        f2.delete();
                                        File fnew = new File(path);
                                        File filenew[] = fnew.listFiles();
                                        int lnew = filenew.length;
                                        String filenamenew[] = new String[lnew];
                                        for (int i = 0; i < lnew; i++) {
                                            filenamenew[i] = filenew[i].getName();
                                        }
                                        ArrayAdapter<String> calladapternew = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_white_text, filenamenew);
                                        listview.setAdapter(calladapternew);
                                    }
                                }
                            });

                    builder1.setNegativeButton("No", null);
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    return true;
                }
            });
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MediaPlayer mp3;
                    File f3 = new File(path + "/" + filename[position]);
                    if (f3.exists()) {
                        mp3 = new MediaPlayer();
                        String s = f3.toString();
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        try {
                            mp3.setDataSource(s);
                            mp3.prepare();
                            mp3.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), com.prettymobileapp.liu.callrecorder.R.string.filenotfound1, Toast.LENGTH_SHORT).show();
                    }
                    ;
                }
            });
        }else{ Toast.makeText(getApplicationContext(), com.prettymobileapp.liu.callrecorder.R.string.filenotfound1, Toast.LENGTH_SHORT).show();  }

    }
    public void clickback(View v) { Intent i = new Intent(getApplicationContext(), HomePageActivity.class); i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);startActivity(i); finish(); }

}
