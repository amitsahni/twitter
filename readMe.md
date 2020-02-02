[ ![Download](https://api.bintray.com/packages/amitsahni/Library/twitter/images/download.svg) ](https://bintray.com/amitsahni/Library/twitter/_latestVersion)

`Sample` Example

To set `Configuration` regarding Twitter Key , Twitter Secret

```kotlin
config(this, true, TWITTER_KEY, TWITTER_SECRET)
```

`Login Sample`

```kotlin
val user = twitterUser
            if (user == null) {
                login(this, {
                    twitterProfile { user, exception ->
                                            Log.d(localClassName + "Twitter", user?.displayName + " " + user?.email + "" + user?.phoneNumber)
                                            exception?.printStackTrace()
                                            twitterToken {
                                                Log.d("twitterToken", it)
                                            }
                                        }
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
     onTwitterActivityResult(requestCode, resultCode, data!!)
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
implementation 'com.amitsahni:twitter:0.0.1-alpha04'
```