package sc.example.testcodes;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

public class Threads extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private View view;
    private static final String TAG = "Threads";
    private Button Startmusic, StopMusic;
    private Thread stopmusicThread;
    private Runnable stopWatchRunnable;
    private boolean isPaused;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        registerForContextMenu(findViewById(R.id.main));
        MyRunnable runnableTask = new MyRunnable();
        Startmusic = findViewById(R.id.button);
        StopMusic = findViewById(R.id.button1);
        Thread thread = new Thread(runnableTask);
        thread.start();
        Startmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTh(view);
            }
        });
        StopMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTh(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("AsyncTask");
        menu.add("Threads");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String st = item.getTitle().toString();
        if (st.equals("AsyncTask")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyRunnable implements Runnable {
        @Override
        public void run() {
            mediaPlayer = MediaPlayer.create(Threads.this, R.raw.sound);
            mediaPlayer.start();
        }
    }
        private Runnable createStopWatchRunnable() {
            return () -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };
        }
        public void stopMusic() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    public void startTh(View view) {
        if (stopmusicThread == null || !stopmusicThread.isAlive()) {
            stopWatchRunnable = createStopWatchRunnable();
            isPaused = false;
            stopmusicThread = new Thread(stopWatchRunnable);
            stopmusicThread.start();
            mediaPlayer = MediaPlayer.create(Threads.this, R.raw.sound);
            mediaPlayer.start();
        }
    }
    public void stopTh(View view) {
        if (stopmusicThread != null) {
            stopmusicThread.interrupt();
            stopMusic();
        }
    }
}

