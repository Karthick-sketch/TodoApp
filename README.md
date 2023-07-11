# TodoApp

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
