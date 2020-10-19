/*
 * Created on Fri Oct 9 2020
 *
 * Copyright (c) 2020 - RapidMart
 */
package com.app.rapidshopper;
import android.app.Application;
import android.util.Log;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.AmplifyModelProvider;

public class RapidMartApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Amplify.addPlugin(new AWSApiPlugin());
          //  Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSDataStorePlugin(AmplifyModelProvider.getInstance()));
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("RapidMartApplication", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("RapidMartApplication", "Could not initialize Amplify", e);
        }
    }
}
