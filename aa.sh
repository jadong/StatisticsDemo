filePath=$0
echo "-----------------------------------"
exit_script(){
    echo "退出j脚本"
    exit 1
}
aaa=$(git "pull")
#aaa="Unpacking objects: 100%"
bbb1="Already up-to-date."
bbb2="Unpacking objects: 100%"
echo "数据$aaa"
if  [[ $aaa =~ $bbb1 ]] || [[ $aaa =~ $bbb2 ]];then
    echo "拉取代码成功"
else
    echo "拉取代码成功失败"
    exit_script
fi
git add .
commitTime=$(date "+%F..%H:%M:%S")
git commit -m "commit change  time==>>$commitTime"



