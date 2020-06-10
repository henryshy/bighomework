

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;


//主界面
public class MainFrame extends JFrame implements ActionListener {
	private JMenuItem[] mI ={new JMenuItem("密码重置"),new JMenuItem("退出系统")};
	private JMenuItem m_FMEdit=new JMenuItem("收支编辑");
	private JTextField t_fromdate,t_todate;
	private JButton b_select1,b_select2;
	private JComboBox<String> c_type;
	private double bal1;
	private JTable table;
	private String username;



	public MainFrame(String username) {
		super(username+",欢迎使用个人理财账本!");
		Container c=this.getContentPane();
		this.username=username;
		c.setLayout(new BorderLayout());
		JMenuBar mb = new JMenuBar();
		c.add(mb,"North");
		JMenu m_system = new JMenu("系统管理");
		mb.add(m_system);
		JMenu m_fm = new JMenu("收支管理");
		mb.add(m_fm);
		m_system.add(mI[0]);
		m_system.add(mI[1]);
		m_fm.add(m_FMEdit);
		m_FMEdit.addActionListener(this);
	        mI[0].addActionListener(this);
	    mI[1].addActionListener(this);

		JLabel l_type = new JLabel("收支类型：");
		String[] s1 = {"收入", "支出"};
		c_type=new JComboBox<>(s1);
	    b_select1=new JButton("查询");
		JLabel l_fromdate = new JLabel("起始时间");
        t_fromdate=new JTextField(8);
		JLabel l_todate = new JLabel("终止时间");
        t_todate=new JTextField(8);
		b_select2=new JButton("查询");
		JLabel l_ps = new JLabel("注意：时间格式为YYYYMMDD，例如：20150901");
		JPanel p_condition = new JPanel();
		p_condition.setLayout(new GridLayout(3,1));
		p_condition.setBorder(BorderFactory.createCompoundBorder(
	    BorderFactory.createTitledBorder("输入查询条件"),
	    BorderFactory.createEmptyBorder(5,5,5,5)));

		JPanel p1 = new JPanel();
	    JPanel p2 = new JPanel();
	    JPanel p3 = new JPanel();
	    p1.add(l_type);
	    p1.add(c_type);
	    p1.add(b_select1);
	    p2.add(l_fromdate);
		p2.add(t_fromdate);
		p2.add(l_todate);
		p2.add(t_todate);
		p2.add(b_select2);
		p3.add(l_ps);
		p_condition.add(p1);
	    p_condition.add(p2);
	    p_condition.add(p3);
        c.add(p_condition,"Center");

        b_select1.addActionListener(this);
        b_select2.addActionListener(this);

		JPanel p_detail = new JPanel();
        p_detail.setBorder(BorderFactory.createCompoundBorder(
	    BorderFactory.createTitledBorder("收支明细信息"),
	    BorderFactory.createEmptyBorder(5,5,5,5)));
		JLabel l_bal = new JLabel();
//新建一个InformationTable对象，为主界面的信息显示框
        InformationTable t=new InformationTable(new File("account.txt"));
        table=new JTable(t);
        table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(580,350));
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setViewportView(table);
		p_detail.add(l_bal);
		p_detail.add(scrollpane);
		c.add(p_detail,"South");

		for(BalFile balFile:InformationTable.TableData){
			if(balFile.getType().equals("支出")){
				 bal1 -= balFile.getBal();
			}
			else{
				bal1+=balFile.getBal();
			}
		}


	    if(bal1<0)
		    l_bal.setText("个人总收支余额为"+bal1+"元。您已超支，请适度消费！");
		else
			l_bal.setText("个人总收支余额为"+bal1+"元。");

        this.setResizable(false);
		this.setSize(600,580);
		Dimension screen = this.getToolkit().getScreenSize();
	    this.setLocation((screen.width-this.getSize().width)/2,(screen.height-this.getSize().height)/2);
		this.setVisible(true);
	}

   public void actionPerformed(ActionEvent e) {
	     Object temp=e.getSource();
	     if(temp==mI[0]){
	    	new ModifyPwdFrame(username);
	     }else if(temp==mI[1]){
	    	System.exit(0);
	     }else if(temp==m_FMEdit){
			 try {
			 	InformationTable t=new InformationTable(new File("account.txt"));
				 JFrame frame=new BalEditFrame(t);
				 /*frame.addWindowListener(new MyWindowListener(table));*/
				 frame.addWindowListener(new WindowAdapter() {
					 @Override
					 public void windowClosing(WindowEvent e) {
						 SwingUtilities.invokeLater(()->table.updateUI());
					 }
				 });
			 } catch (IOException ex) {
				 ex.printStackTrace();
			 }
		 }else if(temp==b_select1){  //根据收支类型查询
			 ArrayList<BalFile> listout = new ArrayList<>();
			 for (int i=0;i<InformationTable.TableData.size();i++) {
				 BalFile balFile=InformationTable.TableData.get(i);
				 if (balFile.getType().equals(c_type.getSelectedItem())) {
					 listout.add(balFile);
				 }
			 }
			 InformationTable t=new InformationTable(listout);
			 table.setModel(t);
	     }else if(temp==b_select2){   //根据时间范围查询
				int year1, year2;
				int month1, month2;
				int day1, day2;
				try {
					year1 = Integer.parseInt(t_fromdate.getText().substring(0, 4));
					year2 = Integer.parseInt(t_todate.getText().substring(0, 4));
					month1 = Integer.parseInt(t_fromdate.getText().substring(4, 6));
					month2 = Integer.parseInt(t_todate.getText().substring(4, 6));
					day1 = Integer.parseInt(t_fromdate.getText().substring(6, 8));
					day2 = Integer.parseInt(t_todate.getText().substring(6, 8));
					LocalDate ld1 = LocalDate.of(year1, month1, day1);
					LocalDate ld2 = LocalDate.of(year2, month2, day2);
					ArrayList<BalFile> listout = new ArrayList<>();
					for (int i=0;i<InformationTable.TableData.size();i++) {
						BalFile balFile=InformationTable.TableData.get(i);
						int yearin = (balFile.getDate()) / 10000;
						int monthin = (balFile.getDate() - yearin * 10000) / 100;
						int dayin = balFile.getDate() % 100;
						LocalDate ldin = LocalDate.of(yearin, monthin, dayin);
						if (ldin.compareTo(ld1) >= 0 && ldin.compareTo(ld2) <= 0&&balFile.getType().equals(c_type.getSelectedItem())) {
							listout.add(balFile);
						}
					}
					InformationTable t = new InformationTable(listout);
					table.setModel(t);
				}
				catch(StringIndexOutOfBoundsException | DateTimeException e1){
					JOptionPane.showMessageDialog(null,"日期输入错误！", "",JOptionPane.ERROR_MESSAGE);
					t_fromdate.setText("");
					t_todate.setText("");
				}
		 }
   }


}
