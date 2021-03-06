package service.android.google.com.accessibility.dagger.module;

import android.app.Application;
import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * @author Created by trijckaert
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationModuleTest {

    private ApplicationModule applicationModule;

    @Mock
    private Application application;
    @Mock
    private Resources resources;

    @Before
    public void setUp() throws Exception {
        when(application.getResources()).thenReturn(resources);
        applicationModule = new ApplicationModule(application);
    }

    @Test
    public void test_provideApplication() throws Exception {
        assertThat(applicationModule.provideApplication(), is(application));
    }

    @Test
    public void test_provideCrashlyticsTree() throws Exception {
        assertNotNull(applicationModule.provideCrashlyticsTree());
    }

    @Test
    public void test_provideCrashlytics() throws Exception {
        assertNotNull(applicationModule.provideCrashlytics());
    }

    @Test
    public void test_resources() throws Exception {
        assertThat(applicationModule.resources(), is(resources));
    }
}