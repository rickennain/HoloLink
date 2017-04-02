package lahacks2017.hololink;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

/**
 * Created by patrick on 4/1/17.
 */

public class SensorActivity extends Activity implements SensorEventListener {
    private final SensorManager mSensorManager;
    private final Sensor mGyroscope;
    TextView tvGyro, tvSpeed;

    public SensorActivity(TextView textView ) {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        tvSpeed = (TextView) findViewById(R.id.SpeedTV);
        tvGyro = (TextView) findViewById(R.id.GyroTV);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_GYROSCOPE && event.timestamp != 0){
            // we only care about rotation about the z-axis (forward)
            float axisZ = event.values[2];
            tvGyro.setText( String.valueOf( axisZ ));
            // send axisZ to client HoloLens
        }
    }
}
