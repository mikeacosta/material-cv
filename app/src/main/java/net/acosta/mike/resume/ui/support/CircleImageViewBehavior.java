package net.acosta.mike.resume.ui.support;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.appbar.AppBarLayout;

import java.util.logging.Handler;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * Layout Behavior to show/hide a CircleImageView anchored to a AppBarLayout
 */
public class CircleImageViewBehavior extends CoordinatorLayout.Behavior<FrameLayout> {

    private Rect mTmpRect;

    public CircleImageViewBehavior(Context context, AttributeSet attrs, Handler mHandler) {
        super();
    }

    public CircleImageViewBehavior() {
        super();
    }

    public CircleImageViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FrameLayout child, View dependency) {
        // check that our dependency is the AppBarLayout
        return dependency instanceof AppBarLayout;
    }

    @Override public boolean onDependentViewChanged(CoordinatorLayout parent, FrameLayout child,
                                                    View dependency) {
        if (dependency instanceof AppBarLayout) {
            return updateSize(parent, (AppBarLayout) dependency, child);
        }
        return false;
    }

    private boolean updateSize(CoordinatorLayout parent, AppBarLayout dependency, FrameLayout child) {
        int[] dependencyLocation = new int[2];
        int[] childLocation = new int[2];

        dependency.getLocationInWindow(dependencyLocation);
        child.getLocationInWindow(childLocation);

        if ((dependencyLocation[1] <= 0) && (dependencyLocation[1] >= - 200)) {
            float scale = dependencyLocation[1]/200f;

            child.setScaleX(1 + scale);
            child.setScaleY(1 + scale);
            child.setVisibility(View.VISIBLE);
        }

        if (dependencyLocation[1] > 0) {
            child.setScaleX(1);
            child.setScaleY(1);
            child.setVisibility(View.VISIBLE);
        }

        if (dependencyLocation[1] < -200) {
            child.setVisibility(View.INVISIBLE);
        }

        return false;
    }
}
