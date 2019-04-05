package net.acosta.mike.resume.ui.support

import android.content.Context
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.widget.TextView

class LinkTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
    : TextView(context, attrs, defStyleAttr) {

    init {
        this.movementMethod = LinkMovementMethod.getInstance()
    }
}