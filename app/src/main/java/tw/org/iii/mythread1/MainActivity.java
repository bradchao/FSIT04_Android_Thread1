package tw.org.iii.mythread1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mesg;
    private UIHandler handler;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new UIHandler();

        mesg = findViewById(R.id.mesg);
    }

    public void test1(View view) {
        new Thread(){
            @Override
            public void run() {
                for (int i=0; i<20; i++){
                    Log.v("brad", "i = " + i);
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

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Log.v("brad", "handler:ok");

            mesg.setText("i = " + msg.what);
        }
    }

}
