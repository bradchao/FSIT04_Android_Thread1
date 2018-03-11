package tw.org.iii.mythread1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView mesg;
    private UIHandler handler;
    private int i;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();

        handler = new UIHandler();

        mesg = findViewById(R.id.mesg);
    }

    public void test1(View view) {
        new Thread(){
            @Override
            public void run() {
                for (int i=0; i<20; i++){
                    Log.v("brad", "test1: i = " + i);
                    //mesg.setText("i = " + i);
                    handler.sendEmptyMessage(i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    public void test2(View view) {
        Log.v("brad", "before");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<10; i++){
                    mesg.setText("i = " + i);
                    Log.v("brad", " i = " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Log.v("brad", "after");

    }
    public void test3(View view) {
        Log.v("brad", "click");
    }

    public void test4(View view) {
        new Thread(){
            @Override
            public void run() {
                for (i=0; i<10; i++){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mesg.setText("OK" + i );
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }

    public void test5(View view) {
        timer.schedule(new Task1(), 0, 1000);
    }

    private class Task1 extends TimerTask {
        @Override
        public void run() {
            Log.v("brad", "i = " + i++);
        }
    }

    @Override
    public void finish() {
        if (timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
        }

        super.finish();
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Log.v("brad", "handler:ok");

            mesg.setText("i = " + msg.what);
        }
    }

}
