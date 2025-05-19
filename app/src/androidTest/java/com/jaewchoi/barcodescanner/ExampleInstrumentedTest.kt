package com.jaewchoi.barcodescanner

import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jaewchoi.barcodescanner", appContext.packageName)
    }

//    @Test
//    fun mainAcitivty_displayCorrectText() {
//        ActivityScenario.launch(
//            MainActivity::class.java
//        )
//
//
//        onView(withId(R.id.textView))
//            .check(matches(
//                withText("Hello World")
//            )
//        )
//
//
//    }

    @Test
    fun mainActivity_displayFragment() {
        ActivityScenario.launch(
            MainActivity::class.java
        )

        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()))
    }



}