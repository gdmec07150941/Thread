package com.example.administrator.thread;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private int seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv1);
        Date theLastDay = new Date(117, 5, 23);
        Date toDay = new Date();
        seconds = (int) (theLastDay.getTime() - toDay.getTime()) / 1000;
    }
    public void anr(View view){
        for (int i = 0; i < 10000; i++) {
            BitmapFactory.decodeResource(getResources(), R.drawable.android_pic);
        }
    }
    public void threadclass(View view){
        class ThreadSample extends Thread{
            Random rm;
            public ThreadSample(String tname){
                super(tname);
                rm = new Random();
            }
            public void run(){
                for (int i = 0; i < 10; i++) {
                    System.out.println(i+" "+getName());
                    try {
                        sleep(rm.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(getName()+"完成");
            }
        }
        ThreadSample thread1 = new ThreadSample("线程1");
        thread1.start();
        ThreadSample thread2 = new ThreadSample("线程2");
        thread2.start();
    }
    public void runnableinterface(View v){
        class RunnableExample implements Runnable{
            Random rm;
            String name;
            public RunnableExample(String tname){
                this.name = tname;
                rm = new Random();
            }
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(i+" "+name);
                    try {
                        Thread.sleep(rm.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(name+"完成");
            }
        }
        Thread thread1 = new Thread(new RunnableExample("线程1"));
        thread1.start();
        Thread thread2 = new Thread(new RunnableExample("线程2"));
        thread2.start();
    }
    public void timertask(View v){
        class MyThread extends TimerTask{
            Random rm;
            String name;
            public MyThread(String name){
                this.name = name;
                rm = new Random();
            }
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(i+" "+name);
                    try {
                        Thread.sleep(rm.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(name+"完成");
            }
        }
        Timer timer1 = new Timer();
        Timer timer2 = new Timer();
        MyThread thread1 = new MyThread("线程1");
        MyThread thread2 = new MyThread("线程2");
        timer1.schedule(thread1, 0);
        timer2.schedule(thread2, 0);
    }
    public void handlermessage(View v){
        Handler myHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        showmsg(String.valueOf(msg.arg1+msg.getData().get("arrach").toString()));
                }
            }
        };
        class MyTask extends TimerTask{

            @Override
            public void run() {

            }
        }
    }
    public void showmsg(String msg){
        textView.setText(msg);
    }
    public void asynctask(View v){
        class LearHard extends AsyncTask<Long, String, String>{
            private Context context;
            final int duration = 10;
            int count = 0;
            public LearHard(Activity context){
                this.context = context;
            }
            @Override
            protected String doInBackground(Long... params) {
                long num = params[0].longValue();
                while (count < duration){
                    num--;
                    count++;
                    String status = "离毕业还有"+num+"秒,努力学习"+count+"秒";
                    publishProgress(status);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return "这"+duration+"秒没有点虚度,收获太少";
            }

            @Override
            protected void onProgressUpdate(String... values) {
                ((MainActivity)context).textView.setText(values[0]);
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(String s) {
                showmsg(s);
                super.onPostExecute(s);
            }
        }
        LearHard learHard = new LearHard(this);
        learHard.execute((long)seconds);
    }
}
