Authobusy: endpoint
==================

**Requirements**
Java 8 installed

**Install and run**

```bash
$ ./gradlew bootRun
```
**Test**

```bash
$ ./gradlew test
```

Not too verbose, better see the code.

**Available endpoints**

```bash
GET /check
```
checks if app is up and running

```bash
POST /login
```

**About DDD**

Application level is com.authobusy.endpoint. I could have called it 'api', but I wanted to be original.

Domain has the most clearly separated layer. It contains the User entity and some value objects.

I prefered not to have an Infrastructure folder. Application/Infrastructure mix is under com.authobusy.endpoint.

But I believe that the "service" and "repository" folders can be out of endpoint: imagine that we need some
commands to be run from console for the sake of the project. It will be nice to create a "com.authobusy.command" package, and be able to call
services or repos from the same level.

**About in-memory database**
I would like to use H2. Now user data is initialized inside the UserRepository constructor :S

**ToDos**
- WebSecurity.configure ignores LoginController action,
populate response body with Token and expiration date