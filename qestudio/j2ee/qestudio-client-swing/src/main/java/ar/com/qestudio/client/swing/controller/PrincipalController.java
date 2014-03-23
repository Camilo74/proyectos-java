package ar.com.qestudio.client.swing.controller;

import javax.swing.JInternalFrame;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ar.com.qestudio.client.socket.api.APIService;
import ar.com.qestudio.client.swing.view.PrincipalView;
import ar.com.qestudio.client.swing.view.QueryView;
import ar.com.qestudio.client.swing.view.components.registries.Tabbable;

public class PrincipalController implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private PrincipalView principalView;
    private FileManagerController fileManagerController;
    
    private APIService client;

    public void init() {
        principalView.getjMenuItem1().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webMenuItem1ActionPerformed(evt);
            }
        });
        principalView.getjMenuItem2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webMenuItem2ActionPerformed(evt);
            }
        });
        principalView.getjMenuItem3().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webMenuItem3ActionPerformed(evt);
            }
        });
        show();
    }

    private void webMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        QueryController queryController = (QueryController) applicationContext.getBean("query.controller", client.findAllRegistries());
        principalView.getjDesktopPane1().add(queryController.getQueryView());
        queryController.show();
    }

    private void webMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        JInternalFrame internalFrame = principalView.getjDesktopPane1().getSelectedFrame();
        internalFrame.dispose();
    }

    private void webMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
    	JInternalFrame internalFrame = principalView.getjDesktopPane1().getSelectedFrame();
    	Tabbable tabbable = (Tabbable) ((QueryView)internalFrame).getjTabbedPane1().getSelectedComponent();
    	fileManagerController.refresh(tabbable.getSelected());
    	fileManagerController.show();
    }

    private void show() {
        principalView.setVisible(true);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //-------------------------------
    public PrincipalView getPrincipalView() {
        return principalView;
    }

    public void setPrincipalView(PrincipalView principalView) {
        this.principalView = principalView;
    }

    public FileManagerController getFileManagerController() {
        return fileManagerController;
    }

    public void setFileManagerController(FileManagerController fileManagerController) {
        this.fileManagerController = fileManagerController;
    }

    public APIService getClient() {
        return client;
    }

    public void setClient(APIService client) {
        this.client = client;
    }
}