[ ![Download](https://api.bintray.com/packages/amitsahni/Library/twitter/images/download.svg) ](https://bintray.com/amitsahni/Library/twitter/_latestVersion)

`Sample` Example

To set `Configuration` regarding Twitter Key , Twitter Secret

```kotlin
TwitterManager.config(this, true, TWITTER_KEY, TWITTER_SECRET)
```

`Login Sample`

```kotlin
val user = TwitterManager.user
            if (user == null) {
                login(this, {
                    profile({
                        Log.i(
                            localClassName + "Twitter",
                            user?.displayName + " " + user?.email + "" + user?.phoneNumber
                        )
                    }, {
                        it.printStackTrace()
                    })
                }, {
                    it.printStackTrace()
                })
            } else {
                Log.i(localClassName + "Twitter", user.displayName + " " + user.email + "" + user.phoneNumber)
            } 
```

`OnAcitivyResult`

```
if(requestCode == TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE){
     TwitterManager.onActivityResult(requestCode, resultCode, data!!)
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