import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {

    	
    	public boolean isCellEditable(final int rowIndex, final int columnIndex)
    	{
    		return false;
    	}
    }