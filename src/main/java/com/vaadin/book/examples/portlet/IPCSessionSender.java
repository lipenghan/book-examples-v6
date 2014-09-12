package com.vaadin.book.examples.portlet;


import javax.portlet.PortletSession;

import com.vaadin.Application;
import com.vaadin.addon.ipcforliferay.LiferayIPC;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.Window;

public class IPCSessionSender extends Application {
    private static final long serialVersionUID = 6878322382574777744L;

    @Override
	public void init() {
		Window mainWindow = new Window("IPC Session Variable Sender");
		setMainWindow(mainWindow);
		
		final Person person = new Person();
		BeanItem<Person> item = new BeanItem<Person>(person);

		Form form = new Form();
		form.setItemDataSource(item);
		mainWindow.addComponent(form);
		
		final LiferayIPC liferayipc = new LiferayIPC();
		mainWindow.addComponent(liferayipc);
		
		Button send = new Button("Send");
		form.getFooter().addComponent(send);
		send.addListener(new ClickListener() {
            private static final long serialVersionUID = -6960227803436709237L;

            @Override
            public void buttonClick(ClickEvent event) {
                PortletSession session =
                        ((PortletApplicationContext2)getContext()).
                            getPortletSession();

                // Share the object
                String key = "IPCDEMO_person";
                session.setAttribute(key, person, PortletSession.APPLICATION_SCOPE); 
                
                // Notify that it's available
                liferayipc.sendEvent("ipc_demodata_available", key);
            }
        });
	}
}
