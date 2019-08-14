package com.example.zee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    EditText username;
    Button login;
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            socket = SocketClass.getInstance().socket;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        username = findViewById(R.id.username);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                if (!name.isEmpty()){
                    socket.emit("username",name);
                }else{
                    Toast.makeText(getApplicationContext(),"Enter user name",Toast.LENGTH_SHORT).show();
                }
            }
        });

        socket.on("authenticated", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String check = (String) args[0];
                Log.d("json", String.valueOf(check));
                if (!check.isEmpty()){
                    Intent i = new Intent(getApplicationContext(),ChatActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}