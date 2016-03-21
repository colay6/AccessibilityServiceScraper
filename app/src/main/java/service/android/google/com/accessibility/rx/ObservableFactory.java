package service.android.google.com.accessibility.rx;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import service.android.google.com.accessibility.extractor.EventExtractor;
import service.android.google.com.accessibility.model.ChatEvent;
import service.android.google.com.accessibility.model.Event;
import service.android.google.com.accessibility.scraper.WindowRipper;
import service.android.google.com.accessibility.util.function.FunctionFactory;
import service.android.google.com.accessibility.util.function.event.filters.FilterAccessibilityEventsFunction;
import service.android.google.com.accessibility.util.function.event.filters.FilterNullChatEventsFunction;
import service.android.google.com.accessibility.util.function.event.filters.FilterWindowInfoEventFunction;
import service.android.google.com.accessibility.util.function.event.mappers.MapAccessibilityEventToEventFunction;
import service.android.google.com.accessibility.util.function.event.mappers.MapAccessibilityNodeInfoToChatEvent;

public class ObservableFactory {

    private final FunctionFactory functionFactory;
    private final ObserverFactory observerFactory;

    public ObservableFactory(final FunctionFactory functionFactory,
                             final ObserverFactory observerFactory) {
        this.functionFactory = functionFactory;
        this.observerFactory = observerFactory;
    }

    public PublishSubject<AccessibilityEvent> createPublishSubjectOfAccessibilityTextEvents(final EventExtractor eventExtractor,
                                                                                            final Subscriber<Event> eventSubscriber) {
        PublishSubject<AccessibilityEvent> AETextPublishSubject = PublishSubject.create();

        AETextPublishSubject
                .map(functionFactory.getMapAccessibilityEventToEventFunction(eventExtractor))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventSubscriber);

        return AETextPublishSubject;
    }

    public PublishSubject<AccessibilityEvent> createPublishSubjectOfAccessibilityEvents(final EventExtractor eventExtractor,
                                                                                        final Subscriber<Event> eventSubscriber) {
        PublishSubject<AccessibilityEvent> AEPublishSubject = PublishSubject.create();

        MapAccessibilityEventToEventFunction mapFunction = functionFactory.getMapAccessibilityEventToEventFunction(eventExtractor);
        FilterAccessibilityEventsFunction predicate = functionFactory.filterAccessibilityEventFunction();

        AEPublishSubject
                .map(mapFunction)
                .filter(predicate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventSubscriber);

        return AEPublishSubject;
    }

    public PublishSubject<AccessibilityNodeInfo> createPublishSubjectOfAccessibilityNodeInfo(final WindowRipper windowRipper,
                                                                                             final Subscriber<ChatEvent> chatEventSubscriber) {
        PublishSubject<AccessibilityNodeInfo> windowInfoEventPublishSubject = PublishSubject.create();

        FilterWindowInfoEventFunction predicate = functionFactory.filterWindowInfoEventFunction(windowRipper);
        MapAccessibilityNodeInfoToChatEvent mapFunction = functionFactory.getMapAccessibilityNodeInfoToChatEvent(windowRipper);
        FilterNullChatEventsFunction predicateNullValues = functionFactory.filterNullChatEventsFunction();

        windowInfoEventPublishSubject
                .filter(predicate)
                .map(mapFunction)
                .filter(predicateNullValues)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chatEventSubscriber);

        return windowInfoEventPublishSubject;
    }
}