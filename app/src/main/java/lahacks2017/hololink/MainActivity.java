package lahacks2017.hololink;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    double ax,ay,az;
    TextView tvSpeed;

    long t1=0;
    long t2=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvSpeed = (TextView) findViewById(R.id.textView);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];


            long now = System.currentTimeMillis();
            interval = (now - lastEvent);
            if(interval > 100){
                lastEvent = now;
                double acceleration = x+y+z-lastX-lastY-lastZ;
                double velocity = v0 + (acceleration*(interval/(double)1000));
                velocities.add(Math.abs(velocity));
                v0= velocity;
                lastX = x;
                lastY = y;
                lastZ = z;


            //long t2 = (new Date()).getTime()
             //       + (event.timestamp - System.nanoTime()) / 1000000L;

//            long delta_t = t2 - t1;

            tvSpeed.setText(String.valueOf(ax));


            // save value to use next time
  //          t1 = t2;
        }
    }
}
