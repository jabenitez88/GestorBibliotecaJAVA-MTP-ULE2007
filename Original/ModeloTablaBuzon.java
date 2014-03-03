import javax.swing.DefaultListModel;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
/** Clase que crea el modelo de la tabla del buzón */
public class ModeloTablaBuzon extends DefaultListModel
	                          implements TableModel {
	        public String[] columnNames;

	        public ModeloTablaBuzon(String[] columnNames) {
	            super();
	            this.columnNames = columnNames;
	        }

	        public void rowChanged(int row) {
	            fireContentsChanged(this, row, row); 
	        }
	        private TableModel tableModel = new AbstractTableModel() {
	            public String getColumnName(int column) {
	                return columnNames[column];
	            }
	            public int getRowCount() { 
	                return size();
	            }
	            public int getColumnCount() {
	                return columnNames.length;
	            }
	            public Object getValueAt(int row, int column) {
	                String[] rowData = (String [])elementAt(row);
	                return rowData[column];
	            }
	            public void setValueAt(Object value, int row, int column) {
	                String newValue = (String)value;
	                String[] rowData = (String [])elementAt(row);
	                rowData[column] = newValue;
	                fireTableCellUpdated(row, column); //table event
	                rowChanged(row);                   //list event
	            }
	        };

	        //Implement the TableModel interface.
	        public int getRowCount() {
	            return tableModel.getRowCount();
	        }
	        public int getColumnCount() {
	            return tableModel.getColumnCount();
	        }
	        public String getColumnName(int columnIndex) {
	            return tableModel.getColumnName(columnIndex);
	        }
	        public Class getColumnClass(int columnIndex) {
	            return tableModel.getColumnClass(columnIndex);
	        }
	        public boolean isCellEditable(int rowIndex, int columnIndex) {
	            return tableModel.isCellEditable(rowIndex, columnIndex);
	        }
	        public Object getValueAt(int rowIndex, int columnIndex) {
	            return tableModel.getValueAt(rowIndex, columnIndex);
	        }
	        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	            tableModel.setValueAt(aValue, rowIndex, columnIndex);
	        }
	        public void addTableModelListener(TableModelListener l) {
	            tableModel.addTableModelListener(l);
	        }
	        public void removeTableModelListener(TableModelListener l) {
	            tableModel.removeTableModelListener(l);
	        }

	    }// Fin class shareddatamodel