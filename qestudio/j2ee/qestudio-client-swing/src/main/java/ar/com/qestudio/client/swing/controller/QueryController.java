package ar.com.qestudio.client.swing.controller;

import java.util.List;


import org.apache.log4j.Logger;

import ar.com.qestudio.client.swing.view.QueryView;
import ar.com.qestudio.client.swing.view.components.registries.DefaultList;
import ar.com.qestudio.core.model.Registry;
import javax.swing.JButton;

public class QueryController {

    private final static Logger logger = Logger.getLogger(QueryController.class);
    private QueryView queryView;
    private List<Registry> registries;

    public QueryController(List<Registry> registries) {
        this.registries = registries;
    }

    public void init() {

        queryView.setTitle("Consulta 1");
        queryView.setBounds(50, 20, 460, 210);
        queryView.setResizable(true);

        queryView.getjTabbedPane1().addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPane1FocusGained(evt);
            }
        });

        queryView.getjTabbedPane1().addTab("Registros", new DefaultList(registries));
        queryView.getjTabbedPane1().addTab("Calendario", new JButton("sss"));

//        ((javax.swing.plaf.basic.BasicInternalFrameUI)queryView.getUI()).setNorthPane(null);
        
    }

    private void jTabbedPane1FocusGained(java.awt.event.FocusEvent evt) {
        System.out.println("##############");
    }

    // ----------------------------------
    public QueryView getQueryView() {
        return queryView;
    }

    public void setQueryView(QueryView queryView) {
        this.queryView = queryView;
    }

    public List<Registry> getRegistries() {
        return registries;
    }

    public void setRegistries(List<Registry> registries) {
        this.registries = registries;
    }

    public void show() {
        try {
            queryView.setVisible(true);
        	queryView.setMaximum(true);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}