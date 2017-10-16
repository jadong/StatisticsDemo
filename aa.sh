filePath=$0
echo "-----------------------------------"
aaa=$(git "pull")
bbb="Already"
echo "数据$aaa"
if  [[ $aaa =~ $bbb ]];then
    echo "包含$aaa"
fi