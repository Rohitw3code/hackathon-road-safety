package com.example.road_safety

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

class AccidentAlert : AppCompatActivity() {
    private lateinit var noBtn: Button
    private lateinit var yesBtn: Button
    private lateinit var counter:TextView
    private lateinit var alertLinear:LinearLayout
    private lateinit var lottiAmbulance:LottieAnimationView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_yes_or_no)

        noBtn = findViewById(R.id.nobtn) as Button
        yesBtn = findViewById(R.id.yesbtn) as Button
        counter = findViewById(R.id.counter_timer)
        alertLinear = findViewById(R.id.alert_linear)
        lottiAmbulance = findViewById(R.id.lotti_ambulance)

        noBtn.setOnClickListener {
            finish()
            System.out.close()
        }
        yesBtn.setOnClickListener{
            finish()
            System.out.close()
        }

        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter.textSize = 45F
                counter.text = "${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                counter.text = "Message is sent to nearest ambulance for your help"
                counter.textSize = 30F
                lottiAmbulance.visibility = View.VISIBLE
                alertLinear.visibility = View.GONE
            }
        }.start()

    }

}