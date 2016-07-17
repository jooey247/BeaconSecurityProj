package cse.knu.kr.deagu.beaconsecurityapp.UserInfo;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Intent;
import android.util.Log;

/**
 * Created by juhee on 2016-07-15.
 */
public class SocketManager implements Runnable {
    private static final String serverIP = "";
    private static final int serverPort = 5555;
    private String msg;

    //0 : fail ,1 : success
    public int SUflag = 0;
    public int JIflag = 0;

    private user mUser;

    public SocketManager(String _msg, user _user) {
        this.msg = _msg;
        mUser = _user;
    }

    public void run() {

        InetAddress server_adr = null;
        try {
            server_adr = InetAddress.getByName(serverIP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Log.d("DB", "Socket : Connecting...");
        Socket socket = null;
        try {
            socket = new Socket(server_adr, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Log.d("DB", "Socket : Sending : '" + msg + "'");

            DataInputStream sock_in = new DataInputStream(socket.getInputStream());
            DataOutputStream sock_out = new DataOutputStream((socket.getOutputStream()));

            byte[] buf = new byte[1024];

            Log.d("DB", "Socket : Sent.");

            if (msg == "SU") {
                //Sign up
                Log.i("Request", "msg Sign-up");
                buf = msg.getBytes();
                sock_out.write(buf);
                sock_out.flush();

                buf = new byte[1024];
                buf = mUser.userId.getBytes();
                sock_out.write(buf);
                sock_out.flush();

                buf = new byte[1024];
                buf = mUser.userPw.getBytes();
                sock_out.write(buf);
                sock_out.flush();

                SUflag = sock_in.read();
                Log.i("Request", "" + SUflag);

                if (SUflag == 0) {
                    //fail
                    //socket error
                } else if (SUflag == 2) {
                    //fail
                    //same ID user
                } else if (SUflag == 1) {
                    //success
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}