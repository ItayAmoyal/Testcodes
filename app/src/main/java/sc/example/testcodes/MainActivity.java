package sc.example.testcodes;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private  View view;
    private static final String TAG ="MainActivity";
    private Button Startmusic, StopMusic;
    private stopWatchAsyncTask stopWatchAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Startmusic=findViewById(R.id.button);
        StopMusic=findViewById(R.id.button1);
        Startmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start(view);
            }
        });
        StopMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop(view);
            }
        });
    }
    public void Start(View view){
        if(stopWatchAsyncTask==null||
            stopWatchAsyncTask.getStatus()==AsyncTask.Status.FINISHED){
    stopWatchAsyncTask =new stopWatchAsyncTask(this);
    stopWatchAsyncTask.execute();
            }
    }
    public void stop(View view){
        if(stopWatchAsyncTask!=null){
            stopWatchAsyncTask.cancel(true);
            stopMusic();
        }
    }
    @Override
  public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("AsyncTask");
        menu.add("Threads");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        String st=item.getTitle().toString();
        if(st.equals("Threads")){
            Intent intent=new Intent(this,Threads.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    public class  stopWatchAsyncTask extends AsyncTask<Void, Integer, Void> {
        private static final String TAG="stopWatchAsyncTask";
        private AtomicBoolean isRunning=new AtomicBoolean(true);
        private AtomicBoolean isPaused=new AtomicBoolean(false);
        private final WeakReference<MainActivity> activityRef;
        public stopWatchAsyncTask(MainActivity context){
            activityRef =new WeakReference<>(context);
            Log.i(TAG,"AsyncTask created");
        }
        protected void onPreExecute() {
            isRunning.set(true);
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sound);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Log.i(TAG,"AsyncTask started");
            while(isRunning.get()&&!isCancelled()) {
                try {
                    Thread.sleep(1000);
                    mediaPlayer.start();
                   publishProgress();
                } catch (InterruptedException e) {
                    isRunning.set(false);
                }
            }
            return null;
        }
        @Override
        protected void onCancelled(Void aVoid) {
            Log.i(TAG,"Music stopped");
            isRunning.set(false);
        }
    }
}