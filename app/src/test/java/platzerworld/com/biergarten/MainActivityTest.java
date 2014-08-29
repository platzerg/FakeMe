package platzerworld.com.biergarten;

import android.app.Activity;

import com.platzerworld.fakefb.FakeFBActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;


/**
 * Created by vandekr on 11/02/14.
 */
//@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testActivityFound() {
        Activity activity = Robolectric.buildActivity(FakeFBActivity.class).create().get();

        Assert.assertNotNull(activity);
    }
}