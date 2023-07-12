# TodoApp

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

```sh
curl -XGET 'http://localhost:8080/todos/{user-id}'
```

```sh
curl -XPOST -d '{"title" : "Learn AWS", "dueDate" : 1688149800000, "userId" : 1}' 'http://localhost:8080/todo/'
```

```sh
curl -XPATCH 'http://localhost:8080/todo/{todo-id}'
```

```sh
curl -XDELETE 'http://localhost:8080/todo/{todo-id}'
```

### Users APIs

```sh
curl -XGET 'http://localhost:8080/user/{user-id}'
```

```sh
curl -XPOST -d '{"name" : "Ezio", "email" : "ezio@ac.com", "password" : "ac2"}' 'http://localhost:8080/user/{user-id}'
```

```sh
curl -XPATCH -d '{"name" : "Ezio Auditore", "email" : "ezio.auditore@ac.com", "password" : "ac2br"}' 'http://localhost:8080/user/{user-id}'
```

```sh
curl -XDELETE 'http://localhost:8080/user/{user-id}'
```
