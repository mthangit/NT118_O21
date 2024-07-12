package com.example.lab5_5;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button btnPlay;
    private Button btnPause;
    private Button btnRestart;
    private TextView tvStatus;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private String url = "https://vietup.net/files/0c20994b2c7a3e1c878ae03fb9f42d9d/4e394c628cae6ffde0c45b27ee274035/Legends%20Never%20Die%20(ft.%20Against%20The%20Current)%20-%20Worlds%202017%20-%20League%20of%20Legends.mp3";
    private final Handler handler = new Handler();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnRestart = findViewById(R.id.btnRestart);
        tvStatus = findViewById(R.id.tvStatus);
        seekBar = findViewById(R.id.seekBar);

        btnPlay.setOnClickListener(v -> playMusic());
        btnPause.setOnClickListener(v -> pauseMusic());
        btnRestart.setOnClickListener(v -> restartMusic());
    }

    private void resetSeekBarAndStatus() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(0);
            seekBar.setProgress(0);
            tvStatus.setText("Status: Finished");
            isPlaying = false;
            Toast.makeText(this, "Finished", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void playMusic() {
        if (isPlaying) {
            Toast.makeText(this, "Music is already playing", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
            tvStatus.setText("Status: Playing");
            Toast.makeText(this, "Continue", Toast.LENGTH_SHORT).show();
            updateSeekBar();
        } else {
            tvStatus.setText("Status: Downloading music...");
            startNewMediaPlayer();
        }
    }

    private void startNewMediaPlayer() {
        compositeDisposable.add(Observable.fromCallable(() -> {
                    mediaPlayer = MediaPlayer.create(this, Uri.parse(url));
                    mediaPlayer.setOnCompletionListener(mp -> resetSeekBarAndStatus());
                    mediaPlayer.start();
                    isPlaying = true;
                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result) {
                        tvStatus.setText("Status: Playing music");
                        Toast.makeText(this, "Playing music", Toast.LENGTH_SHORT).show();
                        updateSeekBar();
                    } else {
                        tvStatus.setText("Status: Failed to load");
                    }
                }));
    }

    private void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            tvStatus.setText("Status: Paused");
            Toast.makeText(this, "Stop playing music", Toast.LENGTH_SHORT).show();
        }
    }

    private void restartMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
            isPlaying = true;
            tvStatus.setText("Status: Playing");
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
        compositeDisposable.clear();
    }
}