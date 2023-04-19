package com.example.infinitscroll
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.infinitscroll.presentation.SearchRepositoriesActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    @get:Rule
    var mActivityRule = ActivityScenarioRule(SearchRepositoriesActivity::class.java)

    @Test
    fun checkIfLanguageIsKotlin() {
        onView(withId(R.id.pb)).check(matches(isDisplayed()))
        onView(isRoot()).perform(waitFor(5000))

        onView(
            allOf(
                withId(R.id.repo_language), isDescendantOfA(nthChildOf(withId(R.id.list), 0))
            )
        ).check(matches(withText("Language: Kotlin"))).check(matches(isDisplayed()))

    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (
                        parentMatcher.matches(parent) &&
                                parent.childCount > childPosition &&
                                parent.getChildAt(childPosition) == view
                        )
            }
        }
    }

}