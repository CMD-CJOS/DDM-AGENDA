$env:GRADLE_USER_HOME = "C:\Users\Tibuff\Downloads\AGENDA-DDM\.gradle_home"
$env:ANDROID_USER_HOME = "C:\Users\Tibuff\Downloads\AGENDA-DDM\.android_home"
Remove-Item Env:ANDROID_PREFS_ROOT -ErrorAction SilentlyContinue
.\gradlew.bat assembleDebug --no-daemon
