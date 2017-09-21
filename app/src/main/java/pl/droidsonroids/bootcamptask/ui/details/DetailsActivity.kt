package pl.droidsonroids.bootcamptask.ui.details

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import android.view.MenuItem
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.include_details_content.*
import pl.droidsonroids.bootcamptask.R
import pl.droidsonroids.bootcamptask.model.Image


class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IMAGE = "EXTRA_IMAGE"

        fun showDetailsActivity(activity: AppCompatActivity, image: Image, imageView: View, titleView: View) {
            val intent = Intent(activity, DetailsActivity::class.java)
            val pairImage = android.support.v4.util.Pair.create(imageView, "image")
            val pairTitle = android.support.v4.util.Pair.create(titleView, "title")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairImage, pairTitle)
            intent.putExtra(EXTRA_IMAGE, image)
            ActivityCompat.startActivity(activity, intent, options.toBundle())
        }
    }

    private lateinit var pairAuthor: Pair<View, View>
    private lateinit var pairDate: Pair<View, View>
    private lateinit var pairCategory: Pair<View, View>
    private lateinit var pairDescription: Pair<View, View>
    private lateinit var orderList: MutableList<Pair<View, View>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pairAuthor = Pair(authorLabelTextView, authorTextView)
        pairDate = Pair(dateLabelTextView, dateTextView)
        pairCategory = Pair(categoryLabelTextView, categoryTextView)
        pairDescription = Pair(descriptionLabelTextView, descriptionTextView)
        orderList = mutableListOf(pairAuthor, pairDate, pairCategory, pairDescription)

        supportPostponeEnterTransition()
        val image = intent.getSerializableExtra(EXTRA_IMAGE) as Image
        collapsingToolbarLayout.title = image.title
        Picasso.with(this).load(image.url).into(imageView, object : Callback {
            override fun onSuccess() {
                supportStartPostponedEnterTransition()
            }

            override fun onError() {
                supportStartPostponedEnterTransition()
            }
        })

        val sharedElementEnterTransition = window.sharedElementEnterTransition
        sharedElementEnterTransition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition) {
                collapsingToolbarLayout.visibility = View.VISIBLE
                shadeView.animate().alpha(1f).setDuration(500).setInterpolator(DecelerateInterpolator()).start()
            }

            override fun onTransitionResume(p0: Transition?) {
            }

            override fun onTransitionPause(p0: Transition?) {
            }

            override fun onTransitionCancel(p0: Transition?) {
            }

            override fun onTransitionStart(p0: Transition?) {
                collapsingToolbarLayout.visibility = View.INVISIBLE
            }
        })

        authorImageView.setOnClickListener { startSpringAnimation(authorLabelTextView) }
        dateImageView.setOnClickListener { startSpringAnimation(dateLabelTextView) }
        categoryImageView.setOnClickListener { startSpringAnimation(categoryLabelTextView) }
        descriptionImageView.setOnClickListener { startSpringAnimation(descriptionLabelTextView) }
        arrowFab.setOnClickListener { nestedScrollView.scrollTo(0, 0) }
        upDescriptionImageView.setOnClickListener { moveUp(pairDescription) }
        upCategoryImageView.setOnClickListener { moveUp(pairCategory) }
        upDateImageView.setOnClickListener { moveUp(pairDate) }
        upAuthorImageView.setOnClickListener { moveUp(pairAuthor) }
    }

    private fun moveUp(pair: Pair<View, View>) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        val position = orderList.indexOf(pair)
        constraintSet.connect(pair.first.id, ConstraintSet.TOP, R.id.authorImageView, ConstraintSet.BOTTOM)
        constraintSet.connect(orderList[0].first.id, ConstraintSet.TOP, pair.second.id, ConstraintSet.BOTTOM)
        orderList.removeAt(position)
        orderList.add(0, pair)
        if (position < orderList.size - 1) {
            constraintSet.connect(orderList[position + 1].first.id, ConstraintSet.TOP, orderList[position].second.id, ConstraintSet.BOTTOM)
        }
        TransitionManager.beginDelayedTransition(coordinatorLayout)
        constraintSet.applyTo(constraintLayout)
    }

    private fun startSpringAnimation(view: View) {
        val springAnimation = SpringAnimation<View>(view, SpringAnimation.X)
        val spring = SpringForce()
        spring.finalPosition = view.x
        spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        springAnimation.spring = spring
        view.animate().cancel()
        view.animate().translationXBy(100f).setDuration(150).setInterpolator(DecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        springAnimation.start()
                    }
                }).start()

    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            ActivityCompat.finishAfterTransition(this)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}