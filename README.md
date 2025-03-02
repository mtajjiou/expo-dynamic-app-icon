# 🎨 @howincodes/expo-dynamic-app-icon

Easily **change your app icon dynamically** in **Expo SDK 52**!

## 🚀 **What's New in v2:**

✨ **Android icon change without app restart!**  
✨ Seamless icon updates while the app stays running  
✨ Improved stability and performance

## 🎁 **Features:**

✅ Reset icon to default  
✅ Support for **round icons**  
✅ Different icons for **iOS and Android**  
✅ Dynamic icon variants for **iOS** (light, dark, tinted)  
✅ iOS icon update **with or without alert popup**  
✅ **Simple API** to get and set the app icon

## Demo🚀

![dynamic-icon-demo-5](https://github.com/user-attachments/assets/3dced15a-8d4e-4eb9-b76c-4c7c8fc9f59a)

---

## 📦 Installation

```sh
npx expo install @howincodes/expo-dynamic-app-icon
```

---

## 🔧 Setup

Add the plugin to your `app.json`:

```json
"plugins": [
  [
    "@howincodes/expo-dynamic-app-icon",
    {
      "red": {
        "ios": "./assets/ios_icon1.png",
        "android": "./assets/android_icon1.png"
      },
      "gray": {
        "android": "./assets/icon2.png"
      },
      "dynamic": {
        "ios": {
          "light": "./assets/ios_icon_light.png",
          "dark": "./assets/ios_icon_dark.png",
          "tinted": "./assets/ios_icon_tinted.png"
        }
      }
    }
  ]
]
```

---

## 📜 Android Setup

Run the following command:

```sh
expo prebuild
```

Then, check if the following lines have been added to `AndroidManifest.xml`:

```xml
<activity-alias
  android:name="expo.modules.dynamicappicon.example.MainActivityred"
  android:enabled="false"
  android:exported="true"
  android:icon="@mipmap/red"
  android:targetActivity=".MainActivity">
  <intent-filter>
    <action android:name="android.intent.action.MAIN"/>
    <category android:name="android.intent.category.LAUNCHER"/>
  </intent-filter>
</activity-alias>

<activity-alias
  android:name="expo.modules.dynamicappicon.example.MainActivitygray"
  android:enabled="false"
  android:exported="true"
  android:icon="@mipmap/gray"
  android:targetActivity=".MainActivity">
  <intent-filter>
    <action android:name="android.intent.action.MAIN"/>
    <category android:name="android.intent.category.LAUNCHER"/>
  </intent-filter>
</activity-alias>
```

---

## 🚀 Usage

### **Set App Icon**

```typescript
import { setAppIcon } from "@howincodes/expo-dynamic-app-icon";

/**
 * Change app icon to 'red'
 */
setAppIcon("red");

/**
 * Reset to default icon
 */
setAppIcon(null);
```

#### ✅ Available Parameters:

```typescript
setAppIcon(
  name: IconName | null,
  isInBackground?: boolean
)
```

| Parameter        | Type               | Default | Description                                                                                                                    |
| ---------------- | ------------------ | ------- | ------------------------------------------------------------------------------------------------------------------------------ |
| `name`           | `IconName \| null` | `null`  | The icon name to switch to. Pass `null` to reset to the default icon.                                                          |
| `isInBackground` | `boolean`          | `true`  | - `true`: Icon changes silently in the background (no alert on iOS).<br>- `false`: Immediate change, with system alert on iOS. |

#### ✅ Returns:

- `"DEFAULT"` if reset to the original icon.
- The **new icon name** on success.
- `false` if an error occurs.

---

### **Get Current Icon**

```typescript
import { getAppIcon } from "@howincodes/expo-dynamic-app-icon";

// Get the current app icon name
const icon = getAppIcon();
console.log(icon); // "red" (or "DEFAULT" if not changed)
```

---

### ⚠️ Notes:

- **Android limitations:**  
  Android does **not** support icon changes while the app is running in the foreground.  
  To work around this, the icon is changed when the app enters the **Pause state** (background).

- ⚠️ **Pause state** can also trigger during events like permission dialogs.  
  To avoid unwanted icon changes, a **5-second delay** is added to ensure the app is truly in the background.

- To disable the delay and apply the icon change immediately (with the risk of it running during permission dialogs or other pause events), set:

  ```typescript
  setAppIcon("red", false);
  ```

  - On **iOS**, `isInBackground: false` triggers the system alert immediately.
  - On **Android**, it applies the icon change right away without waiting.

## ☕ Support the Original Author

A huge shoutout to [outsung](https://github.com/outsung) for the original package! 🎉

If you find this useful, consider **buying him a coffee**:

<a href="https://www.buymeacoffee.com/outsung" target="_blank">
  <img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" height="41" width="174" />
</a>

---

## 🌐 About Us

This package is maintained by **[HowinCloud](https://howincloud.com/)** – delivering powerful cloud-based solutions for modern app development.

🔥 **Enjoy building dynamic and customizable apps with Expo!** 🚀
