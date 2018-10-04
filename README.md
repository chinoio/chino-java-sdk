#  CHINO.io Java SDK [ [![Build Status](https://travis-ci.org/chinoio/chino-java.svg?branch=master)](https://travis-ci.org/chinoio/chino-java) [![](https://jitpack.io/v/chinoio/chino-java.svg)](https://jitpack.io/#chinoio/chino-java) ]
Official Java wrapper for [**CHINO.io** API](https://chino.io).

Full API docs are available [here](http://docs.chino.io).

#### What's new - version 1.3

* **Upgraded minimum JDK version**:
    
    Since official support for Java 7 will be dropped in December 2018, the minimum SDK version for the Chino.io SDK
    has been raised to Java 8. We suggest to use the `openjdk8` build. 

* **Removed deprecated method from version 1.2.3**:
    
    This change affects a method in class `io.chino.java.Users`.

* **Search API**:

    The Search API used to produce strange requests, which now have been fixed.
    
    We have redesigned the Search system to be more developer-friendly and as a consequence 
    **the old Search API have been deprecated**. They will be removed in a future version (1.3.X or 1.4).
    
    A new Search interface has been implemented: see the *Search* section below to learn more.
    We strongly suggest to migrate to the new Search API as soon as possible to preserve compatibility with new versions 
    of our SDK.
    
* **Permissions API**

    We also redesigned the interface of the **Permissions API** and made it easier to use.
    You can now set or revoke Permissions to your Users with fewer lines of code and in a better readable fashion.
    
    The old interface is still working but has been deprecated and **will be removed in a
    future version** (1.3.X or 1.4).
    
* **BLOBs API**:
    
    From the next release of Chino.io Java SDK, BLOB uploads will only work with the `uploadBlob(...)` method.
    Methods that handle the intermediate steps of the upload will no longer be accessible.
    
    Affected methods are `initUpload`, `uploadChunk` and `commitUpload`.
    
    Also method `delete(String, boolean)` will be removed from the public API and replaced by `delete(String)`.
    
* **Documents API** and **Users API**:

    Now it is possible to wait for Documents and Users to be fully indexed, so that they will appear in the Search results
    right after the `create(...)` and `update(...)` methods return. See the [*Documents*](#documents-iochinojavadocuments) and [*Users*](#users-iochinojavausers-and-userschemas-iochinojavauserschemas) section below to learn more about synchronous creation and update.
    
* **Other minor changes**:
    * Added Javadoc for most of the classes of the SDK; existing documentation has been fixed and updated.
    * New method `Users.checkPassword(String password)` to verify a User's password
    

## Setup

### Build with Maven 
Edit your project's "pom.xml" and add this:

```xml
<repositories>
    <!-- other repositories... -->
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <!-- other dependencies... -->
    <groupId>com.github.chinoio</groupId>
        <artifactId>chino-java</artifactId>
    <version>1.3</version>
</dependency>
```

### Build with Gradle
Edit your project's "build.gradle" and add this:

```groovy
allprojects {
    repositories {
        // other repositories...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    // other dependencies...
    compile 'com.github.chinoio:chino-java:1.3'
}
```

Due to a bug in gradle, if you're developing in android you have to add also the following code
to the "build.gradle" file

```groovy
android {
    // other settings...
    packagingOptions {
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/LICENSE'
    }
}
```

### Build from source

1. Clone the repository locally and *cd* into the root folder:

        git clone https://github.com/chinoio/chino-java.git
        cd chino-java

2. Build the SDK with the command
    
        ./gradlew build

    The output will be in the `/build` folder.

3. if you want a "fat jar" with all the dependencies inside, run

        ./gradlew build shadowJar

4. Finally, you can generate the Javadoc for this project (see below)

        
## Javadoc
The Javadoc for this version of the SDK can be obtained:

* from the *Attachments* section of the latest [GitHub Release](https://github.com/chinoio/chino-java/releases), as a JAR archive
* by executing the Gradle task `javadoc` locally, that will create the HTML files in `build/docs/javadoc`:

        ./gradlew build javadoc
        
    or the task `javadocJar`, that will package them inside a JAR in `build/libs/chino-java-1.3-javadoc.jar`:
        
        ./gradlew build javadocJar

* from [jitpack.io](https://jitpack.io/com/github/chinoio/chino-java/1.3/javadoc/)
    
## Usage
In order to use this SDK you need to register an account at the [Chino.io console](https://console.test.chino.io/).

From the Console you can see your API keys and create/manage resources of Chino.io, such as Users and Documents.

### "Hello, World!" Document
The most common task you can perform is the creation of a Document, where data can be stored.

1. Create a ChinoAPI variable with your `customer_id` and `customer_key`
    
    ```Java
    String customerId, // paste your Customer ID
       customerKey;    // paste your Customer Key
    ChinoAPI chino = new ChinoAPI("https://api.test.chino.io", customerId, customerKey);
    ```

2. Create a Repository

    ```Java
    Repository r = chino.repositories.create("Sample repository");
    ```

3. Create a Schema inside the Repository.

    ```Java
    List<Field> fieldsList = new LinkedList<>();
    fieldsList.add(new Field ("the_content", "string"));
    SchemaStructure schemaFields = new SchemaStructure(fieldsList);
    Schema s = chino.schemas.create(r.getRepositoryId(), "Sample Schems", schemaFields);
    ```

4. Create a Document

    ```Java
    HashMap<String, Object> values = new HashMap<>();
    values.put("the_content", "Hello, World!");
    Document d = chino.documents.create(s.getSchemaId(), values);
    ```

And that's it! from here you can retrieve, update and delete your Document with a single API call.

Chino.io also offers User management and authentication, GDPR/HIPAA-compliant Consent tracking,
permissions over resources, resource indexing and search. [See what you can do with Chino.io](https://chino.io/api-tutorials-home).

## The SDK
The Java SDK implements all the features that are offered by **Chino.io API v1**.

The package `io.chino.java` contains everything you need to work with this SDK;
we provide an overview of how it can be used.

***We suggest to read the [Chino.io API docs](https://docs.chino.io/) when using this SDK.*** 
 
### The ChinoAPI client
Main API client for sending API calls to Chino.io API.

You need an authenticated client in order to perform API call. There are several options to authenticate a client:

* Authenticate with **customer credentials** (found on Chino.io console). Only for admin access - [Learn more](https://docs.chino.io/#header-application-developers)
    ```Java
    ChinoAPI chino = new ChinoAPI(<host_url>, <customer_id>, <customer_key>);
    ```
    
* Authenticate with **bearer token** (get one using class [Auth](#auth-iochinojavaauth))
    ```Java
    ChinoAPI chino = new ChinoAPI(<host_url>, <bearer_token>);
    ```
    
* **Don't authenticate** - you will need to *login* later (see [Auth](#auth-iochinojavaauth))
    ```Java
    ChinoAPI chino = new ChinoAPI(<host_url>);
    ```
    
Use the test "host_url" `https://api.test.chino.io` during development - test sandbox is **free** to use.

You can change the authentication type with:
```Java
chino.setCustomer(<customer_id>, <customer_key>);
chino.setBearerToken(<bearer_token>);
```

Check out [ChinoAPITest.java](https://github.com/chinoio/chino-java/blob/develop/src/test/java/io/chino/java/ChinoAPITest.java)
to see some practical usage examples of the `ChinoAPI` client.
    
### Users `io.chino.java.Users` and UserSchemas `io.chino.java.UserSchemas`
API clients for managing UserSchemas and Users. [*See full docs*](https://docs.chino.io/#applications)

A **User** on Chino.io represents a person and is bound to a **username & password** pair, which can be used to log in to
[Applications](#applications-iochinojavaapplications) and get access tokens. In the User object can be stored
additional attributes, the name and type of which is defined in a **UserSchema**.

Other usage of Users include differentiating between roles (doctor and patients), identity verification, 
setting different access [Permissions](#permissions-iochinojavapermissions) over different resources.

To learn more about Users, check out the [tutorial](https://chino.io/tutorials/tutorial-users).

***New in 1.3*** **- "consistent" creation calls**

You can wait for indexing of Users to end before proceeding.
This is useful if you plan to [Search](#search-iochinojavasearch) for Users right after the creation.

Use the following methods:
- `create(<user_schema_id>, <attributes>, <consistent>)`
- `update(<user_id>, <attributes>, <consistent>)`



### Auth `io.chino.java.Auth`
API client for User authentication. [*See full docs*](https://docs.chino.io/#user-authentication)

Auth works together with [Applications](#applications-iochinojavaapplications) to provide
OAuth2 authentication to Users. [Read more about Chino.io and OAuth2](https://chino.io/tutorials/tutorial-auth)

When working with multiple Users, start with a non-authenticated client
```Java
ChinoAPI chino = new ChinoAPI(<host_url>);
```

Then ask the User to input its username and password and login with those credentials.
Users login to an Application, so you should create one and save the `applicationId` and `applicationSecret`.

```Java
LoggedUser tokens = chino.auth.login(username, password, appId, appSecret);
```

The `LoggedUser` object contains an **access_token** (a.k.a. bearer token) and a **refresh token** that are also 
stored in the `chino` client after the method returns and used to authenticate the API calls.

### Applications `io.chino.java.Applications`
API client for User authentication and management of OAuth2 clients. [*See full docs*](https://docs.chino.io/#applications)

Applications on Chino.io implement the "client credentials grant" of OAuth2. [Read more about Chino.io and OAuth2](https://chino.io/tutorials/tutorial-auth)

Applications can be of two types:
1. **Public clients** that are executed on a device (such as a PC, Mac, smartphone, Raspberry, etc...)
    or run partially on the front-end (e.g. a script in a web browser).
    Those clients are considered unsafe and developers must *never* store credentials on them, such as
    the `customer_id` and `customer_key` of Chino.io.
    
    This type of client is represented by "public" Applications on Chino.io and are identified with an
    `application_id`.
    
2. **Confidential clients** are executed exclusively on server-side, where only an admin has access.
    Those clients are considered a safe place for sensitive information.
    
    They are represented by "confidential" Applications on Chino.io and are identified by both an
    `application_id` and an `application_secret`.


### Groups `io.chino.java.Groups`
API client for creating and managing Groups. [*See full docs*](https://docs.chino.io/#groups)

Groups can be used to collect Users regardless of their UserSchema, can have attributes and can be granted 
[Permissions](#permissions-iochinojavapermissions) over other resources.

### Permissions `io.chino.java.Permissions`
API client to manage access Permissions of Users to the resources. [*See full docs*](https://docs.chino.io/#permissions)

***New in v1.3*** **- new Permissions interface**:

The new system provides: 
* a `PermissionSetter` to specify which Permissions will be granted, as in this JSON object:
```JSON
{
  "manage" : [ /* list of permissions for the user */ ],
  "authorize" : [ /* list of permissions that the user can give to other users */ ],
  "created_document" : {
    // can only be specified for documents in a Schema;
    // default permissions that are given on new documents created in the Schema.
    "manage" : [ /* default permissions for the user */ ],
    "authorize" : [ /* default permissions that the user can give to other users */ ]
  }
}
```
* a `PermissionsRequestBuilder` to specify the target resource and the user which will obtain (or lose) the Permissions.
    1. specify a target resource:
        * `on(ResourceType, "resourceId")` apply Permissions to a single resource
        * `onChildrenOf(ResourceType, "resourceId")` apply Permissions to every child resource. Only works with REPOSITORY, SCHEMA, USER_SCHEMA.
        * `onEvery(ResourceType)` apply permissions to all resources. Only works with REPOSITORY, USER_SCHEMA, GROUP
    2. specify a subject:
        * `toUser("userId")` or `to(User)`: apply Permissions to a single [User](#users-iochinojavausers-and-userschemas-iochinojavauserschemas)
        * `toGroup("groupId")` or `to(Group)`: apply Permissions to all Users in a [Group](#groups-iochinojavagroups)
    3. use `buildRequest()` to get a `PermissionsRequest` and execute it:
        * `chino.permissions.executeRequest(PermissionsRequest)`
* some constant values (as `enum`s) that represent resources and permission types:
    * `Permissions.Type`: the types of grant that can be specified, e.g. `CREATE`, `DELETE`, `LIST` etc...
    * `Permissions.ResourceType`: the resources of Chino.io, e.g. `DOCUMENTS`, `SCHEMAS`, `GROUPS`, etc...
    
#### Example 1: Grant CRUD permissions over a Document
```Java
import static io.chino.java.Permissions.Type.*;

public class GrantCRUD {
    public static void main (String[] s) {
        Document doc = chino.documents.read("some-document-id");
        PermissionsRequest req = chino.permissions.grant()
                .toUser("some-user-id")
                .on(Permissions.ResourceType.DOCUMENT, doc.getDocumentId)
                .permissions(
                        new PermissionSetter()
                        .manage(
                                CREATE,     // Permissions.Type.CREATE, UPDATE, etc:
                                READ,       // the Permissions.Type part is
                                UPDATE,     // omitted because of static import
                                DELETE      // on top of the page
                        )
                ).buildRequest();
        
        // Then use one of the following methods (they are equivalent) to execute the request:
        req.execute();
        chino.permissions.executeRequest(req);
    }
}
```

#### Example 2: Revoke permission on all Repositories
In this example, User 'oldAdmin' has Permission to `LIST` every Repository; it also can authorize other people to do the same.

We can revoke those Permissions this way:
```Java
import static io.chino.java.Permissions.Type.*;
import static io.chino.java.Permissions.ResourceType.*;

public class RevokeAuthorization {
    public static void main (String[] s) {
        User oldAdmin = chino.users.read("oldAdmin-user-id");
        
        chino.permissions.revoke()
                    .onEvery(REPOSITORY)
                    .to(oldAdmin)
                    .permissions(
                            new PermissionSetter()
                            .manage(LIST, READ)
                            .authorize(LIST)
                    )
            .buildRequest()
        .execute();
    }
}
```

### Repositories `io.chino.java.Repositories`
API client for management of Repositories. [*See full docs*](https://docs.chino.io/#repositories)

Repositories act as containers for [Schemas](#schemas-iochinojavaschemas).
 

### Schemas `io.chino.java.Schemas`
API client for management of Schemas. [*See full docs*](https://docs.chino.io/#schemas)

Schemas define the structure of [Documents](#documents-iochinojavadocuments),
i.e. the name and type of their attributes and which ones are indexed for
[Search](#search-iochinojavasearch).

### Documents `io.chino.java.Documents`
API client for management of Documents. [*See full docs*](https://docs.chino.io/#documents)

Documents are used on Chino.io to store sensitive information. Learn more about Documents in the [tutorial](https://chino.io/tutorials/tutorial-docs).

***New in 1.3*** **- "consistent" creation & update calls**

You can wait for indexing of Documents to end before proceeding.
This is useful if you plan to [Search](#search-iochinojavasearch) for Documents right after the creation.

Use the following methods:
- `create(<schema_id>, <content>, <consistent>)`
- `update(<document_id>, <content>, <consistent>)`

### BLOBs `io.chino.java.Blobs`
API client for binary file (BLOB) upload. [*See full docs*](https://docs.chino.io/#blobs)

***New in v1.3*** **- deprecated methods**:

- `uploadBlob(<path>, <document_id>, <field>, <file_name>)`
    this is the main function which handles the upload of a Blob from start to end.

The following functions are deprecated and will be removed soon:
- `get(<blob_id>, <destination>)`
- `initUpload(<document_id>, <field>, <file_name>)`
- `uploadChunk(<upload_id>, <chunk_data>, <offset>, <length>)`
- `commitUpload(<upload_id>)`
- `delete(<blob_id>, <force>)`

### Search `io.chino.java.Search`
API client to perform search operations on Chino.io resources. [*See full docs*](https://docs.chino.io/#search-api)

***New in v1.3*** **- new Search interface**:

We have updated our Search API, implementing:
 
* a more dev-friendly interface
* support for complex queries - now supporting multiple conditional operators (AND, OR, NOT) in one query.
* a `SearchQueryBuilder` class, that makes queries easily repeatable and thread-safe 

The new Search requests must contain the following parameters:
* the Search domain, either a `UserSchema` or a `Schema`
* the `ResultType`, which can be one of `FULL_CONTENT`, `NO_CONTENT`, `ONLY_ID` and `COUNT`
* any amount (even 0) of `SortRule` objects
* a **query** describing the conditions that the search results must match:
    * name of an indexed **field**
    * an **operator** from class FilterOperator, i.e. one of `EQUALS`, `GREATER_EQUAL`, `GREATER_THAN`, `IN`, `IS`, 
    `LIKE`, `LOWER_EQUAL`, `LOWER_THAN`
    * the expected **value** for the comparison

Then the query must be built using the `buildSearch()` method.

```Java
    UserSearch search = (UserSearch) chino.search
        // search domain
    .users("user-schema-id")
        // result type
    .setResultType(ResultType.FULL_CONTENT)
        // sort rules (0 or more)
    .addSortRule("first_name", SortRule.Order.ASC)
        // query starts here:
            .with("last_name", FilterOperator.EQUALS, "Rossi")
            .and("age", FilterOperator.GREATER_THAN, 59)  
        // return a search client to perform the query            
    .buildSearch();
``` 

The returned object is a subclass of `AbstractSearchClient` that can perform that query. By calling
```java
    search.execute();
```
you will send the API call, just like in the old search, and obtain a GetDocumentResponse.

More complex queries can be made, e.g. nested queries.

#### Example 1 - `last_name = "Smith" OR last_name = "Snow"`
simple condition (taken from the [official docs](https://chino.io/))
```java
    chino.search.users(<user-schema-id>).setResultType(ResultType.FULL_CONTENT).addSortRule("first_name", SortRule.Order.ASC)
         .with("last_name", FilterOperator.EQUALS, "Smith")
         .or("last_name", FilterOperator.EQUALS, "Snow")
         .buildSearch().execute();
```

----------------------------------------------------------

#### Example 2 - `last_name="smith" AND (age>60 OR (NOT age >= 20)))`
nested queries (taken from the [official docs](https://chino.io/) and improved)
```java
    chino.search.users(<user-schema-id>).setResultType(ResultType.FULL_CONTENT).addSortRule("first_name", SortRule.Order.ASC)
             .with("last_name", FilterOperator.EQUALS, "Smith")
             .and(
                     // create new query: use the following static method
                     SearchQueryBuilder.with("age", FilterOperator.GREATER_THAN, 60)
                     .or(
                             // create negated query: another static method
                             SearchQueryBuilder.not("age", FilterOperator.GREATER_EQUAL, 20)
                     )
             )
             .buildSearch().execute();
```

----------------------------------------------------------

#### Example 3 - `(NOT document_title = "empty") AND page_count <= 30`
use of **static imports** to improve code readability
```java
    import static SearchQueryBuilder.with;
    import static SearchQueryBuilder.not;
    import static FilterOperator.*;
    
    . . .
    
    chino.search.users(<user-schema-id>).setResultType(ResultType.FULL_CONTENT).addSortRule("first_name", SortRule.Order.ASC)
             .with(
                     not("document_title", EQUALS, "empty")
             )
             .and(
                     with("page_count", LOWER_EQUAL, 30)
             )
             .buildSearch().execute();
```

#### New Search API overview:

`io.chino.java.Search`:
- `users(String userSchemaId)`: get a new `UsersSearch` client.
- `documents(String schemaId)`: get a new `DocumentsSearch` client.

`io.chino.api.search.UsersSearch` & `*.DocumentsSearch` (implementations of `*.AbstractSearchClient`):
- `setResultType(ResultType type)`: overwrite the type of the results returned by this search
- `addSortRule(String field, SortRule.Order order)`: add a sort rule to this search
- `with(String field, FilterOperator type, ? value)`: get a new `SearchQueryBuilder` 
with the search condition in the parameters.
- `with(SearchQueryBuilder query)`: get a new `SearchQueryBuilder` from the `query` parameter.

`io.chino.api.search.SearchQueryBuilder`:
- (static) `with(String field, FilterOperator type, ? value)`: negate the search condition in the parameters.
- (static) `with(SearchQueryBuilder query)`: return the `query` parameter.
- (static) `not(String field, FilterOperator type, ? value)`: negate the search condition in the parameters
 and return it as a new query.
- (static) `not(SearchQueryBuilder query)`: negate the `query` parameter and return it as a new query.
- `and(String field, FilterOperator type, ? value)`: get a new query that is equivalent to the original query AND the 
search condition in the parameters 
- `and(SearchQueryBuilder query)`: get a new query that is equivalent to the original query AND the `query` parameter
- `or(String field, FilterOperator type, ? value)`: get a new query that is equivalent to the original query OR the 
search condition in the parameters
- `or(SearchQueryBuilder query)`: get a new query that is equivalent to the original query OR the `query` parameter


#### Old search API
***WARNING:*** this Search system is deprecated and will be removed soon.
Please consider migrating to the new Search API.

- `searchDocuments(<schema_id>, <result_type>, <filter_type>, <sort_options_list>, <filter_option_list>, <offset>, <limit>)`

All the functions below are used in the following form

Example:
```
Documents docs = chino.search.where("test_integer").gt(123).and("test_date").eq("1994-02-04").sortAscBy("test_string").search(SCHEMA_ID);
```

- `sortAscBy`
- `sortDescBy`
- `resultType`
- `withoutIndex`
- `where`
- `search`
- `and`
- `or`
- `eq`
- `lt`
- `gt`
- `lte`
- `gte`
- `isCaseSensitive`

### Collections `io.chino.java.Collections`
API client to manage Collections of [Documents](#documents-iochinojavadocuments).
[*See full docs*](https://docs.chino.io/#blobs)

## Testing
With the SDK are included some JUnit tests, that are used for continuous integration.
If you want (for some reason) to run these tests by yourself, the best thing to do is to run them in
an account *ad hoc*.
In fact, after each test **every object on the account is deleted**, in order to preserve the correctness of tests.

If you know what you are doing, open `io.chino.java.TestConstants` in the test folder, then:
1. set the constant `TestConstants.FORCE_DELETE_ALL_ON_TESTS` to `true`.
As an alternative, you can also set `automated_test=allow` in your environment variables. 
2. set the required environment variables (customer credentials);
3. run the tests.
    
After every test, all the related object will be deleted.
(E.g. after running the `ApplicationsTest` test class, every existing *Application* on the account will be lost forever.)

Testing is made with JUnit 4. Tests are implemented for the following classes:
* `io.chino.java.Applications`
* `io.chino.java.Auth`
* `io.chino.java.Blobs`
* `io.chino.java.ChinoAPI`
* `io.chino.java.Collections`
* `io.chino.java.Consents`
* `io.chino.java.Documents`
* `io.chino.java.Groups`
* `io.chino.java.Permissions`
* `io.chino.java.Repositories`
* `io.chino.java.Schemas`
* `io.chino.java.Search`
* `io.chino.java.UserSchemas`
* `io.chino.java.Users`

Deprecated methods have been skipped; this can cause some classes to appear to be
covered for less than 100% in a coverage report.

## Support
Please report problems and ask for support using **Github issues**.

If you want to learn more about Chino.io, visit the [official site](https://chino.io) or email us at [info@chino.io](mailto:info@chino.io).
