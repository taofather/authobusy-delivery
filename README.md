
**Requirements*
Java 8 installed

**Install**

Run ./gradlew bootRun

**Test**

Run ./gradlew test

**Available endpoints**
GET /check, Check if app is up and running
POST /login

**About DDD**

Application level is com.authobusy.endpoint. I could name it api, but I wanted to be original.
Domain has the most clearly separated layer. Only User entity and some value objects.

I did not used Infrastructure nor Application levels.
I consider obvious that both layers are mixed under endpoint folder.

**About in-memory database**
I would like to use H2. Now user data are initialized inside the UserRepository constructor :S