import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*class MyWindowListener extends WindowAdapter{
	private JTable table;
	public MyWindowListener(JTable table) {
		this.table=table;
	}
	@Override

	public void windowClosing(WindowEvent e) {
		super.windowClosing(e);
		SwingUtilities.invokeLater(()->table.updateUI());
	}
}*/
//选中Table中某行的监听
public class MyListListener implements ListSelectionListener {
	private JTable table;
	JComboBox c_type,c_item;
	private  JTextField t_id,t_date,t_bal;


	public MyListListener(JTable table, JComboBox c_type, JComboBox c_item, JTextField t_id, JTextField t_date, JTextField t_bal) {
		this.table = table;
		this.c_type = c_type;
		this.c_item = c_item;
		this.t_id = t_id;
		this.t_date = t_date;
		this.t_bal = t_bal;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
       int row=table.getSelectedRow();
       if(row>=InformationTable.TableData.size()) {
	   return;
       }
       BalFile b = InformationTable.TableData.get(row);
       c_type.setSelectedItem(b.getType());
       c_item.setSelectedItem(b.getItem());
       t_id.setText(String.valueOf(b.getId()));
       t_date.setText(String.valueOf(b.getDate()));
       t_bal.setText(String.valueOf(b.getBal()));
       t_id.setEditable(false);
	   }
}
