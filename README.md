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

Issue: some test fail running this way, but passes using IntelliJ bulk test.

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

The Infrastructure/Endpoint package (com.authobusy.endpoint) contains only Controllers and Security implementation. I could have called it 'api', but I wanted to be original.

I prefered not to have an Infrastructure folder. No ports and adapters separation is performed yet.

*The Application level* 

Application level is under com.authobusy.service: imagine that we need some commands to be run from console for the sake of the project.
It will be nice to create a "com.authobusy.command" package (more Infrastructure), and be able to call
services from there.

**About in-memory database**
I would like to use H2. Now user data is initialized inside the UserRepository constructor :S

**ToDos**
- WebSecurity.configure ignores LoginController action,
populate response body with Token and expiration date