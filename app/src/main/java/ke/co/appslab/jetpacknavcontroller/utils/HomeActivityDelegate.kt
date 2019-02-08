package ke.co.appslab.jetpacknavcontroller.utils

import androidx.appcompat.widget.Toolbar

interface HomeActivityDelegate {
    fun setupNavDrawer(toolbar: Toolbar)

    fun enableNavDrawer(enable: Boolean)
}