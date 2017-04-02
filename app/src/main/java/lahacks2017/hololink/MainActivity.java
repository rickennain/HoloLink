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
    TextView tvSpeed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSpeed = (TextView) findViewById(R.id.textView);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_GYROSCOPE && event.timestamp != 0){
            // we only care about rotation about the z-axis (forward)
            float axisZ = event.values[2];
            tvSpeed.setText(String.valueOf(axisZ));
            }
        }
    }