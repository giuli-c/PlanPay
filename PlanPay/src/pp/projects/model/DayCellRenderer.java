package pp.projects.model;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class DayCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value != null) {
            cell.setText(value.toString());
        } else {
            cell.setText("");
        }
        
        cell.setVerticalAlignment(SwingConstants.TOP); 		// Posiziona il testo in alto
        cell.setHorizontalAlignment(SwingConstants.LEFT); 	// Posiziona il testo a sinistra
        return cell;
    }
}