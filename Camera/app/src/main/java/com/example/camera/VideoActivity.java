package com.example.camera;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoActivity extends AppCompatActivity {


    static final int REQUEST_VIDEO_CAPTURE = 101;
    String currentVideoPath;

    private File createVideoFile() throws IOException {
        // Create an video file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VIDEO_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File video = File.createTempFile(
                imageFileName,  /* prefix */
                ".mp4",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentVideoPath = video.getAbsolutePath();
        return video;
    }

    private void takeVideoIntent(){
        // Creating a video intent
        Intent takeVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(takeVideo.resolveActivity(getPackageManager())!=null){
            File videoFile = null;
            try{
                videoFile = createVideoFile();
            }catch(IOException ex){
                ex.getMessage();
            }
            if(videoFile!=null){
                Uri provideVideo = FileProvider.getUriForFile(getApplicationContext(),"com.example.android.fileProvider",videoFile);
                takeVideo.putExtra(MediaStore.EXTRA_OUTPUT, provideVideo);
                startActivityForResult(takeVideo, REQUEST_VIDEO_CAPTURE);
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Button videoButton = this.findViewById(R.id.record);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeVideoIntent();
            }
        });
    }

    protected void onActivityResult(int requestcode, int resultcode, Intent intent){
        if (requestcode == REQUEST_VIDEO_CAPTURE && resultcode == RESULT_OK) {
            Uri videoUri = intent.getData();
            final VideoView videoView = this.findViewById(R.id.videoView);

            videoView.setVideoURI(videoUri);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoView.start();
                }
            });
        }
    }
}
