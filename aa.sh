filePath=$0

log(){
    l1="-----------------------------------"
    l2="-----"
    if [[ $3 = "e" ]];then
        l2=""
    fi
    if [ $1 = "i" ];then
#        echo  "$l1"
        echo  "\033[32m$l2$2$l2\033[0m"
#        echo  "$l1"
    elif [ $1='w' ];then
#        echo  "\033[31m$l1\033[0m"
        echo  "\033[31m$l2$2$l2\033[0m"
#        echo  "\033[31m$l1\033[0m"
    elif [ $1='m' ];then
        echo  "$l2$2$l2"
    elif [];then
        echo  "\033[34m$l2$2$l2\033[0m"

    fi
}
message(){
    log "m" "$1" "$2"
}
tip(){
    log "t" "$1" "$2"
}
info(){
    log "i" "$1" "$2"
}
warn(){
    log "w" "$1" "$2"
}
exit_script(){
    warn "退出脚本"
    exit 1
}
#查看状态
sss1=$normal
sss2="Changes to be committed"
function check_status(){
    info "开始验证提交状态"
    sss=$(git "status")
    if [[ $sss =~ $sss1 ]]; then
        echo "当前状态无可提交数据"
    else
        echo "当前有可提交状态数据，请重试"
        exit_script
    fi
}
normal="nothing to commit, working tree clean"
info '执行 pull' 'e'

aaa=$(git "pull")
bbb1="Already up-to-date."
bbb2="Unpacking objects: 100%"
echo -e "$aaa"
if  [[ $aaa =~ $bbb1 ]];then
    echo "拉取代码成功"
elif [[ $aaa =~ $bbb2 ]];then
    echo "当前代码为最新状态，无需更新"
    exit_script
else
    echo "拉取代码成功失败"
    exit_script
fi
#添加修改的文件
info "执行 add . 添加更改文件"
git add .

info "执行 commit"
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

#push代码
warn "执行 push"
git push
echo "-----代码推送完成-----"
check_status



