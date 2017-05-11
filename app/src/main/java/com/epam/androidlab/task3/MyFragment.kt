package com.epam.androidlab.task3


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import java.util.*


class MyFragment : android.support.v4.app.Fragment(), View.OnClickListener {
    val TRIANGLE_COLOR_KEY = "${BuildConfig.APPLICATION_ID}_triangleColor"
    val CIRCLE_COLOR_KEY = "${BuildConfig.APPLICATION_ID}_circleColor"
    val STAR_COLOR_KEY = "${BuildConfig.APPLICATION_ID}_starColor"
    var triangleColor: Int = 0
    var circleColor: Int = 0
    var starColor: Int = 0

    lateinit var triangleShape: ImageView
    lateinit var circleShape: ImageView
    lateinit var starShape: ImageView

    lateinit var allColors: IntArray
    lateinit var anim: Animation


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.my_fragment, container, false)
    }

    override fun onActivityCreated(savedState: Bundle?) {
        initVariables()

        savedState?.let {
            triangleColor = savedState.getInt(TRIANGLE_COLOR_KEY)
            triangleShape.setColorFilter(triangleColor)
            triangleShape.tag = triangleColor

            circleColor = savedState.getInt(CIRCLE_COLOR_KEY)
            circleShape.setColorFilter(circleColor)
            circleShape.tag = circleColor

            starColor = savedState.getInt(STAR_COLOR_KEY)
            starShape.setColorFilter(starColor)
            starShape.tag = starColor
        }
        super.onActivityCreated(savedState)
    }

    fun initVariables() {
        triangleShape = view?.findViewById(R.id.triangle_shape) as ImageView
        circleShape = view?.findViewById(R.id.circle_shape) as ImageView
        starShape = view?.findViewById(R.id.star_shape) as ImageView
        allColors = resources.getIntArray(R.array.shapeColors)

        triangleShape.setOnClickListener(this)
        circleShape.setOnClickListener(this)
        starShape.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        anim = AnimationUtils.loadAnimation(activity, R.anim.rotate_animation)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                randomizeColor(v)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        v.startAnimation(anim)
    }

    fun randomizeColor(v: View) {
        v as ImageView
        var newColor: Int
        if (v.getTag() != null) {
            do {
                newColor = allColors[Random().nextInt(allColors.size)]
            } while (v.getTag() == newColor)

        } else {
            newColor = allColors[Random().nextInt(allColors.size)]
        }
        v.setColorFilter(newColor)
        v.setTag(newColor)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        triangleShape.tag?.let { outState.putInt(TRIANGLE_COLOR_KEY, triangleShape.tag as Int) }
        circleShape.tag?.let { outState.putInt(CIRCLE_COLOR_KEY, circleShape.tag as Int) }
        starShape.tag?.let { outState.putInt(STAR_COLOR_KEY, starShape.tag as Int) }
        super.onSaveInstanceState(outState)
    }
}
