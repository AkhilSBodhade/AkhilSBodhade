package com.mutefrog.myfolio.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.ActivityTutorialBinding
import com.mutefrog.myfolio.view.adapter.TutorialViewPagerAdapter

class TutorialActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager2
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button

    private lateinit var binding: ActivityTutorialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mViewPager = binding.viewPager
        mViewPager.adapter = TutorialViewPagerAdapter(this, this)
        mViewPager.offscreenPageLimit = 1
        btnBack = binding.btnPreviousStep
        btnNext = binding.btnNextStep

        binding.btnSkip.setOnClickListener {
            navigateToOnboardingScreen()
        }

        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                // Handle Next/Finish Button
                if (position == 3) {
                    btnNext.text = getText(R.string.finish)
                } else {
                    btnNext.text = getText(R.string.next)
                }

                // Handle Skip Button Color
                when (position) {
                    0, 2 -> binding.btnSkip.setTextColor(ContextCompat.getColor(this@TutorialActivity, R.color.white))

                    1, 3 -> binding.btnSkip.setTextColor(ContextCompat.getColor(this@TutorialActivity, R.color.tutorial_dash_gray))
                }

            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        })

        TabLayoutMediator(binding.pageIndicator, mViewPager) { _, _ -> }.attach()

        btnNext.setOnClickListener {
            if (getItem() > mViewPager.childCount+1) {
                binding.viewTutorial.visibility = View.GONE
                binding.viewTutorialDone.visibility = View.VISIBLE
                binding.btnTutorialDone.setOnClickListener {
                    navigateToOnboardingScreen()
                }
            } else {
                mViewPager.setCurrentItem(getItem() + 1, true)
            }
        }

        btnBack.setOnClickListener {
            if (getItem() == 0) {
                finish()
            } else {
                mViewPager.setCurrentItem(getItem() - 1, true)
            }
        }
    }

    private fun getItem(): Int {
        return mViewPager.currentItem
    }

    private fun navigateToOnboardingScreen() {
        val intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
        finish()
    }


}
