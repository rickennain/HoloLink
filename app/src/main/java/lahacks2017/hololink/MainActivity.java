package lahacks2017.hololink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity{
    public MainActivity() throws IOException {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = new Intent(this, SensorActivity.class);

        // using a thread because we're not allowed to run a network on the main thread of execution
        Thread thread = new Thread( new Runnable() {
            @Override
            public void run(){
                try{
                    ServerSocket serverSocket = new ServerSocket(4444);
                    Socket clientSocket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    BufferedReader in = new BufferedReader( // this thing would receive data on where to register hazard markers i think
                            new InputStreamReader(clientSocket.getInputStream()));
                    startActivity(intent);

                } catch (Exception e) { e.printStackTrace(); }
            }
        });

        thread.start(); //also wtf why is this wrong
    }
}