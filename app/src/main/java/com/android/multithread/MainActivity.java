package com.android.multithread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 单线程
     */
    private Button mBtn1;
    /**
     * 多线程
     */
    private Button mBtn2;
    /**
     * 结束线程
     */
    private Button mBtn3;
    private EditText mEdit;
    private volatile boolean m_bStop = false;
    private EditText mEdit3;
    /**
     * 第四行按钮
     */
    private Button mBnt4;
    private EditText mEdit4;
    /**
     * 第五行按钮
     */
    private Button mBtn5;
    private EditText mEdit5;
    private ProgressBar mProgress;
    /**
     * 下载
     */
    private Button mBtnLoad;
    private TextView mTvLoad;
    private ProgressBar mPbLoad;
    private MyLoadAsyncTask myLoadAsyncTask = new MyLoadAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String string = msg.getData().getString("string");
            switch (msg.what) {
                case 1:
                    Toast.makeText(MainActivity.this, "接收到来自按钮五的消息" + string, Toast.LENGTH_SHORT).show();
                    mProgress.setProgress(msg.arg1);

                    break;
                case 5:
                    Toast.makeText(MainActivity.this, "接收到来自按钮三的消息" + string, Toast.LENGTH_SHORT).show();

                    mProgress.setProgress(msg.arg1);

                    break;
                case 10:
                    mProgress.setProgress(msg.arg1);
                    Toast.makeText(MainActivity.this, "接收到来自按钮四的消息" + string, Toast.LENGTH_SHORT).show();

                    break;
            }

        }
    };

    private void initView() {
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn3.setOnClickListener(this);
        mEdit = (EditText) findViewById(R.id.edit);
        mEdit.setOnClickListener(this);
        mBtn3 = (Button) findViewById(R.id.btn_3);
        mEdit3 = (EditText) findViewById(R.id.edit_3);
        mBnt4 = (Button) findViewById(R.id.bnt_4);
        mBnt4.setOnClickListener(this);
        mEdit4 = (EditText) findViewById(R.id.edit_4);
        mBtn5 = (Button) findViewById(R.id.btn_5);
        mBtn5.setOnClickListener(this);
        mEdit5 = (EditText) findViewById(R.id.edit_5);
        mEdit3.setOnClickListener(this);
        mEdit4.setOnClickListener(this);
        mEdit5.setOnClickListener(this);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mProgress.setMax(100);
        mProgress.setOnClickListener(this);
        mBtnLoad = (Button) findViewById(R.id.btn_load);
        mBtnLoad.setOnClickListener(this);
        mTvLoad = (TextView) findViewById(R.id.tv_load);
        mPbLoad = (ProgressBar) findViewById(R.id.pb_load);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn1:
                int sum = 0;
                for (int i = 1; i <= 50000; i++) {
                    sum += i;
                    System.out.println("1到50000累加的和为：" + sum);

                }
                break;
            case R.id.btn2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!m_bStop) {
                            int sum = 0;
                            for (int i = 1; i <= 30000; i++) {
                                sum += i;
                                Log.e("THREAD", "1到30000累加的和为：" + sum);

                            }
                        }
                        Log.e("THREAD", "Quit Thread");

                    }
                }).start();
                break;
            case R.id.btn3:
                m_bStop = true;

                break;
            case R.id.edit:
                break;
            case R.id.btn_3:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int progress = mProgress.getProgress();
                        while (progress < 100) {
                            try {
                                Thread.sleep(3000);
                                Message message = Message.obtain();
                                message.what = 5;
                                message.arg1 = progress + 5;
                                Bundle bundle = new Bundle();
                                bundle.putString("string", mEdit3.getText().toString());
                                message.setData(bundle);
                                handler.sendMessage(message);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                        }

                    }
                }).start();
                break;
            case R.id.bnt_4:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int progress = mProgress.getProgress();
                        while (progress < 100) {
                            try {
                                Thread.sleep(3000);
                                Message message = Message.obtain();
                                message.what = 10;
                                message.arg1 = progress + 10;
                                Bundle bundle = new Bundle();
                                bundle.putString("string", mEdit4.getText().toString());
                                message.setData(bundle);
                                handler.sendMessage(message);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                        }

                    }
                }).start();
                break;
            case R.id.btn_5:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int progress = mProgress.getProgress();
                        while (progress < 100) {
                            try {
                                Thread.sleep(3000);
                                Message message = Message.obtain();
                                message.what = 1;
                                message.arg1 = progress + 1;
                                Bundle bundle = new Bundle();
                                bundle.putString("string", mEdit5.getText().toString());
                                message.setData(bundle);
                                handler.sendMessage(message);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                        }

                    }
                }).start();
                break;

            case R.id.btn_load:
                new MyLoadAsyncTask().execute("https://www.baidu.com");//有效的URL地址
                break;
        }
    }

    /* 异步任务，后台处理与更新UI */
    class MyLoadAsyncTask extends AsyncTask<String, String, String> {
        /* 后台线程 */
        @Override
        protected String doInBackground(String... params) {
            /* 所下载文件的URL */
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                /* URL属性设置 */
                conn.setRequestProperty("Accept-Encoding", "identity");
                /* URL建立连接 */
                conn.connect();
                /* 下载文件的大小 */
                int total = conn.getContentLength();
                /* 每次下载的大小与总下载的大小 */
                int count = 0;
                int length = -1;
                /* 输入流 */
                InputStream in = conn.getInputStream();
                /* 输出流 */
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                /* 缓存模式，下载文件 */
                byte[] buff = new byte[1024];
                while ((length = in.read(buff)) != -1) {
                    out.write(buff, 0, length);
                    count += length;
                    publishProgress((int) ((count / (float) total) * 100) + "");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                /* 关闭输入输出流 */
                in.close();
                out.flush();
                out.close();


            } catch (MalformedURLException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }


        /* 预处理UI线程 */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        /* 结束时的UI线程 */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mTvLoad.setText("下载完成");
        }

        /* 处理UI线程，会被多次调用,触发事件为publicProgress方法 */
        @Override
        protected void onProgressUpdate(String... values) {
            /* 进度显示 */
            mPbLoad.setProgress(Integer.parseInt(values[0]));
            mTvLoad.setText("Load" + values[0]);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeMessages(1);
            handler.removeMessages(5);
            handler.removeMessages(10);
            handler = null;
        }
    }
}