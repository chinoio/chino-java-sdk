package io.chino.examples.auth;

import io.chino.api.application.Application;
import io.chino.api.auth.LoggedUser;
import io.chino.api.common.ChinoApiException;
import io.chino.api.user.User;
import io.chino.api.userschema.UserSchema;
import io.chino.android.ChinoAPI;
import io.chino.examples.userschemas.UserSchemaStructureSample;
import io.chino.examples.util.Constants;
import java.io.IOException;
import java.util.HashMap;

public class AuthSamplesAndroid {
    public String USER_SCHEMA_ID = "";
    public String USER_ID = "";
    public String TOKEN = "";
    public ChinoAPI chino;

    public void testAuth() throws IOException, ChinoApiException {

        //We try to initialize ChinoAPI with a customerId and a customerKey
        chino = new ChinoAPI(Constants.HOST, Constants.CUSTOMER_ID, Constants.CUSTOMER_KEY);

        //And now we create a user and then we try to login with the credentials of the User created
        UserSchema userSchema = chino.userSchemas.create("test_description", UserSchemaStructureSample.class);
        USER_SCHEMA_ID = userSchema.getUserSchemaId();

        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("test_string", "test_string_value");
        attributes.put("test_boolean", true);
        attributes.put("test_integer", 123);
        attributes.put("test_date", "1993-09-08");
        attributes.put("test_float", 12.4);
        User user = chino.users.create(Constants.USERNAME, Constants.PASSWORD, attributes, USER_SCHEMA_ID);
        USER_ID = user.getUserId();

        Application application = chino.applications.create("ApplicationTest1", "password", "http://127.0.0.1/");

        LoggedUser loggedUser = chino.auth.login(Constants.USERNAME, Constants.PASSWORD, application.getAppId(), application.getAppSecret());
        chino.initClient(loggedUser.getAccessToken(), Constants.HOST);
        System.out.println(loggedUser);

        User user2 = chino.auth.checkUserStatus();
        System.out.println(user2);

        /*LoggedUser loggedUser = chino.auth.loginUser(Constants.USERNAME, Constants.PASSWORD, Constants.CUSTOMER_ID);
        System.out.println(TOKEN = loggedUser.getAccessToken());
        chino.initClient(TOKEN, Constants.HOST);

        //Now we logout
        System.out.println(chino.auth.logoutUser());
        chino.initClient(Constants.CUSTOMER_ID, Constants.CUSTOMER_KEY, Constants.HOST);

        //Now we try to create a new ChinoAPI and login as a user
        chino = new ChinoAPI(Constants.HOST);

        loggedUser = chino.auth.loginUser(Constants.USERNAME, Constants.PASSWORD, Constants.CUSTOMER_ID);
        System.out.println(TOKEN = loggedUser.getAccessToken());
        chino.initClient(TOKEN, Constants.HOST);

        //And we logout again
        System.out.println(chino.auth.logoutUser());
        chino.initClient(Constants.CUSTOMER_ID, Constants.CUSTOMER_KEY, Constants.HOST);*/
    }
}
