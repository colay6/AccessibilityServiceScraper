package service.android.google.com.accessibility.dagger.module;

import android.content.res.Resources;

import com.github.pwittchen.prefser.library.Prefser;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import nl.nl2312.rxcupboard.RxDatabase;
import service.android.google.com.accessibility.AS;
import service.android.google.com.accessibility.extractor.EventExtractor;
import service.android.google.com.accessibility.rx.ObservableFactory;
import service.android.google.com.accessibility.rx.ObserverFactory;
import service.android.google.com.accessibility.rx.util.SchedulerFactory;
import service.android.google.com.accessibility.scraper.WindowRipper;
import service.android.google.com.accessibility.util.action.ActionFactory;
import service.android.google.com.accessibility.util.function.FunctionFactory;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author Created by trijckaert
 */
@RunWith(MockitoJUnitRunner.class)
public class AccessibilityModuleTest {

    private AccessibilityModule accessibilityModule;

    @Mock
    private AS accessibilityService;
    @Mock
    private ObservableFactory observableFactory;
    @Mock
    private ObserverFactory observerFactory;
    @Mock
    private EventExtractor eventExtractor;
    @Mock
    private WindowRipper windowRipper;
    @Mock
    private FunctionFactory functionFactory;
    @Mock
    private SchedulerFactory schedulerFactory;
    @Mock
    private ActionFactory actionFactory;
    @Mock
    private RxDatabase rxDatabase;
    @Mock
    private Prefser prefser;
    @Mock
    private Resources resources;
    @Mock
    private android.content.SharedPreferences sharedPrefs;

    @Before
    public void setUp() throws Exception {
        when(prefser.getPreferences()).thenReturn(sharedPrefs);
        accessibilityModule = new AccessibilityModule(accessibilityService);
    }

    @Test
    public void test_accessibilityService() throws Exception {
        assertThat(accessibilityModule.accessibilityService(), is(accessibilityService));
    }

    @Test
    @Ignore
    public void test_accessibilityServiceController() throws Exception {
        when(prefser.get(anyString(), eq(Boolean.class), Matchers.<Boolean>any())).thenReturn(true);
        assertNotNull(
                accessibilityModule.accessibilityServiceController(
                        observableFactory,
                        prefser,
                        resources
                )
        );
    }

    @Test
    public void test_observableFactory() throws Exception {
        assertNotNull(
                accessibilityModule.observableFactory(
                        functionFactory,
                        actionFactory,
                        observerFactory,
                        schedulerFactory,
                        eventExtractor,
                        windowRipper,
                        rxDatabase,
                        resources,
                        prefser
                )
        );
    }

    @Test
    public void test_functionFactory() throws Exception {
        assertNotNull(
                accessibilityModule.functionFactory()
        );
    }

    @Test
    public void test_eventExtractor() throws Exception {
        assertNotNull(
                accessibilityModule.eventExtractor(prefser, resources)
        );
    }

    @Test
    public void test_windowRipper() throws Exception {
        assertNotNull(
                accessibilityModule.windowRipper()
        );
    }

    @Test
    public void test_observerFactory() throws Exception {
        assertNotNull(
                accessibilityModule.observerFactory()
        );
    }

    @Test
    public void test_schedulerFactory() throws Exception {
        assertNotNull(
                accessibilityModule.schedulerFactory()
        );
    }

    @Test
    public void test_actionFactory() throws Exception {
        assertNotNull(
                accessibilityModule.actionFactory()
        );

    }
}