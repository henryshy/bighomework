

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


//������
public class MainFrame extends JFrame implements ActionListener {
	private JMenuItem[] mI ={new JMenuItem("��������"),new JMenuItem("�˳�ϵͳ")};
	private JMenuItem m_FMEdit=new JMenuItem("��֧�༭");
	private JTextField t_fromdate,t_todate;
	private JButton b_select1,b_select2;
	private JComboBox<String> c_type;
	private double bal1;
	private JTable table;
	private String username;



	public MainFrame(String username) {
		super(username+",��ӭʹ�ø�������˱�!");
		Container c=this.getContentPane();
		this.username=username;
		c.setLayout(new BorderLayout());
		JMenuBar mb = new JMenuBar();
		c.add(mb,"North");
		JMenu m_system = new JMenu("ϵͳ����");
		mb.add(m_system);
		JMenu m_fm = new JMenu("��֧����");
		mb.add(m_fm);
		m_system.add(mI[0]);
		m_system.add(mI[1]);
		m_fm.add(m_FMEdit);
		m_FMEdit.addActionListener(this);
	        mI[0].addActionListener(this);
	    mI[1].addActionListener(this);

		JLabel l_type = new JLabel("��֧���ͣ�");
		String[] s1 = {"����", "֧��"};
		c_type=new JComboBox<>(s1);
	    b_select1=new JButton("��ѯ");
		JLabel l_fromdate = new JLabel("��ʼʱ��");
        t_fromdate=new JTextField(8);
		JLabel l_todate = new JLabel("��ֹʱ��");
        t_todate=new JTextField(8);
		b_select2=new JButton("��ѯ");
		JLabel l_ps = new JLabel("ע�⣺ʱ���ʽΪYYYYMMDD�����磺20150901");
		JPanel p_condition = new JPanel();
		p_condition.setLayout(new GridLayout(3,1));
		p_condition.setBorder(BorderFactory.createCompoundBorder(
	    BorderFactory.createTitledBorder("�����ѯ����"),
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
	    BorderFactory.createTitledBorder("��֧��ϸ��Ϣ"),
	    BorderFactory.createEmptyBorder(5,5,5,5)));
		JLabel l_bal = new JLabel();
//�½�һ��InformationTable����Ϊ���������Ϣ��ʾ��
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
			if(balFile.getType().equals("֧��")){
				 bal1 -= balFile.getBal();
			}
			else{
				bal1+=balFile.getBal();
			}
		}


	    if(bal1<0)
		    l_bal.setText("��������֧���Ϊ"+bal1+"Ԫ�����ѳ�֧�����ʶ����ѣ�");
		else
			l_bal.setText("��������֧���Ϊ"+bal1+"Ԫ��");

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
		 }else if(temp==b_select1){  //������֧���Ͳ�ѯ
			 ArrayList<BalFile> listout = new ArrayList<>();
			 for (int i=0;i<InformationTable.TableData.size();i++) {
				 BalFile balFile=InformationTable.TableData.get(i);
				 if (balFile.getType().equals(c_type.getSelectedItem())) {
					 listout.add(balFile);
				 }
			 }
			 InformationTable t=new InformationTable(listout);
			 table.setModel(t);
	     }else if(temp==b_select2){   //����ʱ�䷶Χ��ѯ
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
					JOptionPane.showMessageDialog(null,"�����������", "",JOptionPane.ERROR_MESSAGE);
					t_fromdate.setText("");
					t_todate.setText("");
				}
		 }
   }


}
