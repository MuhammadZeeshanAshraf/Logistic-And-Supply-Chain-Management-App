package com.example.logisticandsupplychainmanagementsystem.OnBoarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.logisticandsupplychainmanagementsystem.Activities.MainActivity
import com.example.logisticandsupplychainmanagementsystem.Adapters.OnboardingViewPagerAdapter
import com.example.logisticandsupplychainmanagementsystem.Common.LoginAsActivity
import com.example.logisticandsupplychainmanagementsystem.R
import com.example.logisticandsupplychainmanagementsystem.Utils.Animatoo

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var mViewPager: ViewPager
    private lateinit var textSkip: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        mViewPager = findViewById(R.id.viewPager)
        mViewPager.adapter = OnboardingViewPagerAdapter(supportFragmentManager, this)

        textSkip = findViewById(R.id.text_skip)
        textSkip.setOnClickListener {
            finish()
            val intent =
                    Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            Animatoo.animateSlideLeft(this)
        }

        val btnNextStep: Button = findViewById(R.id.btn_next_step)

        btnNextStep.setOnClickListener {
            if (getItem(+1) > mViewPager.childCount-1) {
                finish()
                val intent =
                        Intent(applicationContext, LoginAsActivity::class.java)
                startActivity(intent)
                Animatoo.animateSlideLeft(this)
            } else {
                mViewPager.setCurrentItem(getItem(+1), true)
            }
        }

    }
    private fun getItem(i: Int): Int {
        return mViewPager.currentItem + i
    }

}
