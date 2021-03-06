package service.android.google.com.accessibility.rx.observer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import service.android.google.com.accessibility.AS;

/**
 * Created by tim on 19.03.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class EventSubscriberTest {
    private EventSubscriber eventSubscriber;

    @Mock
    private AS accessibilityService;

    @Test
    public void test_onCreate() throws Exception {
        eventSubscriber = new EventSubscriber(accessibilityService);
    }
}