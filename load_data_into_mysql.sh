#!/bin/bash
# 传入参数@时间，将符合时间的文件上传到mysql
#定义变量
mysql_files=/home/mysql/mysql-files/
mysql_pass=Ymt@123456
mysql_user=root
table_c=consume
table_r=consume_routing_recording
db=ymt_history

input_date=$1
if [ $input_date"x" = "x" ];
then
   echo "请传入时间参数"
   exit 1
fi

data_files=`ls ${mysql_files} | grep 'tar' |grep ${input_date}`
file_count=`ls ${mysql_files} | grep ${input_date}| wc -l`

if [ $file_count -lt 1 ];
then
    echo "沒有符合要求的文件，脚本結束"
    exit 1
fi

function mysql_server(){
mysql -u${mysql_user} -p${mysql_pass} <<EOF
use ${db};
create table if not exists ${table_c}_$1 like ${table_c}_template;
load data infile "${mysql_files}$1/${table_c}_1.txt" replace into table ${table_c}_$1 fields terminated by '|'
lines terminated by '\n';
create table if not exists ${table_r}_$1 like ${table_r}_template;
load data infile "${mysql_files}$1/${table_r}_1.txt" replace into table ${table_r}_$1 fields terminated by '|'
lines terminated by '\n';
EOF
}

function load_data_into_mysql(){
    data_file=$1
    echo $data_file
    tar -xf $data_file
    file_date=${data_file%%.*}
#    echo $file_date
    cd ${file_date}


}
cd $mysql_files
for file in ${data_files[@]}
do
    load_data_into_mysql $file
done