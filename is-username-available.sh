#!/bin/bash

if [ -z $1 ]; then
  echo "Invalid usage, you must provide a username!"
  echo "Example: $0 tester99"
  exit 1
fi

if [ $(./is-this-thing-on.sh local-db) -eq 0 ]; then
  echo "Cannot connect to database. Check to see if it is running."
  exit 1
fi

query="select * from quizzard_app.app_users where username = '$1';"

echo "$query" | docker exec -i local-db psql -U postgres | grep $1 | wc -l
