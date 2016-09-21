package pe.area51.timecounter;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static long REPETITIVE_TASK_DELAY_IN_MILLIS = 17;

    private TextView textViewStatus;

    private RepetitiveTask timeCounterRepetitiveTask;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewStatus = (TextView) findViewById(R.id.textview_status);
        timeCounterRepetitiveTask = createTimeCounterRepetitiveTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch_timer:
                toggleTimeCounter();
                return true;
            default:
                return false;
        }
    }

    private void toggleTimeCounter() {
        if (timeCounterRepetitiveTask.isRunning()) {
            timeCounterRepetitiveTask.stop();
        } else {
            startTime = SystemClock.elapsedRealtime();
            timeCounterRepetitiveTask.start(true);
        }
    }

    private RepetitiveTask createTimeCounterRepetitiveTask() {
        return new RepetitiveTask(new Runnable() {
            @Override
            public void run() {
                final long elapsedTime = (SystemClock.uptimeMillis() - startTime) / 1000;
                updateTextViewStatus(String.valueOf(elapsedTime));
            }
        }, REPETITIVE_TASK_DELAY_IN_MILLIS);
    }

    private void updateTextViewStatus(final String text) {
        textViewStatus.setText(text);
    }
}