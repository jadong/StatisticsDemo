filePath=$0
echo "当前的命令路径：" $filePath
echo "开始执行BUILD"
#gradle uploadArchives
gradle assembleDebug
java -classpath "/Users/kayo/maven/asm-debug-all-5.0.1.jar" org.objectweb.asm.util.ASMifier /Users/kayo/GitProjects/StatisticsDemo/app/build/intermediates/classes/debug/com/dong/code/GenerateBytecode.class
