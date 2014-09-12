package com.vaadin.book.examples.addons;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.EntityManager;

import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.addon.calendar.ui.CalendarDateRange;
import com.vaadin.addon.calendar.ui.ContainerEventProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public class CalendarExample extends CustomComponent implements
		BookExampleBundle {

	private static final long serialVersionUID = -3205020480634478985L;
	public static final int DATE_YEAR = 2014;
	String context;

	public void init(String context) {
		VerticalLayout layout = new VerticalLayout();

		if ("basic".equals(context))
			basic(layout);
		else if ("monthlyview".equals(context))
			monthlyview(layout);
		else if ("contextmenu".equals(context))
			contextmenu(layout);
		else if ("beanitemcontainer".equals(context))
			beanitemcontainer(layout);
		else if ("jpacontainer".equals(context))
			jpacontainer(layout);

		setCompositionRoot(layout);
	}

	public static final String basicDescription = "<h1>Basic Use of Calendar</h1>";

	void basic(Layout layout) {
		// BEGIN-EXAMPLE: calendar.basic
		// Create the calendar
		Calendar calendar = new Calendar("My Calendar");
		calendar.setWidth("600px"); // Undefined by default
		calendar.setHeight("300px"); // Undefined by default

		// Use US English for date/time representation
		calendar.setLocale(new Locale("en", "US"));

		// Set daily time range
		calendar.setVisibleHoursOfDay(9, 17);

		// Add an event from now to plus one hour
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		end.add(java.util.Calendar.HOUR, 1);
		calendar.addEvent(new BasicEvent("Calendar study",
				"Learning how to use Vaadin Calendar", start.getTime(), end
						.getTime()));

		layout.addComponent(calendar);
		// END-EXAMPLE: calendar.basic
	}

	public static final String monthlyviewDescription = "<h1>Monthly View</h1>";

	void monthlyview(Layout layout) {
		// BEGIN-EXAMPLE: calendar.monthlyview
		// Create the calendar
		Calendar calendar = new Calendar("My Calendar");
		calendar.setWidth("600px"); // Undefined by default
		calendar.setHeight("400px"); // Undefined by default

		// Use US English for date/time representation
		calendar.setLocale(new Locale("en", "US"));

		// Set start date to first date in this month
		GregorianCalendar startDate = new GregorianCalendar();
		startDate.set(java.util.Calendar.MONTH, 1);
		startDate.set(java.util.Calendar.DATE, 1);
		calendar.setStartDate(startDate.getTime());

		// Set end date to last day of this month
		GregorianCalendar endDate = new GregorianCalendar();
		endDate.set(java.util.Calendar.DATE, 1);
		endDate.roll(java.util.Calendar.DATE, -1);
		calendar.setEndDate(endDate.getTime());

		// Add a short event
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		end.add(java.util.Calendar.HOUR, 2);
		calendar.addEvent(new BasicEvent("Calendar study",
				"Learning how to use Vaadin Calendar", start.getTime(), end
						.getTime()));

		// Add an all-day event
		GregorianCalendar daystart = new GregorianCalendar();
		GregorianCalendar dayend = new GregorianCalendar();
		daystart.set(java.util.Calendar.HOUR_OF_DAY, 0);
		dayend.set(java.util.Calendar.HOUR_OF_DAY, 23);
		BasicEvent dayEvent = new BasicEvent("All-day Long", "This is the Day",
				daystart.getTime(), dayend.getTime());
		dayEvent.setAllDay(true);
		calendar.addEvent(dayEvent);

		// Add an all-week event
		GregorianCalendar weekstart = new GregorianCalendar();
		GregorianCalendar weekend = new GregorianCalendar();
		weekstart.setFirstDayOfWeek(java.util.Calendar.SUNDAY);
		weekstart.set(java.util.Calendar.HOUR_OF_DAY, 0);
		weekstart
				.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SUNDAY);
		weekend.set(java.util.Calendar.HOUR_OF_DAY, 23);
		weekend.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SATURDAY);
		BasicEvent weekEvent = new BasicEvent("A long week",
				"This is a long long week", weekstart.getTime(),
				weekend.getTime());
		// weekEvent.setAllDay(true);
		calendar.addEvent(weekEvent);

		layout.addComponent(calendar);
		// END-EXAMPLE: calendar.monthlyview
	}

	public static final String contextmenuDescription = "<h1>Context Menu</h1>";

	void contextmenu(Layout layout) {
		// BEGIN-EXAMPLE: calendar.contextmenu
		// Create the calendar
		Calendar calendar = new Calendar("My Contextual Calendar");
		calendar.setWidth("600px"); // Undefined by default
		calendar.setHeight("300px"); // Undefined by default

		// Use US English for date/time representation
		calendar.setLocale(new Locale("en", "US"));
		calendar.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Add an event from now to plus one hour
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		end.add(java.util.Calendar.HOUR, 1);
		calendar.addEvent(new BasicEvent("Calendar study",
				"Learning how to use Vaadin Calendar", start.getTime(), end
						.getTime()));

		Action.Handler actionHandler = new Action.Handler() {
			private static final long serialVersionUID = 2122177837743583633L;

			Action addEventAction = new Action("Add Event");
			Action deleteEventAction = new Action("Delete Event");

			@Override
			public Action[] getActions(Object target, Object sender) {
				// The target should be a CalendarDateRage for the
				// entire day from midnight to midnight.
				if (!(target instanceof CalendarDateRange))
					return null;
				CalendarDateRange dateRange = (CalendarDateRange) target;

				// The sender is the Calendar object
				if (!(sender instanceof Calendar))
					return null;
				Calendar calendar = (Calendar) sender;

				// List all the events on the requested day
				List<CalendarEvent> events = calendar.getEvents(
						dateRange.getStart(), dateRange.getEnd());

				// You can have some logic here, using the date
				// information.
				if (events.size() == 0)
					return new Action[] { addEventAction };
				else
					return new Action[] { addEventAction, deleteEventAction };
			}

			@Override
			public void handleAction(Action action, Object sender, Object target) {
				// The sender is the Calendar object
				Calendar calendar = (Calendar) sender;

				if (action == addEventAction) {
					// Check that the click was not done on an event
					if (target instanceof Date) {
						Date date = (Date) target;
						// Add an event from now to plus one hour
						GregorianCalendar start = new GregorianCalendar();
						start.setTime(date);
						GregorianCalendar end = new GregorianCalendar();
						end.setTime(date);
						end.add(java.util.Calendar.HOUR, 1);
						calendar.addEvent(new BasicEvent("Calendar study",
								"Learning how to use Vaadin Calendar", start
										.getTime(), end.getTime()));
					} else
						getWindow().showNotification("Can't add on an event");
				} else if (action == deleteEventAction) {
					// Check if the action was clicked on top of an event
					if (target instanceof CalendarEvent) {
						CalendarEvent event = (CalendarEvent) target;
						calendar.removeEvent(event);
					} else
						getWindow().showNotification("No event to delete");
				}
			}
		};
		calendar.addActionHandler(actionHandler);

		layout.addComponent(calendar);
		// END-EXAMPLE: calendar.contextmenu
	}

	public static final String navigationDescription = "<h1>Calendar Navigation</h1>"
			+ "<p>Vaadin Calendar has no built-in support for navigation as it can be implemented in so many different ways. "
			+ "This example demonstrates one implementation of navigation.</p>";

	void navigation(Layout layout) {
		// BEGIN-EXAMPLE: calendar.navigation
		// Create the calendar
		Calendar calendar = new Calendar("My Calendar");
		calendar.setWidth("300px"); // Undefined by default
		calendar.setHeight("300px"); // Undefined by default

		// Use US English for date/time representation
		calendar.setLocale(new Locale("en", "US"));

		// Set start date to first date in this month
		GregorianCalendar startDate = new GregorianCalendar();
		startDate.set(java.util.Calendar.MONTH, 1);
		startDate.set(java.util.Calendar.DATE, 1);
		calendar.setStartDate(startDate.getTime());

		// Set end date to last day of the month
		GregorianCalendar endDate = new GregorianCalendar();
		endDate.set(java.util.Calendar.DATE, 1);
		endDate.roll(java.util.Calendar.DATE, -1);
		calendar.setEndDate(endDate.getTime());

		// Add a short event
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		end.add(java.util.Calendar.HOUR, 2);
		calendar.addEvent(new BasicEvent("Calendar study",
				"Learning how to use Vaadin Calendar", start.getTime(), end
						.getTime()));

		layout.addComponent(calendar);
		// END-EXAMPLE: calendar.navigation
	}

	public static final String beanitemcontainerDescription = "<h1>Binding to a BeanItemContainer</h1>"
			+ "<p>You can bind calendar to a BeanItemContainer with properties required for events.</p>"
			+ "<ul>"
			+ "  <li>You must call <i>sort()</i> with the start date/time property after you add any events!</li>"
			+ "</ul>";

	void beanitemcontainer(Layout layout) {
		// BEGIN-EXAMPLE: calendar.beanitemcontainer
		// Create the calendar
		Calendar calendar = new Calendar("Bound Calendar");
		calendar.setWidth("600px"); // Undefined by default
		calendar.setHeight("400px"); // Undefined by default

		// Use US English for date/time representation
		calendar.setLocale(new Locale("en", "US"));

		// Use the weekly view
		calendar.setStartDate(new GregorianCalendar(DATE_YEAR, 1, 12).getTime());
		calendar.setEndDate(new GregorianCalendar(DATE_YEAR, 1, 18).getTime());

		// Use a container of built-in BasicEvents
		final BeanItemContainer<BasicEvent> container = new BeanItemContainer<BasicEvent>(
				BasicEvent.class);

		// Create a meeting in the container
		container.addBean(new BasicEvent("The Event", "Single Event",
				new GregorianCalendar(DATE_YEAR, 1, 14, 12, 00).getTime(),
				new GregorianCalendar(DATE_YEAR, 1, 14, 14, 00).getTime()));

		calendar.setContainerDataSource(container, "caption", "description",
				"start", "end", "styleName");

		// We can also add events to the container through the calendar
		BasicEvent event = new BasicEvent("Wednesday Wonder",
				"Wonderful Event",
				new GregorianCalendar(DATE_YEAR, 1, 15, 16, 00).getTime(),
				new GregorianCalendar(DATE_YEAR, 1, 15, 19, 00).getTime());
		event.setStyleName("blue");
		calendar.addEvent(event);

		// The container MUST be ordered by the start time. You
		// have to sort the BIC every time after you have added items.
		container.sort(new Object[] { "start" }, new boolean[] { true });

		layout.addComponent(calendar);
		// END-EXAMPLE: calendar.beanitemcontainer
	}

	public static final String beanitemcontainer2Description = "<h1>Binding to a BeanItemContainer</h1>"
			+ "<p>You can bind calendar to a BeanItemContainer with properties required for events.</p>"
			+ "<ul>"
			+ "  <li>You must call <i>sort()</i> with the start date/time property after you add any events!</li>"
			+ "</ul>";

	void beanitemcontainer2(Layout layout) {
		// BEGIN-EXAMPLE: calendar.beanitemcontainer2
		// Create the calendar
		Calendar calendar = new Calendar("Bound Calendar");
		calendar.setWidth("600px"); // Undefined by default
		calendar.setHeight("400px"); // Undefined by default

		// Use US English for date/time representation
		calendar.setLocale(new Locale("en", "US"));

		// Set month view
		GregorianCalendar startDate = new GregorianCalendar(DATE_YEAR, 1, 1);
		calendar.setStartDate(startDate.getTime());
		GregorianCalendar endDate = new GregorianCalendar(DATE_YEAR, 1, 29);
		calendar.setEndDate(endDate.getTime());

		final BeanItemContainer<MyCalendarEvent> container = new BeanItemContainer<MyCalendarEvent>(
				MyCalendarEvent.class);

		// Meeting every Monday morning
		GregorianCalendar startTime = new GregorianCalendar(DATE_YEAR, 1, 1, 9, 0);
		startTime
				.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
		startTime.set(java.util.Calendar.DAY_OF_WEEK_IN_MONTH, 1);
		for (int i = 0; i < 5; i++) {
			GregorianCalendar endTime = (GregorianCalendar) startTime.clone();
			endTime.add(java.util.Calendar.HOUR, 1);
			MyCalendarEvent event = new MyCalendarEvent("Monday Meeting",
					"The same meeting every Monday", startTime.getTime(),
					endTime.getTime(), "red");
			container.addBean(event);
			startTime.add(java.util.Calendar.WEEK_OF_YEAR, 1);
		}

		calendar.setContainerDataSource(container, "caption", "description",
				"startDate", "endDate", "styleName");

		// Now we can add events to the container through the calendar
		BasicEvent event = new BasicEvent("Wednesday Wonder",
				"Wonderful Event",
				new GregorianCalendar(DATE_YEAR, 1, 15, 12, 00).getTime(),
				new GregorianCalendar(DATE_YEAR, 1, 15, 14, 00).getTime());
		event.setStyleName("blue");
		calendar.addEvent(event);

		// The container must be ordered by the start time. We
		// have to sort the BIC every time after we add items.
		container.sort(new Object[] { "start" }, new boolean[] { true });

		layout.addComponent(calendar);
		// END-EXAMPLE: calendar.beanitemcontainer2
	}

	public static final String jpacontainerDescription = "<h1>JPAContainer Binding</h1>"
			+ "<p>Binding to a JPAContainer is really easy.</p>"
			+ "<ul>"
			+ "  <li>You must set up <i>sort()</i> with the start date/time property!</li>"
			+ "</ul>";

	void jpacontainer(Layout layout) {
		// BEGIN-EXAMPLE: calendar.jpacontainer
		// Create the calendar
		Calendar calendar = new Calendar("Bound Calendar");
		calendar.setWidth("600px"); // Undefined by default
		calendar.setHeight("400px"); // Undefined by default

		// Use US English for date/time representation
		calendar.setLocale(new Locale("en", "US"));

		// Set month view
		GregorianCalendar startDate = new GregorianCalendar(DATE_YEAR, 1, 1);
		calendar.setStartDate(startDate.getTime());
		GregorianCalendar endDate = new GregorianCalendar(DATE_YEAR, 1, 29);
		calendar.setEndDate(endDate.getTime());

		// Generate example data if none already exists
		EntityManager em = JPAContainerFactory
				.createEntityManagerForPersistenceUnit("book-examples");
		em.getTransaction().begin();
		em.createQuery("DELETE FROM MyCalendarEvent mce").executeUpdate();

		// Meeting every Monday morning
		GregorianCalendar startTime = new GregorianCalendar(DATE_YEAR, 1, 1, 9,
				0);
		startTime
				.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
		startTime.set(java.util.Calendar.DAY_OF_WEEK_IN_MONTH, 1);
		for (int i = 0; i < 5; i++) {
			GregorianCalendar endTime = (GregorianCalendar) startTime.clone();
			endTime.add(java.util.Calendar.HOUR, 1);
			MyCalendarEvent event = new MyCalendarEvent("Monday Meeting",
					"The same meeting every Monday", startTime.getTime(),
					endTime.getTime(), "red");
			em.persist(event);
			startTime.add(java.util.Calendar.WEEK_OF_YEAR, 1);
		}
		em.getTransaction().commit();

		// Create a JPAContainer
		final JPAContainer<MyCalendarEvent> container = JPAContainerFactory
				.make(MyCalendarEvent.class, "book-examples");

		// The container must be ordered by start date. For JPAContainer
		// we can just set up sorting once and it will stay ordered.
		container.sort(new String[] { "startDate" }, new boolean[] { true });

		// Customize the event provider for adding events
		// as entities
		ContainerEventProvider cep = new ContainerEventProvider(container) {
			private static final long serialVersionUID = 4985603843578816634L;

			@Override
			public void addEvent(CalendarEvent event) {
				MyCalendarEvent entity = new MyCalendarEvent(
						event.getCaption(), event.getDescription(),
						event.getStart(), event.getEnd(), event.getStyleName());
				container.addEntity(entity);
			}
		};
		cep.setStartDateProperty("startDate");
		cep.setEndDateProperty("endDate");

		// Set the container as the data source
		calendar.setEventProvider(cep);

		// Now we can add events to the database through the calendar
		BasicEvent event = new BasicEvent("The Event", "Single Event",
				new GregorianCalendar(DATE_YEAR, 1, 15, 12, 00).getTime(),
				new GregorianCalendar(DATE_YEAR, 1, 15, 14, 00).getTime());
		event.setStyleName("blue");
		calendar.addEvent(event);

		layout.addComponent(calendar);
		// END-EXAMPLE: calendar.jpacontainer
	}
}
