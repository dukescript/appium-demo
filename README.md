# DukeScript Appium Demo

If you'd like to do integration testing of your DukeScript mobile on actual devices, 
you can use [Appium](http://appium.io). Appium is great for local testing, but it has a lot of dependencies and is a bit difficult to install correctly.
We still like Appium a lot, because it is supported by cloud vendors like Amazon,
who provide services for running Appium tests on a huge set of actual devices in the cloud.

## Setup Android SDK

Make sure you have Android SDK installed and ANDROID_HOME is setup correctly. In order
to run this application you'll need some dependencies which are not in Maven central.
Google decided to put them somewhere in the SDK, so you should deploy them to your local repository. 
You can do so using the Maven Android SDK Deployer:


https://github.com/simpligility/maven-android-sdk-deployer

## Setup Appium

The most important step for running your integration tests locally is to setup Appium. 
Please follow the "getting started" guide to install it:


[Getting started](http://appium.io/docs/en/about-appium/getting-started/)


Please make sure that you tested if everything is alright with appium-doctor as described in the "Getting started" guide:


    appium-doctor --android


Now you can start appium simply by typing:


    appium


If you have any appium related issues when getting started (which is not unlikely),
 please refer to google to solve them. 
Fortunately appium is popular, and chances are high
someone else had the same issue and solved it. 

## Prepare device

The next step is to connect a debug enabled Android device to your computer. If you don't have one, you can also 
run the tests in an Emulator. Checkout the Appium Documentation for creating and running the emulator:


https://www.seleniumeasy.com/appium-tutorials/running-appium-tests-on-android-emulator


You may need to adjust AndroidApplicationTest.java to use the Emulator.

## Run Tests

Now you can finally clone this repository and build it from your IDE, or use the commandline to build it from the root directory:


    mvn install

The application should build, and the integration test should succeed.

## How does it work in my application?

The most important step to make it work in your own application is to enable web debugging in 
Android WebViews. This is done via:


             WebView.setWebContentsDebuggingEnabled(true);


You need to do that, before the the DukeScript Android Presenter launches the WebView containing your view.
By default your application class (AndroidMain in the Maven Archetypes) is invoked after that. 
But luckily you can [change the boot sequence and start the application from your own Activity](http://www.dukescript.com/best/practices/2015/11/20/AndroidBoot.html).
Add something like this to your AndroidMain:


```java
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView.setWebContentsDebuggingEnabled(true);

        try {
            // delegate to original activity
            startActivity(new Intent(getApplicationContext(), Class.forName("com.dukescript.presenters.Android")));
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException(ex);
        }
        finish();
    }
```

Also make sure you remove the private cosntructor if there is one, otherwise your Activity won't start. In order to make your Activity the main Activity which is started first, you need to register it in
the AndroidManifest.xml:


```xml
    <application
        android:allowBackup="true"
        android:icon="@drawable/ic_launcher"
        android:label="appium"
        android:theme="@android:style/Theme.Black.NoTitleBar.Fullscreen">
        <activity android:name="com.dukescript.demo.AndroidMain" 
                  android:configChanges="orientation|screenSize"                 
        >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            
        </activity>
        <activity android:name="com.dukescript.presenters.Android"
                  android:configChanges="orientation|screenSize">
        </activity>

        <!-- Configuration section. Defines:
           - the HTML page to load on start
           - the class that contains the main initialization method
           - name of the initialization method in the given class
        -->
        <meta-data android:name="loadPage" android:value="file:///android_asset/pages/index.html" />
        <meta-data android:name="loadClass" android:value="com.dukescript.demo.AndroidMain" />
        <meta-data android:name="invoke" android:value="main" />
    </application>
```

Make sure you remove the intent-filter part from the old main activity and put it in the new one (your Activity).

In your android client project make sure you change the android version number. You can find it in the properties section of your pom:


    <platform.version>8.0.0</platform.version>


By default a much older version is used (4.x.x), because it's the only one in Maven Central.
Make sure to use the "Maven Android SDK Deployer" first, as described above to populate your 
local repo with the required dependencies.


Then you should be ready to run your tests on Android devices with Appium. 

This little demo project is work in progress. Stay tuned for learning how to run the same tests on ios devices.


Enjoy DukeScript!
