#!/bin/bash

echo "select * from quizzard_app.app_users;" | docker exec -i local-db psql -U postgres --html | tee result.html
