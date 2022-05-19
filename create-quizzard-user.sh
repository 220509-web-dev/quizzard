#!/bin/bash

# Task: Create a script file that takes in required user information and inserts into a table that exists in your running database

# Syntax rules:
#   - variable assignments should not include any spaces between the variable name, the = sign, or the value
#   - test constructs [], must spaces separating their contents from the brackets themselves
#   - subscript $(), used to execute a command and use its output for something (e.g. store it in a variable)

isDatabaseOn=$(./is-this-thing-on.sh local-db)
if [ $isDatabaseOn -eq 0 ]; then
  echo "Cannot connect to database. Check to see if it is running."
  echo "Cancelling registration."
  exit 1
fi

echo "Please provide some basic information to complete your registration"
read -p "First name: " first
read -p "Last name: " last
read -p "Email address: " email
read -p "Username: " username
read -r -p "Password: " password

# Check to see if the provided email is already taken in the database
emailTaken=$(./is-email-available.sh $email)

# If it is, have the user provide a different email (loop until an available one is given)
while [ $emailTaken -ne 0 ]; do
  echo "A user is already registered with that email, please enter another or type \q to cancel"
  read -r -p "Email address: " email

  if [ $email = "\q" ]; then
    echo "Cancelling registration"
    exit 0
  else
    emailTaken=$(./is-email-available.sh $email)
  fi

done

# Check to see if the provided username is already taken in the database
usernameTaken=$(./is-username-available.sh $username)

# If it is, have the user provide a different username (loop until an available one is given)
while [ $usernameTaken -ne 0 ]; do
  echo "A user is already registered with that username, please choose another"
  read -p "Username: " username
  usernameTaken=$(./is-username-available.sh $username)
done

insert="INSERT INTO quizzard_app.app_users (first_name, last_name, email, username, password, role_id) VALUES ('$first', '$last', '$email', '$username', '$password', 2);"

echo "$insert" | docker exec -i local-db psql -U postgres
