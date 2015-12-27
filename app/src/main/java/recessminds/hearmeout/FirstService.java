package recessminds.hearmeout;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.view.KeyEvent;
import android.widget.Toast;

public class FirstService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        AudioManager m_amAudioMananager;
        m_amAudioMananager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //m_amAudioMananager.setStreamMute(AudioManager.STREAM_MUSIC,true);
        sendMediaButton(getApplicationContext(), KeyEvent.KEYCODE_MEDIA_PAUSE);
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
        m_amAudioMananager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        }
        else
        {
            m_amAudioMananager.setMode(AudioManager.MODE_IN_CALL);
        }
        m_amAudioMananager.setSpeakerphoneOn(true);
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
    private static void sendMediaButton(Context context, int keyCode) {
        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
        context.sendOrderedBroadcast(intent, null, null, null, Activity.RESULT_OK, null, null);

        keyEvent = new KeyEvent(KeyEvent.ACTION_UP, keyCode);
        intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
        context.sendOrderedBroadcast(intent, null, null, null, Activity.RESULT_OK, null, null);
    }
}