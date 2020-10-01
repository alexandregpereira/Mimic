# Mimic
[![](https://jitpack.io/v/alexandregpereira/mimic.svg)](https://jitpack.io/#alexandregpereira/mimic)

Android library to mock objects with random values to increase speed development / Boost your Experience.

## Implementation
Add it in your root `build.gradle` at the end of repositories.
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
Add the dependency in the app `build.gradle`.
```gradle
dependencies {
  implementation 'com.github.alexandregpereira:mimic:version'
}
```

## Example
Generate a list of objects with random values.
```kotlin
val objList: List<MyObject> = MyObject::class.java.generateList(size = 100)
```
Generate an object with random values.
```kotlin
val obj: MyObject = MyObject::class.java.generateObj()
```
## Annotation
If you only want some fields to generate random values, you can use the mimic annotations. You need to pass a boolean as a parameter to the mimic methods to avoid fields without the annotation.
```kotlin
val objList: List<MyObject> = MyObject::class.java.generateList(size = 100, mimicAnnotationOnly = true)
val obj: MyObject = MyObject::class.java.generateObj(mimicAnnotationOnly = true)
```
Use the `MimicRandom` annotation to specify the fields that you want to generate random value. The `MimicRandom` annotation only works in the primitives types and the `java.util.Date` class.
```kotlin
class MyObject {
  @MimicRandom
  var stringValue: String? = null
  
  @MimicRandom
  var intValue: Int = 0
  
  @MimicRandom
  var booleanValue: Boolean = false
  
  @MimicRandom
  var doubleValue: Double = 0.0
  
  @MimicRandom
  var floatValue: Float = 0.0f
  
  @MimicRandom
  var dateValue: Date? = null
  
  //This field will be avoided and continue null if the mimicAnnotationOnly is true
  var anotherValue: String? = null
}
```
The Mimic library has other annotations to help to mock values.
```kotlin
class MyObject {
  @MimicStringId
  var stringId: String = ""
  
  @MimicIntId
  var intId: Int = 0
  
  @MimicLongId
  var longId: Long = 0
  
  @MimicObject<MyObject2>(MyObject2::class)
  var obj: MyObject2? = null
  
  @MimicString(maxLength = 100, minWords = 2, maxWords = 20)
  var string3: String? = null
  
  @MimicInt(max = 20)
  var intValue: Int = 0
  
  @MimicDouble(max = 50.0)
  var doubleValue: Double = 0.0
  
  @MimicDate(minTime = YESTERDAY_TIME_IN_MILLIS, maxTime = TOMORROW_TIME_IN_MILLIS)
  var date: Date? = null
  
  @MimicLong(max = 100000)
  var long: Long = 0
}
```
## ProGuard
If your project uses ProGuard or some ofuscation configuration, you wii be going to need to configure the ofuscation to avoid the Mimic library classes. There are some examples below using ProGuard:
```pro
-keep class br.bano.mimic.** { *; }
```
