
package com.example.pushnotification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            receive();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }




    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu. main_menu , menu) ;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id. action_5:
                scheduleNotification(getNotification( "5 second delay" ) , 5000 ) ;
                return true;
            case R.id. action_10:
                scheduleNotification(getNotification( "10 second delay" ) , 10000 ) ;
                return true;
            case R.id. action_15:
                scheduleNotification(getNotification( "15 second delay" ) , 15000 ) ;
                return true;
            default:
                return super.onOptionsItemSelected(item) ;
        }
    }



    public static void send() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("SERVER_URL");
        factory.setUsername("USER_NAME");
        factory.setPassword("PASSWORD");
        factory.setVirtualHost("/");
        factory.setPort(5672);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("hello", false, false, false, null);
            String message = "Hello world from Android";
            channel.basicPublish("", "hello", null, message.getBytes("UTF-8"));
            Log.d("MESSAGE SENT: ", " [x] Sent '" + message + "'");
            channel.close();
            connection.close();
        }catch(TimeoutException e){
            e.printStackTrace();
        }
    }

    public void receive() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("SERVER_URL");
        factory.setUsername("USER_NAME");
        factory.setPassword("PASSWORD");
        factory.setVirtualHost("/");
        factory.setPort(5672);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("hello", false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                scheduleNotification(getNotification( message ) , 0 ) ;
            };
            channel.basicConsume("hello", true, deliverCallback, consumerTag -> { });
        }catch(TimeoutException e){
            e.printStackTrace();
        }

    }


    public void receiveQueue(View view) throws IOException, TimeoutException {
        receive();
    }

    public void sendQueue(View view) throws  IOException, TimeoutException {
        send();
    }



    private void scheduleNotification (Notification notification , int delay) {
        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + delay ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }

    private Notification getNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id );
        builder.setContentTitle( "Scheduled Notification" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID );
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setCategory(NotificationCompat.CATEGORY_CALL);

        return builder.build() ;
    }
}