#ttt=$(git "status")
#echo "信息 "$ttt
#printf "aaa"
#function test(){
#echo "函数入参1 = $1"
#return 100
#}
#
#test 400
#echo  "\033[31m 红色字 \033[0m"
message='结束'
if [[ $1 != '' ]];then
    message='开始'
fi
echo $message