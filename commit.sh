filePath=$0
echo "当前的命令路径：" $filePath
echo "--------开始执行 pull---------"
git pull
echo "--------开始执行 add .---------"
git add .
echo "--------开始执行 commit---------"
git commit -m 'commit change'
git status
echo "--------开始执行 push---------"
git push

