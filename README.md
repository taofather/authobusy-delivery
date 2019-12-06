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
Example Request (with working hardcoded user):

```json
{
  "email":"pepito@test.com",
  "password":"123123"
}
```

```bash
POST /passchange
```

Example request:
```json
{
  "oldpassword":"123123",
  "newpassword":"321321"
}
```

**About DDD**

The Infrastructure/Endpoint package (com.authobusy.endpoint) contains only Controllers and Security implementation. I could have called it 'api', but I wanted to be original.

I prefered not to have an Infrastructure folder. No ports and adapters separation is performed yet.

*The Application level* 

Application level is under com.authobusy.service: imagine that we need some commands to be run from console for the sake of the project.
It will be nice to create a "com.authobusy.command" package (more Infrastructure), and be able to call
services from there.

*New in Authobusy delivery-II* 
- Request objects moved to Application level (com.authobusy.service), as they should be accessible from other hypothetic Infrastructure packages.
- Repository belongs to Domain, traditionally.

**About in-memory database**
I would like to use H2. Now user data is initialized inside the UserRepository constructor :S

**ToDos**
- WebSecurity.configure ignores LoginController action,
populate response body with Token and expiration date