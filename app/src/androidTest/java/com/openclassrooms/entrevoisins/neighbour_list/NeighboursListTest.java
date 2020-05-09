
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.ViewPagerActions.scrollLeft;
import static android.support.test.espresso.contrib.ViewPagerActions.scrollRight;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withSubstring;
import static android.support.test.espresso.matcher.ViewMatchers.withTagKey;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    public static com.openclassrooms.entrevoisins.neighbour_list.RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new com.openclassrooms.entrevoisins.neighbour_list.RecyclerViewMatcher(recyclerViewId);
    }

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT-1));
    }


    /**
     * We verify that when we click on a neighbour, the profile is launch
     */
    @Test
    public void myNeighbourList_clickAction_shouldLaunchNeighbourProfile() {
        // When perform a click on this neighbour
        onView(withRecyclerView(R.id.list_neighbours).atPosition(5))
                .perform(click());
        // Check that profile page was launch
        onView(withId(R.id.name1))
                .check(matches(isDisplayed()));
    }

    /**
     * We verify that's the good neighbour comparing the name on the list and on the profile
     */
    @Test
    public void myNeighbourList_VerifyNeighbourName_shouldFindTheSameName() {
        //Check the name of the first neighbour
        onView(withRecyclerView(R.id.list_neighbours).atPosition(0))
                .check(matches(hasDescendant(withText("Caroline"))));
        // When perform a click on this neighbour
        onView(withRecyclerView(R.id.list_neighbours).atPosition(0))
                        .perform(click());
        // Check that's the good Neighbour's profile
        onView(withId(R.id.name1))
                .check(matches(withText("Caroline")));
    }


    /**
     *Check if the favorite neighbour list receive the favorite neighbour
     */
    @Test
    public void myNeighbourList_favoriteList_onlyFavoriteNeighbourOnList() {
        // Swipe to the favorite neighbour list
        onView(allOf(withId(R.id.container), isDisplayed()))
                .perform(scrollRight());
        //check if it's empty
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasChildCount(0)));
        //Swipe to the neighbour list
        onView(allOf(withId(R.id.container), isDisplayed()))
                .perform(scrollLeft());
        // Go on a neighbour
        onView(withRecyclerView(R.id.list_neighbours).atPosition(1))
                .perform(click());
        // Change the neighbour on favorite neighbour
        onView(withId(R.id.favorite_button))
                .perform(click());
        // Back to the neighbour List
        onView(withId(R.id.arrow_back))
                .perform(click());
        // Swipe to the favorite neighbour list
        onView(allOf(withId(R.id.container), isDisplayed()))
                .perform(scrollRight());
        //check if it contain our favorite neighbour
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasChildCount(1)));
    }
}