[ ![Download](https://api.bintray.com/packages/amitsahni/Library/twitter/images/download.svg) ](https://bintray.com/amitsahni/Library/twitter/_latestVersion)

`Sample` Example

To set `Configuration` regarding Twitter Key , Twitter Secret

```kotlin
TwitterConfiguration.keys(TWITTER_KEY, TWITTER_SECRET)
            .isDebug(BuildConfig.DEBUG)
            .config(applicationContext)
```

`Login Sample`

```kotlin
val user = TwitterConnect.user
            if (user == null) {
                TwitterConnect.with()
                    .login(this)
                    .success {
                        TwitterConnect.with()
                            .profile(this@MainActivity)
                            .success {
                                Log.i(
                                    localClassName,
                                    displayName + " " + email + "" + phoneNumber
                                )
                                Unit
                            }.build()
                    }
                    .error {
                    }.build()
            } else {
                Log.i(localClassName + "Twitter", user.displayName + " " + user.email + "" + user.phoneNumber)
            } 
```

`OnAcitivyResult`

```
if(requestCode == TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE){
     TwitterConnect.get().onActivityResult(requestCode, resultCode, data);
 }
```

Download
--------

[ ![Download](https://api.bintray.com/packages/amitsahni/Library/twitter/images/download.svg) ](https://bintray.com/amitsahni/Library/twitter/_latestVersion)


```groovy
repositories {
    maven {
        url  "https://dl.bintray.com/amitsahni/Library" 
    }
}
```

```groovy
implementation 'com.amitsahni:twitter:0.0.1-alpha03'
```