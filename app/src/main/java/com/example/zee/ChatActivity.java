package com.example.zee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText content;
    FloatingActionButton send;
    Socket socket;
    ChatAdapter chatAdapter;
//    ArrayList<Data> contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        try {
            socket = SocketClass.getInstance().socket;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        chatAdapter = new ChatAdapter();
        send = findViewById(R.id.sendbtn);
        content = findViewById(R.id.messagecontent);
//        contentList = new ArrayList<>();
        recyclerView = findViewById(R.id.chat_recyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(chatAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = content.getText().toString();
                String name = getIntent().getStringExtra("name");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name",name);
                    jsonObject.put("message",message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("messageDetection",name,message);
            }
        });

        socket.on("receivemessage", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jsonObject = (JSONObject) args[0];

                Log.d("jsonbody", String.valueOf(jsonObject));

                try {
                    String n = jsonObject.getString("u");
                    String m = jsonObject.getString("m");
                    chatAdapter.setList(n,m);
                    Log.d("adapter","adapter");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public class Data{
        String name,message;

        Data(String name,String message){
            this.name=name;
            this.message = message;

        }

        public String getName() {
            return name;
        }

        public String getMessage() {
            return message;
        }
    }
}
