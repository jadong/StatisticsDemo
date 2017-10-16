#filePath=$0
#echo "当前的命令路径：" $filePath
echo "--------开始执行 pull---------"
git pull
echo "--------开始执行 add .---------"
git add .
echo "--------开始执行 commit---------"
commitTime=$(date "+%F..%H:%M:%S")
git commit -m "commit change  time==>>$commitTime"
git status
echo "--------开始执行 push---------"
git push
echo "--------查看提交状态---------"
git status
echo "--------代码操作结束---------"




