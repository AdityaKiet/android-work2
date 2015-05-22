package com.o9pathshala.notifications;

import com.o9paathshala.R;
import com.o9pathshala.student.slidingmenu.fragments.MainActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;


/*public class NotificationBuilderAlarmReceiver extends BroadcastReceiver {
    private PendingIntent mAlarmSender;
	 @Override
	    public void onReceive(Context context, Intent intent) {
	        generateNotification(context,  intent);
	    }
	 
	 
	    public void generateNotification(Context context, Intent intent)
	    {	
	    	NotificationManager notifManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
			Notification note = new Notification(R.drawable.logo, "Message from O9Paathshala", System.currentTimeMillis());
			intent = new Intent(context, MainActivity.class);
	        PendingIntent bintent = PendingIntent.getActivity(context, 0,intent, 0);
	        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.notification);
	        mediaPlayer.start();
	        note.setLatestEventInfo(context, "New Tests has been uploaded ", "Review new tests uploaded by o9paathshala for you !!", bintent);
	        notifManager.notify(123, note);
	        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	        Calendar c = Calendar.getInstance();
	        c.add(Calendar.SECOND, 10);
	        long firstTime = c.getTimeInMillis();
	        mAlarmSender = PendingIntent.getBroadcast(context, 0, new Intent(context, NotificationBuilderAlarmReceiver.class), 0);
	        am.set(AlarmManager.RTC_WAKEUP, firstTime, mAlarmSender);
	        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	    	if(null != sharedPreferences.getString("session", null)){
	    		GetNotificationAsynTask asynDemo = new GetNotificationAsynTask(context);
	    		asynDemo.execute();
	    	}
	    }

	}*/
public class NotificationBuilderAlarmReceiver extends Service{
	  @Override
	  public void onCreate() 
	  {   


	  Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();
	  Intent resultIntent=new Intent(this, MainActivity.class);
	  PendingIntent pIntent=PendingIntent.getActivity(this,0,resultIntent,0);


	 /* Notification noti_builder= new Notification.Builder(this)
	  .setContentTitle("Don't forget to plan your activitites for the day! ")
	  .setContentIntent(pIntent).build();*/
	  Notification noti_builder = new Notification(R.drawable.ic_launcher,"Android Example Status message!", System.currentTimeMillis());
	  NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); //what does this do!?


	  noti_builder.flags |=Notification.FLAG_AUTO_CANCEL;

	  notificationManager.notify(1,noti_builder); 

	  }
	  @Override
	      public IBinder onBind(Intent intent) {
	      return null;
	      }
}