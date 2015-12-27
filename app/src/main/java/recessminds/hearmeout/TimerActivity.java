package recessminds.hearmeout;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.concurrent.TimeUnit;


public class TimerActivity extends Activity {
    Button btnStart, btnStop, buttondialog, about;
    TextView textviewTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_timer);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);

        buttondialog = (Button) findViewById(R.id.buttondialog);
        //about us
        // about = (Button) findViewById(R.id.about);


        textviewTime = (TextView) findViewById(R.id.textviewTime);
        textviewTime.setText("00:00:00");
        Intent i = getIntent();
        Bundle b = i.getExtras();
        String u = b.getString("time");
        textviewTime.setText(u);

        int h, m;
        String[] ret = new String[2];
        //split code
        int j = 0;
        for (String retval : u.split(":")) {
            ret[j] = retval;
            j++;
        }
        h = Integer.parseInt(ret[0]);
        m = Integer.parseInt(ret[1]);
        //code for conv. of hour and minute in milliseconds
        long mil = (long) ((h * 3600) + (m * 60)) * 1000;
        final CounterClass timer = new CounterClass(mil, 1000);

        buttondialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(TimerActivity.this, DialogActivity.class);
                startActivity(d);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
            }
        });

       /* about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(TimerActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });*/
    }
    public class CounterClass extends CountDownTimer
    {
        public CounterClass(long millisInFuture, long CountDownInterval)
        {
            super(millisInFuture, CountDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            textviewTime.setText(hms);
        }

        @Override
        public void onFinish() {
            textviewTime.setText("completed.");
            /*Intent i = new Intent(TimerActivity.this,hearme.class);
            startActivity(i);*/
            startService(new Intent(getBaseContext(), FirstService.class));
        }
    }
    public void OnClickExit(View view){
        stopService(new Intent(getBaseContext(), FirstService.class));
        System.exit(0);
    }
    public void OnClickAbout(View view){
        Intent i = new Intent(TimerActivity.this,AboutActivity.class);
        startActivity(i);
    }
}
