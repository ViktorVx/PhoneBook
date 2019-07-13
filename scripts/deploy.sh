#!/usr/bin/env bash

mvn clean package

echo "Copy files..."

scp -i ~/.ssh/id_rsa \
    target/PhoneBook-1.0-SNAPSHOT.jar \
    osboxes@192.168.1.42:/home/osboxes/

echo "Restart server ..."

ssh -i ~/.ssh/id_rsa osboxes@192.168.1.42 << EOF

pgrep java | xargs kill
nohup java -jar PhoneBook-1.0-SNAPSHOT.jar > log.txt &

EOF

echo "Bye"

