package com.mutefrog.myfolio.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.view.fragment.TutorialFragment


class TutorialViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val context: Context
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TutorialFragment.newInstance(
                context.resources.getString(R.string.title_tutorial_1),
                context.resources.getString(R.string.description_tutorial_1),
                R.drawable.img_intro_1,
                1
            )
            1 -> TutorialFragment.newInstance(
                context.resources.getString(R.string.title_tutorial_2),
                context.resources.getString(R.string.description_tutorial_2),
                R.drawable.img_intro_2,
                0
            )
            2 -> TutorialFragment.newInstance(
                context.resources.getString(R.string.title_tutorial_3),
                context.resources.getString(R.string.description_tutorial_3),
                R.drawable.img_intro_3,
                1
            )
            else -> TutorialFragment.newInstance(
                context.resources.getString(R.string.title_tutorial_4),
                context.resources.getString(R.string.description_tutorial_4),
                R.drawable.img_intro_4,
                0
            )
        }
    }

    override fun getItemCount(): Int {
        return 4
    }
}