package ese343.mikekenndy.github.lab02_sensorandcamera;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mLightSensor;

    private SensorListener accelListener = new AccelListener();
    private SensorListener lightListener = new LightListener();

    private Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

//        mSensorManager.registerListener((SensorEventListener) accelListener, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
//        mSensorManager.registerListener((SensorEventListener) lightListener, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(accelListener);
        mSensorManager.unregisterListener(lightListener);
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            AccelWork work = new AccelWork(event);
            myHandler.post(work);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class AccelListener implements SensorListener {

        @Override
        public void onSensorChanged(int sensor, float[] values) {

        }

        @Override
        public void onAccuracyChanged(int sensor, int accuracy) {

        }
    }

    private class LightListener implements SensorListener {

        @Override
        public void onSensorChanged(int sensor, float[] values) {

        }

        @Override
        public void onAccuracyChanged(int sensor, int accuracy) {

        }
    }

    private class AccelWork implements Runnable {

        private SensorEvent data;
        public AccelWork(SensorEvent d) {
            data = d;
        }

        @Override
        public void run() {
            float x = data.values[0];
            float y = data.values[1];
            float z = data.values[2];
            TextView tx1 = (TextView) findViewById(R.id.textview1);
            TextView tx2 = (TextView) findViewById(R.id.textview2);
            TextView tx3 = (TextView) findViewById(R.id.textview3);
            tx1.setText(String.valueOf(x));
            tx2.setText(String.valueOf(y));
            tx3.setText(String.valueOf(z));
        }
    }
}
