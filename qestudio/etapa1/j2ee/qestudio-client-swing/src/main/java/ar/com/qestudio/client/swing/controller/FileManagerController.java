package ar.com.qestudio.client.swing.controller;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.table.AbstractTableModel;

import ar.com.qestudio.client.socket.api.APIService;
import ar.com.qestudio.client.swing.view.FileManagerView;
import ar.com.qestudio.core.model.Attached;
import ar.com.qestudio.core.model.Registry;

public class FileManagerController {

	private APIService client;
	private FileManagerView fileManagerView;
	private Registry registry;
	
	public void init(){
		fileManagerView.setResizable ( true );
		fileManagerView.setModal ( true );
		fileManagerView.setBounds(50, 20, 460, 210);
		fileManagerView.setDefaultCloseOperation ( JDialog.DISPOSE_ON_CLOSE );
		fileManagerView.getjTable1().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
	}
	
	private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			Attached attached = ((JTableModelFileManagerTransient)fileManagerView.getjTable1().getModel()).getIdByRow(fileManagerView.getjTable1().getSelectedRow());
			client.openFile(registry, attached);
		}
	}

	public void show() {
		fileManagerView.setVisible(true);
	}

	public void refresh(Registry registry) {
		this.registry = registry;
		fileManagerView.getjTable1().setModel(new JTableModelFileManagerTransient(registry.getAttachments()));
	}
	
	public FileManagerView getFileManagerView() {
		return fileManagerView;
	}

	public void setFileManagerView(FileManagerView fileManagerView) {
		this.fileManagerView = fileManagerView;
	}

    public APIService getClient() {
		return client;
	}

	public void setClient(APIService client) {
		this.client = client;
	}

	public Registry getRegistry() {
		return registry;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	public class JTableModelFileManagerTransient extends AbstractTableModel {

        private static final long serialVersionUID = -1684575201953195767L;
        
        // Two arrays used for the table data
        private String[] columnNames = {"ID", "NOMBRE"};
        private List<Attached> files;
        private Object[][] data;

        public JTableModelFileManagerTransient(List<Attached> files) {
            this.files = files;
            data = new Object[files.size()][2];
            for (int i = 0; i < files.size(); i++) {
                data[i] = new Object[]{files.get(i).getId(), files.get(i).getName()};
            }
        }

        public Attached getIdByRow(Integer row) {
            return files.get(row);
        }

        @Override
        public int getRowCount() {
            return files.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int row, int column) {
            return data[row][column];
        }

        // Used by the JTable object to set the column names
        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        // Used by the JTable object to render different
        // functionality based on the data type
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            if (column == 0 || column == 1) {
                return false;
            } else {
                return true;
            }
        }
    }
	
}