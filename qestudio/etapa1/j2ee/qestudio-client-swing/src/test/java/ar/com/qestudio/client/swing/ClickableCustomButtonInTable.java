package ar.com.qestudio.client.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class ClickableCustomButtonInTable extends JToggleButton {

	public ClickableCustomButtonInTable() {
		Dimension d = new Dimension(100, 100);
		JLabel lFirst = new JLabel("1st label");
		lFirst.setPreferredSize(d);

		JLabel lSecond = new JLabel("2nd label");
		lSecond.setPreferredSize(d);

		JPanel panel = new JPanel();
		panel.setOpaque(true);

		panel.setLayout(new BorderLayout());
		panel.add(lFirst, BorderLayout.NORTH);
		panel.add(lSecond, BorderLayout.SOUTH);
		add(panel); //if i comment this line out the click event gets triggered immediately as expected
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button clicked");
			}
		});
	}

	private static class CustomButtonRenderer implements TableCellRenderer {

		private final ClickableCustomButtonInTable button = new ClickableCustomButtonInTable();

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			return button;
		}
	}

	private static class CustomButtonEditor extends AbstractCellEditor
			implements TableCellEditor {

		private final ClickableCustomButtonInTable button = new ClickableCustomButtonInTable();

		@Override
		public Object getCellEditorValue() {
			return button.getText();
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			return button;
		}

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(200, 200));
		Container content = frame.getContentPane();
		TableModel model = new AbstractTableModel() {

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				return null;
			}

			@Override
			public int getRowCount() {
				return 1;
			}

			@Override
			public int getColumnCount() {
				return 1;
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return true;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return ClickableCustomButtonInTable.class;
			}
		};

		JTable table = new JTable(model);
		// table.setBounds(new Rectangle(0, 0, content.getWidth(), content
		// .getHeight()));
		table.setRowHeight(frame.getHeight());
		table.setDefaultRenderer(ClickableCustomButtonInTable.class,
				new CustomButtonRenderer());
		table.setDefaultEditor(ClickableCustomButtonInTable.class,
				new CustomButtonEditor());

		content.add(table);
		content.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}