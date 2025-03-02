package expo.modules.dynamicappicon

import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import expo.modules.core.interfaces.ReactActivityLifecycleListener
//  import android.widget.Toast


object SharedObject {
    var packageName: String = ""
    var classesToKill = ArrayList<String>()
    var icon: String = ""
    var pm: PackageManager? = null
    var shouldChangeIcon: Boolean = false
}
// For Support Contact: bashahowin@gmail.com

// Used Toast for easy Debugging purpose
class ExpoDynamicAppIconReactActivityLifecycleListener : ReactActivityLifecycleListener {
    override fun onPause(activity: Activity) {

        if(SharedObject.shouldChangeIcon){
            // Toast.makeText(activity, "Onpause Triggered and icon will be changed", Toast.LENGTH_LONG).show()
             applyIconChange(activity)
        }
    }

    override fun onDestroy(activity: Activity) {
        // Toast.makeText(activity, "OnDestroy Triggered", Toast.LENGTH_LONG).show()
        if(SharedObject.shouldChangeIcon){
            // Toast.makeText(activity, "OnDestroy Triggered and icon will be changed", Toast.LENGTH_LONG).show()
             applyIconChange(activity)
        }
       
    }

    override fun onResume(activity: Activity) {
        // Toast.makeText(activity, "Onresume Triggered", Toast.LENGTH_LONG).show()
    }

    private fun applyIconChange(activity: Activity) {
    // Toast.makeText(activity, "Attempting to change the app icon", Toast.LENGTH_SHORT).show()

    SharedObject.icon.takeIf { it.isNotEmpty() }?.let { icon ->
        val newComponent = ComponentName(SharedObject.packageName, icon)
        if (!doesComponentExist(activity, newComponent)) {
            // Toast.makeText(activity, "Component not found: $icon", Toast.LENGTH_LONG).show()
            SharedObject.shouldChangeIcon = false
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
        }finally{
            SharedObject.shouldChangeIcon = false
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