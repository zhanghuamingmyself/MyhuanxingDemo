package com.zhanghuaming.myhuanxingdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn ;
    private EditText editText;
    private TextView tv;
    private EMMessageListener msgListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.tv);
        tv.setText("開始聊天吧");
        EMClient.getInstance().login("z", "123456",new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！"+message);
            }
        });

        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText() !=null) {
                    EMMessage message = EMMessage.createTxtSendMessage(editText.getText().toString(), "h");
//发送消息
                    EMClient.getInstance().chatManager().sendMessage(message);

                    tv.setText(tv.getText() + "\n" + "自己 :     " + editText.getText().toString());
                    editText.setText("");
                }
            }
        });
        editText = (EditText)findViewById(R.id.et);

        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(final List<EMMessage> messages) {
                //收到消息
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this,"you get a message --------"+messages.toString(),Toast.LENGTH_LONG).show();
                        tv.setText(tv.getText()+"\n"+"盆友 :     "+messages.toString());
                    }
                });
                Log.i(MainActivity.class.getSimpleName(),"you get a message --------"+messages.toString());
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
}
