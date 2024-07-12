package com.example.lab5_4;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;


import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnPlay;
    private Button btnPause;
    private Button btnRestart;
    private TextView tvStatus;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private Handler handler = new Handler();
    private String url = "https://vietup.net/files/0c20994b2c7a3e1c878ae03fb9f42d9d/4e394c628cae6ffde0c45b27ee274035/Legends%20Never%20Die%20(ft.%20Against%20The%20Current)%20-%20Worlds%202017%20-%20League%20of%20Legends.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnRestart = findViewById(R.id.btnRestart);
        tvStatus = findViewById(R.id.tvStatus);
        seekBar = findViewById(R.id.seekBar);


        btnPlay.setOnClickListener(v -> playMusic(url));

        btnPause.setOnClickListener(v -> pauseMusic());

        btnRestart.setOnClickListener(v -> restartMusic());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null && isPlaying) {
                    mediaPlayer.start();
                }
            }
        });
    }

    @SuppressLint({"StaticFieldLeak", "SetTextI18n"})
    private void playMusic(String url) {
        if (isPlaying) {
            Toast.makeText(this, "Music is already playing", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
            tvStatus.setText("Status: Playing music");
            Toast.makeText(this, "Continue", Toast.LENGTH_SHORT).show();
            updateSeekBar();
        } else {
            new AsyncTask<String, Void, Boolean>() {
                @SuppressLint("SetTextI18n")
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    tvStatus.setText("Status: Loading...");
                }

                @SuppressLint("SetTextI18n")
                @Override
                protected Boolean doInBackground(String... strings) {
                    String url = strings[0];
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepare();
                        mediaPlayer.setOnCompletionListener(mp -> {
                            isPlaying = true;
                            mediaPlayer.seekTo(0);
                            mediaPlayer.start();
                            tvStatus.setText("Status: Playing music");
                            updateSeekBar();
                            Toast.makeText(MainActivity.this, "Playing again", Toast.LENGTH_SHORT).show();
                        });
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    super.onPostExecute(result);
                    if (result) {
                        if (mediaPlayer != null) {
                            mediaPlayer.start();
                            isPlaying = true;
                            tvStatus.setText("Status: Playing music");
                            Toast.makeText(MainActivity.this, "Playing music", Toast.LENGTH_SHORT).show();
                            updateSeekBar();
                        }
                    } else {
                        tvStatus.setText("Status: Failed to load");
                    }
                }
            }.execute(url);
        }
    }

    @SuppressLint("SetTextI18n")
    private void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            tvStatus.setText("Status: Paused");
            Toast.makeText(this, "Stop playing music", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void restartMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
            isPlaying = true;
            tvStatus.setText("Status: Playing music");
            updateSeekBar();
            Toast.makeText(this, "Restart music", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer != null && isPlaying) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            seekBar.setMax(duration);
            seekBar.setProgress(currentPosition);
            handler.postDelayed(this::updateSeekBar, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
