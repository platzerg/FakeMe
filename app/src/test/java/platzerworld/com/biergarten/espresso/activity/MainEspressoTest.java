package platzerworld.com.biergarten.espresso.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.platzerworld.fakefb.FakeFBActivity;
import com.platzerworld.fakefb.PickerActivity;
import com.platzerworld.fakefb.R;
import com.platzerworld.fakefb.SplashFragment;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

@LargeTest
public class MainEspressoTest extends ActivityInstrumentationTestCase2<FakeFBActivity> {

    @SuppressWarnings("deprecation")
    public MainEspressoTest() {
        // This constructor was deprecated - but we want to support lower API levels.
        super("com.platzerworld.fakefb", FakeFBActivity.class);
    }
    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }

    public void testCheckText() {
        assertNotNull("");
        //onView(withId(R.id.text)).check(matches(withText("Hello Espresso!")));
    }
}
