@file:Suppress("unused")

package com.microtears.orange.util

import android.app.Activity
import android.app.ActivityManager
import android.app.NotificationManager
import android.app.WallpaperManager
import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

inline val Context.windowManager: WindowManager
    get() = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager

inline val Context.inputMethodManger: InputMethodManager
    get() = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

inline val Context.notificationManager: NotificationManager
    get() = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

inline val Context.wallpaperManager: WallpaperManager
    get() = applicationContext.getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager

inline val Activity.wallpaperManager: WallpaperManager
    get() = applicationContext.getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager

inline val Context.activityManager: ActivityManager
    get() = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
