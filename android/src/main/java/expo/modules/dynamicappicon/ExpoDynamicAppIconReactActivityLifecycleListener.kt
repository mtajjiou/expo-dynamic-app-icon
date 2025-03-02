package expo.modules.dynamicappicon

import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import expo.modules.core.interfaces.ReactActivityLifecycleListener
// import android.widget.Toast


object SharedObject {
    var packageName: String = ""
    var classesToKill = ArrayList<String>()
    var icon: String = ""
    var pm: PackageManager? = null
}
// For Support Contact: bashahowin@gmail.com

// Used Toast for easy Debugging purpose
class ExpoDynamicAppIconReactActivityLifecycleListener : ReactActivityLifecycleListener {

    companion object {
        private const val TAG = "HowincodesDynamicAppIcon"
        private const val BACKGROUND_CHECK_DELAY = 500L
    }

    private val handler = Handler(Looper.getMainLooper())
    private var isChangingIcon = false
    private var isPaused = false

    override fun onPause(activity: Activity) {
        // Toast.makeText(activity, "Onpause Triggered", Toast.LENGTH_LONG).show()
       isPaused = true
        applyIconChange(activity)
    }

    override fun onDestroy(activity: Activity) {
        // Toast.makeText(activity, "OnDestroy Triggered", Toast.LENGTH_LONG).show()
        applyIconChange(activity)
    }

    override fun onResume(activity: Activity) {
        // Toast.makeText(activity, "Onresume Triggered", Toast.LENGTH_LONG).show()
        isPaused = false
    }

    private fun applyIconChange(activity: Activity) {
    // Toast.makeText(activity, "Attempting to change the app icon", Toast.LENGTH_SHORT).show()
    if (isChangingIcon) {
        // Toast.makeText(activity, "Icon change already in progress; skipping", Toast.LENGTH_SHORT).show()
        return
    }

    isChangingIcon = true

    SharedObject.icon.takeIf { it.isNotEmpty() }?.let { icon ->
        val newComponent = ComponentName(SharedObject.packageName, icon)

        if (!doesComponentExist(activity, newComponent)) {
            // Toast.makeText(activity, "Component not found: $icon", Toast.LENGTH_LONG).show()
            isChangingIcon = false
            return
        }

        SharedObject.classesToKill.forEach { cls ->
            if (cls != icon) {
                try {
                    SharedObject.pm?.setComponentEnabledSetting(
                        ComponentName(SharedObject.packageName, cls),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP
                    )
                    // Toast.makeText(activity, "Disabled: $cls", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    // Toast.makeText(activity, "Error disabling: $cls", Toast.LENGTH_SHORT).show()
                }
            }
        }

        SharedObject.classesToKill.clear()

        try {
            SharedObject.pm?.setComponentEnabledSetting(
                newComponent,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
            // Toast.makeText(activity, "Icon changed to: $icon", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            // Toast.makeText(activity, "Error enabling: $icon", Toast.LENGTH_SHORT).show()
        } finally {
            isChangingIcon = false
        }
    }
}


    /**
     * Check if a component exists in the manifest (including disabled ones).
     */
    private fun doesComponentExist(activity: Activity, componentName: ComponentName): Boolean {
    return try {
        val packageInfo = SharedObject.pm?.getPackageInfo(
            SharedObject.packageName,
            PackageManager.GET_ACTIVITIES or PackageManager.GET_DISABLED_COMPONENTS
        )

        val activityExists = packageInfo?.activities?.any { it.name == componentName.className } == true
        // Toast.makeText(activity, "Component exists: ${componentName.className} -> $activityExists", Toast.LENGTH_SHORT).show()
        activityExists
    } catch (e: Exception) {
        // Toast.makeText(activity, "Error checking component existence", Toast.LENGTH_SHORT).show()
        false
    }
}


}