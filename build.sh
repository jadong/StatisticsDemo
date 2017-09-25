filePath=$0
echo "------------------当前的命令路径：" $filePath "-----------------"
echo "-------------------------开始更新库文件-------------------------"
gradle uploadArchives
echo "-------------------------更新库文件结束-------------------------"
echo "-------------------------开始执行CLEAN-------------------------"
gradle clean
echo "-------------------------执行CLEAN结束-------------------------"
echo "-------------------------开始执行BUILD-------------------------"
#gradle build
gradle assembleDebug
echo "-------------------------执行BUILD结束-------------------------"
echo "------------------------- 开始安装apk -------------------------"
adb install -r /Users/kayo/GitProjects/StatisticsDemo/app/build/outputs/apk/app-debug.apk
echo "-------------------------  启动app  -------------------------"
adb shell am start -n "com.dong.statistics/com.dong.statistics.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER