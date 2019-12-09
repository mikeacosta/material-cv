package net.acosta.mike.resume.ui.support

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import net.acosta.mike.resume.R
import net.acosta.mike.resume.ui.AboutFragment
import net.acosta.mike.resume.ui.CodeFragment
import net.acosta.mike.resume.ui.CredsFragment

class InfoPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {

        private const val TAB_COUNT = 3
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return CredsFragment.newInstance()

            1 -> return AboutFragment.newInstance()

            2 -> return CodeFragment.newInstance()
        }
        return CredsFragment.newInstance()
    }

    override fun getCount(): Int {
        return TAB_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.resources.getString(R.string.creds)

            1 -> return context.resources.getString(R.string.about)

            2 -> return context.resources.getString(R.string.code)
        }
        return super.getPageTitle(position)
    }
}