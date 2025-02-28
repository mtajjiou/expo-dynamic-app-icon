# 🎨 @howincodes/expo-dynamic-app-icon

Easily **change your app icon dynamically** in **Expo SDK 52**!

🚀 **Features:**  
✅ Reset icon to default  
✅ Support for **round icons**  
✅ Different icons for **iOS and Android**  
✅ Dynamic icon variants for **iOS** (light, dark, tinted)  
✅ **IOS Icon Update** without alert popup   
✅ **Simple API** to get and set the app icon

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

// Change app icon to 'red'
setAppIcon("red");

// Reset to default icon
setAppIcon(null);
```

Returns:

- `false` if an error occurs
- The **new icon name** on success

### **Get Current Icon**

```typescript
import { getAppIcon } from "@howincodes/expo-dynamic-app-icon";

// Get the current app icon name
const icon = getAppIcon();
console.log(icon); // "red" (or "DEFAULT" if not changed)
```

---

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
