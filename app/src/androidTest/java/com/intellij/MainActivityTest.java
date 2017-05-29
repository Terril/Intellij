package com.intellij;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;

/**
 * Created by Terril-Den on 5/29/17.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private Context instrumentationCtx;
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);
    private ItemView view;

    private ItemPresenterImpl presenter;

    private ItemModel model;
    @Before
    public void init() {
        instrumentationCtx = InstrumentationRegistry.getContext();
        this.view = mock(ItemView.class);
        this.presenter = new ItemPresenterImpl(instrumentationCtx, view);
        this.model = mock(ItemModel.class);

        new SqliteHelper(instrumentationCtx);
    }


    @Test
    public void ensureTextSavesWork() {
        // Type text and then press the button.
        onView(withId(R.id.edt_addItems))
                .perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(R.id.btn_add_item)).perform(click());

        onView(withId(R.id.recylerview_item)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, ItemViewAction.clickChildViewWithId(R.id.imbtn_delete)));

        // Check that the text was changed
    }

    public static class ItemViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }
}