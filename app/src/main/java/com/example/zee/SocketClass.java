package com.example.zee;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.net.URISyntaxException;

class SocketClass {

    Socket socket;
//    String url = "http://100.127.126.167:3000";
    String url = "http://192.168.43.155:3000";
    private SocketClass() throws URISyntaxException {

        socket = IO.socket(url);
        socket.connect();
        Log.d("App connected","App connected");

    }

    static SocketClass getInstance() throws URISyntaxException {
        return new SocketClass();
    }


}
