const NativePermissions = {
  requestScreenLock() {
    if (window.AndroidBridge && window.AndroidBridge.requestLockPermission) {
      window.AndroidBridge.requestLockPermission();
      return true;
    }
    return false;
  },

  checkScreenLockStatus() {
    if (window.AndroidBridge && window.AndroidBridge.isLockPermissionGranted) {
      return window.AndroidBridge.isLockPermissionGranted();
    }
    return false;
  },

  triggerLockScreen() {
    if (window.AndroidBridge && window.AndroidBridge.lockScreenNow) {
      return window.AndroidBridge.lockScreenNow();
    }
    return false;
  },

  requestNotificationsAndCall() {
    if (window.AndroidBridge && window.AndroidBridge.requestRuntimePermissions) {
      window.AndroidBridge.requestRuntimePermissions();
      return true;
    }
    return false;
  },

  openSystemAppSettings() {
    if (window.AndroidBridge && window.AndroidBridge.openAppSettings) {
      window.AndroidBridge.openAppSettings();
      return true;
    }
    return false;
  }
};

