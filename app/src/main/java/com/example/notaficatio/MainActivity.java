package com.example.notaficatio;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.notaficatio.NotificationHandler.CHANNEL_1_ID;
import static com.example.notaficatio.NotificationHandler.CHANNEL_2_ID;
import static com.example.notaficatio.NotificationHandler.CHANNEL_3_ID;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reference;
    ArrayList<MyDoes> list;
    int notifyId1 = 1234;
    int notifyId2 = 5678;
    int notifyId3 = 9678;
    private int LOCATION_PERMISSION_CODE=133;

    NotificationManagerCompat notificationManagerCompat;
    String ar3="shahbag";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"Permission granted alredy",Toast.LENGTH_SHORT).show();
                }
                else{
                    requestLocationPermission();
                }



        list=new ArrayList<MyDoes>();
        sendOnChannel1("shakalaka","boom");
        reference= FirebaseDatabase.getInstance().getReference().child("DoesApp");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //set code to retrieve data and and replace layout
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    MyDoes myDoes=dataSnapshot1.getValue(MyDoes.class);
                    list.add(myDoes);

                    System.out.println(myDoes.getDateDoes());

                    String ar1=myDoes.locationDoes;


                    String ar2=myDoes.getTitleDoes();
                    String ar33=myDoes.dateDoes;


                        sendOnChannel1(ar1,ar2+" "+ar33);
                    sendOnChannel2(ar1,ar2+" "+ar33);
                    sendOnChannel3(ar1,ar2+" "+ar33);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //set code to show an error
                Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void requestLocationPermission(){

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            new AlertDialog.Builder(this)
                    .setTitle("Location permission")
                    .setMessage("Allow Locatio app to access this devices location?")
                    .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    LOCATION_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("DENY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();


        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==LOCATION_PERMISSION_CODE){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"Permission Granted",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendOnChannel1(String title,String message) {


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "some_channel_id";


        Notification notification = new Notification.Builder(MainActivity.this)
                .setContentTitle(title)

                .setContentText(message)
                .setSmallIcon(R.drawable.to_do_list_icon_notification)
                .setChannelId(CHANNEL_1_ID)
                .build();
        // This code is for going to app on clicking the notification.
        notification.contentIntent=PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

         notificationManager.notify(notifyId1, notification);
        notifyId1++;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendOnChannel2(String title,String message) {


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "some_channel_id";


        Notification notification = new Notification.Builder(MainActivity.this)
                .setContentTitle(title)

                .setContentText(message)
                .setSmallIcon(R.drawable.alarm_icon_notification)
                .setChannelId(CHANNEL_2_ID)
                .build();
        // This code is for going to app on clicking the notification.
        notification.contentIntent=PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        notificationManager.notify(notifyId2, notification);
        notifyId2++;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendOnChannel3(String title,String message) {


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "some_channel_id";


        Notification notification = new Notification.Builder(MainActivity.this)
                .setContentTitle(title)

                .setContentText(message)
                .setSmallIcon(R.drawable.silencer_icon_notification)
                .setChannelId(CHANNEL_3_ID)
                .build();
        // This code is for going to app on clicking the notification.
        notification.contentIntent=PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        notificationManager.notify(notifyId3, notification);
        notifyId3++;
    }

    }
