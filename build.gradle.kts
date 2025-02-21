// Top-level build file where you can add configuration options common to all sub-projects/modules.
 buildscript {
     dependencies {
         classpath("com.google.gms:google-services:4.4.2")
     }
     repositories {
         google()
     }
 }
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}