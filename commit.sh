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
    elif [ $1 = 'w' ];then
#        echo  "\033[31m$l1\033[0m"
        echo  "\033[31m$l2$2$l2\033[0m"
#        echo  "\033[31m$l1\033[0m"
    elif [ $1 = 'm' ];then
        echo  "$l2$2$l2"
    elif [ $1 = 't' ];then
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
normal="nothing to commit, working tree clean"
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

msg=""
if [[ $1 != '' ]];then
    msg="$1"
fi

info '执行 pull'

aaa=$(git "pull")
bbb1="Already up-to-date."
bbb2="Unpacking objects: 100%"
message "$aaa" "e"
if  [[ $aaa =~ $bbb1 ]];then
    tip "拉取代码成功" 'e'
elif [[ $aaa =~ $bbb2 ]];then
    tip "当前代码为最新状态，无需更新" "e"
    exit_script
else
    warn "拉取代码成功失败" "e"
    exit_script
fi
#添加修改的文件
info "执行 add . 添加更改文件"
git add .

info "执行 commit"
commitTime=$(date "+%F..%H:%M:%S")
msg1="commit change  time==>>$commitTime"
if [[ $msg = '' ]];then
    msg=$msg1
fi
ccc=$(git commit -m "$msg")
ccc2="no changes added to commit"
ccc3=$normal
echo $ccc
if [[ $ccc =~ $ccc2 ]];then
    tip "提交代码不成功，请稍后重试" "e"
    exit_script
elif [[ $ccc =~ $ccc3 ]];then
    tip "当前没有可提交的信息" "e"
    exit_script
else
    tip "commit成功 " 'e'
fi

#push代码
info "执行 push"
git push
tip "代码推送完成"
check_status



