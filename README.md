# TodoApp

Front-end repository: [Todo-App-FE](https://github.com/Karthick-sketch/Todo-App-FE)

### Requirements
* Java version 17
* Gradle version 7.6.1
* MySql version 8


### Steps to run the application

Ensure the **MySql** server is running
```sh
sudo service mysql status
```

Create the **todoapp** database in your **MySql** locally
```sh
CREATE DATABASE todoapp;
```

Build the **TodoApp** application using **gradle**
```sh
gradle clean build
```

Now run the **Jar** file using **Java** 
```sh
java -jar build/libs/todoapp-0.0.1-SNAPSHOT.jar
```


### Todos APIs
Fetch a Todo record by ID
```sh
curl -XGET 'http://localhost:8080/todos/{user-id}'
```

Create a new Todo record
```sh
curl --location 'localhost:8080/todo' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data '{
    "title" : "Learn AWS",
    "dueDate" : 1686663862675,
    "userId" : 1
}
'
```

Update a Todo fields by ID
```sh
curl -XPATCH 'http://localhost:8080/todo/{todo-id}'
```

Delete a Todo record by ID
```sh
curl -XDELETE 'http://localhost:8080/todo/{todo-id}'
```

### Users APIs

Fetch a User record by ID
```sh
curl -XGET 'http://localhost:8080/user/{user-id}'
```

Create a new User record
```sh
curl --location 'localhost:8080/user' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "Ezio Auditore",
    "email" : "ezio.auditore@ubisoft.com",
    "password" : "ac2"
}'
```

Update a User fields by ID
```sh
curl --location --request PATCH 'localhost:8080/user/2' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email" : "ezio@ac.com"
}'
```

Delete a User record by ID
```sh
curl -XDELETE 'http://localhost:8080/user/{user-id}'
```
