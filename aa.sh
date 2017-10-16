filePath=$0

log(){
    echo "-----------------------------------"
    echo "-----$1-----"
    echo "-----------------------------------"
}
exit_script(){
    echo "退出j脚本"
    exit 1
}
normal="nothing to commit, working tree clean"
log "执行 pull"

aaa=$(git "pull")
#aaa="Unpacking objects: 100%"
bbb1="Already up-to-date."
bbb2="Unpacking objects: 100%"
echo "$aaa"
if  [[ $aaa =~ $bbb1 ]] || [[ $aaa =~ $bbb2 ]];then
    echo "拉取代码成功"
else
    echo "拉取代码成功失败"
    exit_script
fi
#添加修改的文件
log "执行 add . 添加更改文件"
git add .

log "执行 commit"
commitTime=$(date "+%F..%H:%M:%S")
ccc=$(git commit -m "commit change  time==>>$commitTime")
ccc2="no changes added to commit"
ccc3=$normal
echo $ccc
if [[ $ccc =~ $ccc2 ]];then
    echo "提交代码不成功，请稍后重试"
    exit_script
elif [[ $ccc =~ $ccc3 ]];then
    echo "当前没有可提交的信息"
    exit_script
else
    echo "commit成功 "
fi
#查看状态
sss1=$normal
sss2="Changes to be committed"
function check_status(){
    log "开始验证提交状态"
    sss=$(git "status")
    if [[ $sss =~ $sss1 ]]; then
        echo "当前状态无可提交数据"
    else
        echo "当前有可提交状态数据，请重试"
        #exit_script
    fi
}
#push代码
log "执行 push"
ppp1="Compressing objects: 100%"
ppp=$(git push)
echo -e $ppp
check_status



