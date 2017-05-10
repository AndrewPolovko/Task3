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
    val TRIANGLE_COLOR = "triangleColor"
    val CIRCLE_COLOR = "circleColor"
    val STAR_COLOR = "starColor"
    lateinit var triangleShape: ImageView
    lateinit var circleShape: ImageView
    lateinit var starShape: ImageView
    lateinit var shapeColors: IntArray


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.my_fragment, container, false)
    }

    override fun onActivityCreated(savedState: Bundle?) {
        initVariables()

        savedState?.let {
            val triangleColor = savedState.getInt(TRIANGLE_COLOR)
            triangleShape.setColorFilter(triangleColor)
            triangleShape.tag = triangleColor

            val circleColor = savedState.getInt(CIRCLE_COLOR)
            circleShape.setColorFilter(circleColor)
            circleShape.tag = circleColor

            val starColor = savedState.getInt(STAR_COLOR)
            starShape.setColorFilter(starColor)
            starShape.tag = starColor
        }
        super.onActivityCreated(savedState)
    }

    fun initVariables() {
        triangleShape = view?.findViewById(R.id.triangle_shape) as ImageView
        circleShape = view?.findViewById(R.id.circle_shape) as ImageView
        starShape = view?.findViewById(R.id.star_shape) as ImageView
        shapeColors = resources.getIntArray(R.array.shapeColors)

        triangleShape.setOnClickListener(this)
        circleShape.setOnClickListener(this)
        starShape.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val anim = AnimationUtils.loadAnimation(activity, R.anim.rotate_animation)

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
                newColor = shapeColors[Random().nextInt(shapeColors.size)]
            } while (v.getTag() == newColor)

        } else {
            newColor = shapeColors[Random().nextInt(shapeColors.size)]
        }
        v.setColorFilter(newColor)
        v.setTag(newColor)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        triangleShape.tag?.let { outState.putInt(TRIANGLE_COLOR, triangleShape.tag as Int) }
        circleShape.tag?.let { outState.putInt(CIRCLE_COLOR, circleShape.tag as Int) }
        starShape.tag?.let { outState.putInt(STAR_COLOR, starShape.tag as Int) }
        super.onSaveInstanceState(outState)
    }
}
