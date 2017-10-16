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
ccc=$(git "commit -m 'commit change  time==>>$commitTime'")
ccc2="no changes added to commit"
ccc3="nothing to commit, working tree clean"
if [[ $ccc =~ $ccc2 ]];then
    echo "提交代码不成功，请稍后重试"
elif [[ $ccc =~ $ccc3 ]];then
    echo "当前没有可提交的信息"
fi



