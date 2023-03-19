package com.example.road_safety

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector(context: Context) : SensorEventListener {

    private var mSensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var mAccelerometer: Sensor? = null
    private var mShakeListener: OnShakeListener? = null
    private var mLastX: Float = 0.toFloat()
    private var mLastY: Float = 0.toFloat()
    private var mLastZ: Float = 0.toFloat()
    private var mLastTime: Long = 0
    private var mShakeThreshold: Float = 2.5f

    interface OnShakeListener {
        fun onShake(count: Int)
    }

    fun setOnShakeListener(listener: OnShakeListener) {
        mShakeListener = listener
    }

    fun start() {
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stop() {
        mSensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val currentTime = System.currentTimeMillis()
            val timeDiff = currentTime - mLastTime

            if (timeDiff > 100) {
                mLastTime = currentTime

                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val acceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                val delta = acceleration - mShakeThreshold

                if (delta > 0) {
                    val speed = delta / timeDiff * 10000
                    if (speed > 800) {
                        mShakeListener?.onShake(1)
                    }
                }

                mLastX = x
                mLastY = y
                mLastZ = z
            }
        }
    }
}
