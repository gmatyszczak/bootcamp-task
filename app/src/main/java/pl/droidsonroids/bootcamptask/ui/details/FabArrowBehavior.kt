package pl.droidsonroids.bootcamptask.ui.details

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View

class FabArrowBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<FloatingActionButton>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View) = dependency is AppBarLayout

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View): Boolean {
        if (dependency is AppBarLayout) {
            val appBarLayout = dependency
            val scrollFactor = -appBarLayout.y / appBarLayout.totalScrollRange

            if (scrollFactor == 1f && !child.isShown) {
                child.show()
            } else if (scrollFactor >= 0f && child.isShown) {
                child.hide()
            }
        }
        return true
    }

}