package com.epam.androidlab.task3

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    val MY_FRAGMENT_KEY = "${BuildConfig.APPLICATION_ID}_my_fragment"
    var fragment = MyFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.my_container, fragment)
            fragmentTransaction.commit()
        }
    }

    override fun onRestoreInstanceState(savedState: Bundle?) {
        savedState?.let {
            fragment = supportFragmentManager.getFragment(savedState, MY_FRAGMENT_KEY) as MyFragment
        }
        super.onRestoreInstanceState(savedState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        supportFragmentManager.putFragment(outState, MY_FRAGMENT_KEY, fragment as Fragment)
        super.onSaveInstanceState(outState)
    }
}
