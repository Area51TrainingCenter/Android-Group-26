package pe.area51.audioproject;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder mediaRecorder;

    private boolean isRecording;

    private TextView statusTextView;
    private Button recordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.statusTextView = (TextView) findViewById(R.id.activity_main_textview_status);
        this.recordButton = (Button) findViewById(R.id.activity_main_button_play);
        this.recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    stopRecording();
                    updateStatusView();
                } else {
                    try {
                        initMediaRecorder();
                        startRecording();
                        updateStatusView();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void updateStatusView() {
        this.statusTextView.setText(
                isRecording ? R.string.recording : R.string.stopped);
        this.recordButton.setText(
                isRecording ? R.string.stop : R.string.record);
    }

    private void initMediaRecorder() {
        this.mediaRecorder = new MediaRecorder();
        this.mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        this.mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        this.mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        final String filename = "voice_recording_" + System.currentTimeMillis() + ".3gp";
        this.mediaRecorder.setOutputFile(path + "/" + filename);
    }

    private void startRecording() throws IOException {
        this.mediaRecorder.prepare();
        this.mediaRecorder.start();
        this.isRecording = true;
    }

    private void stopRecording() {
        this.mediaRecorder.stop();
        this.mediaRecorder.release();
        this.mediaRecorder = null;
        this.isRecording = false;
    }

}
