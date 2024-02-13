package com.iessanalberto.dam2.proyectodi4

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify

class PrincipalAmplify : Application() {
    override fun onCreate() {
        super.onCreate()
        try{
            try{
                Amplify.addPlugin(AWSCognitoAuthPlugin())
                Log.i("MyAmplifyApp","Initialized Cognito")
            }catch (error: AmplifyException){
                Log.e("MyAmplifyApp", "Could not initialize Cognito", error)
            }
            Amplify.configure(applicationContext)
            Log.i("MyAmplifyApp","Initialized Amplify")
        }catch (error: AmplifyException){
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }

    }
}