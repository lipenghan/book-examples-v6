package com.vaadin.book.examples.portlet;

import javax.portlet.PortletSession;

import com.vaadin.Application;
import com.vaadin.addon.ipcforliferay.LiferayIPC;
import com.vaadin.addon.ipcforliferay.event.LiferayIPCEvent;
import com.vaadin.addon.ipcforliferay.event.LiferayIPCEventListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class IPCSessionReceiver extends Application {
    private static final long serialVersionUID = 6878322382574777744L;
    
    Label label;

    @Override
    public void init() {
        Window mainWindow = new Window("IPC Session Variable Receiver");
        setMainWindow(mainWindow);
        
        final Form form = new Form();
        form.setCaption("The target form");
        form.setReadOnly(true);
        mainWindow.addComponent(form);
        
        final LiferayIPC liferayipc = new LiferayIPC();
        mainWindow.addComponent(liferayipc);

        liferayipc.addListener("IPCDEMO_person", new LiferayIPCEventListener() {
            @Override
            public void eventReceived(LiferayIPCEvent event) {
                PortletSession session =
                        ((PortletApplicationContext2)getContext()).
                            getPortletSession();

                String key = event.getData();
                
                Person person = (Person) session.getAttribute(key);
                BeanItem<Person> item = new BeanItem<Person> (person);
                form.setItemDataSource(item);
            }
        });
    }
}
