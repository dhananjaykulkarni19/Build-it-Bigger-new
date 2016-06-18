package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.test.ActivityUnitTestCase;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.Assert;


public class TestJokeTellerClass extends ActivityUnitTestCase<com.udacity.gradle.builditbigger.MainActivity>{

    public TestJokeTellerClass(Class<com.udacity.gradle.builditbigger.MainActivity> activityClass) {
        super(activityClass);
    }

    @SmallTest
    public void Joker(){
       try{
           Context context = new MockContext();
           JokeTellerTask task = new JokeTellerTask(context, new JokeTellerTask.JokeListener() {
               @Override
               public void onJokeLoaded(String joke) {
                   Assert.assertFalse(joke.isEmpty());
               }
           });
           task.execute(0);
       }catch(Exception e){
            fail("Joke test failed");
       }
   }
}
