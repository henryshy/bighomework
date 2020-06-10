

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

//收支编辑界面
 public class BalEditFrame extends JFrame implements ActionListener {
	private  JTextField t_id,t_date,t_bal;
	private JComboBox c_type,c_item;
	private JButton b_update;
	private JButton b_delete;
	private JButton b_new;
	private JButton b_clear;
	private JTable table;


	public BalEditFrame(InformationTable t) throws IOException {
		super("收支编辑" );
		JLabel l_id = new JLabel("编号：");
		JLabel l_date = new JLabel("日期：");
		JLabel l_bal = new JLabel("金额：");
		JLabel l_type = new JLabel("类型：");
		JLabel l_item = new JLabel("内容：");
		t_id=new JTextField(8);
		t_date=new JTextField(8);
		t_bal=new JTextField(8);

		String[] s1 ={"收入","支出"};
		String[] s2 ={"购物","餐饮","居家","交通","娱乐","人情","工资","奖金","其他"};
		c_type=new JComboBox(s1);
		c_item=new JComboBox(s2);

		b_update=new JButton("修改");
		b_delete=new JButton("删除");
		b_new=new JButton("录入");
		b_clear=new JButton("清空");

		Container c=this.getContentPane();
		c.setLayout(new BorderLayout());

		JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(5,2,10,10));
        p1.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createTitledBorder("编辑收支信息"),
        BorderFactory.createEmptyBorder(5,5,5,5)));
		p1.add(l_id);
		p1.add(t_id);
		p1.add(l_date);
		p1.add(t_date);
		p1.add(l_type);
		p1.add(c_type);
		p1.add(l_item);
		p1.add(c_item);
		p1.add(l_bal);
		p1.add(t_bal);
		c.add(p1, BorderLayout.WEST);

		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(4,1,10,10));
		p2.add(b_new);
		p2.add(b_update);
		p2.add(b_delete);
		p2.add(b_clear);


		c.add(p2,BorderLayout.CENTER);
//加载显示框
		JPanel p3 = new JPanel();
		//传入的t代表主界面的InformationTable类的对象
		table = new JTable(t);
		table.getSelectionModel().addListSelectionListener(new MyListListener(table,c_type,c_item,t_id,t_date,t_bal));
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setViewportView(table);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p3.add(scrollpane);
		c.add(p3,BorderLayout.EAST);

		p3.setBorder(BorderFactory.createCompoundBorder(
		BorderFactory.createTitledBorder("显示收支信息"),
		BorderFactory.createEmptyBorder(5,5,5,5)));



		b_update.addActionListener(this);
		b_delete.addActionListener(this);
		b_new.addActionListener(this);
		b_clear.addActionListener(this);

		//添加代码，为table添加鼠标点击事件监听addMouseListener

	    this.setResizable(false);
		this.setSize(800,300);
		Dimension screen = this.getToolkit().getScreenSize();
	    this.setLocation((screen.width-this.getSize().width)/2,(screen.height-this.getSize().height)/2);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {


		if(b_update==e.getSource()) {  // 修改某条收支信息
				int i;
				if ((i = table.getSelectedRow()) != -1) {
					new UpdateCheckFrame(i,table,t_id,t_date,t_bal,c_type,c_item);
			}
		}else if(b_delete==e.getSource()){   //删除某条收支信息
			int i;
			if((i= table.getSelectedRow())!=-1){
				new DeleteCheckFrame(i,table);
				t_id.setText("");
				t_id.setEditable(true);
				t_date.setText("");
				t_bal.setText("");
			}
		}else if(b_new==e.getSource()){   //新增某条收支信息
			//
			try {
				if(t_date.getText()==null||t_bal.getText()==null||t_id.getText()==null){
					throw new NumberFormatException();
				}
				String id=t_id.getText();
				for(BalFile balFile:InformationTable.TableData){
					if(balFile.getId()==Integer.parseInt(id)){
						throw new IDCheckException();
					}
				}
				int yearin=Integer.parseInt(t_date.getText())/10000;
				int monthin=(Integer.parseInt(t_date.getText())-yearin*10000)/100;
				int dayin=Integer.parseInt(t_date.getText())%100;
				LocalDate.of(yearin,monthin,dayin);

				InformationTable.TableData.add(new BalFile(Integer.parseInt(t_id.getText()), Integer.parseInt(t_date.getText()), (String) c_type.getSelectedItem(), (String) c_item.getSelectedItem(), Double.parseDouble(t_bal.getText())));
				JOptionPane.showMessageDialog(null,"录入成功！", "",JOptionPane.PLAIN_MESSAGE);
				SwingUtilities.invokeLater(()->table.updateUI());
				//对TableData进行序列化到文件中
				Serialization<ArrayList> s=new Serialization();
				try {
					s.Serialize(InformationTable.TableData);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				t_id.setText("");
				t_date.setText("");
				t_bal.setText("");
			}catch (StringIndexOutOfBoundsException | NumberFormatException ex){
				JOptionPane.showMessageDialog(null,"数据输入错误，请重新输入！", "",JOptionPane.WARNING_MESSAGE);
				t_id.setText("");
				t_date.setText("");
				t_bal.setText("");
			}catch(DateTimeException de){
				JOptionPane.showMessageDialog(null,"日期格式错误，请重新输入！", "",JOptionPane.WARNING_MESSAGE);
				t_date.setText("");
			}catch (IDCheckException ie){
				JOptionPane.showMessageDialog(null,"编号重复，请重新输入！", "",JOptionPane.WARNING_MESSAGE);
				t_id.setText("");
			}

		}else if(b_clear==e.getSource()){   //清空输入框
			t_id.setText("");
			t_date.setText("");
			t_bal.setText("");
			t_id.setEditable(true);
		}


	}

}
