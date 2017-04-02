package lahacks2017.hololink;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    PrintWriter out;
    private SensorManager sensorManager;
    TextView tvGyro;

    public MainActivity() throws IOException {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvGyro = (TextView) findViewById(R.id.textView);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);

        // using a thread because we're not allowed to run a network on the main thread of execution
        Thread thread = new Thread( new Runnable() {
            @Override
            public void run(){
                try{
                    ServerSocket serverSocket = new ServerSocket(4444);
                    Socket clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    tvGyro.setText(out.toString());

                    BufferedReader in = new BufferedReader( // this thing would receive data on where to register hazard markers i think
                            new InputStreamReader(clientSocket.getInputStream()));

                } catch (Exception e) { e.printStackTrace(); }
            }
        });
        thread.start();
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_GYROSCOPE && event.timestamp != 0){
            // we only care about rotation about the z-axis (forward)
            float axisZ = event.values[2];
            if (out == null){
                tvGyro.setText("out is null!");
            }
            else {
                tvGyro.setText("sending " + String.valueOf(axisZ));
                out.print(axisZ); // but now this thing is out of scope because of the thread
            }
        }
    }
}