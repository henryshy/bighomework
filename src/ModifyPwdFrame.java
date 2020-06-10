

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

//�޸��������
public class ModifyPwdFrame extends JFrame implements ActionListener {
	private JLabel l_oldPWD,l_newPWD,l_newPWDAgain;
	private JPasswordField t_oldPWD,t_newPWD,t_newPWDAgain;
	private JButton b_ok,b_cancel;
	private String username;

	public ModifyPwdFrame(String username){
		super("�޸�����");
		this.username=username;
		l_oldPWD=new JLabel("������");
		l_newPWD=new JLabel("�����룺");
		l_newPWDAgain=new JLabel("ȷ�������룺");
		t_oldPWD=new JPasswordField(15);
		t_newPWD=new JPasswordField(15);
		t_newPWDAgain=new JPasswordField(15);
		b_ok=new JButton("ȷ��");
		b_cancel=new JButton("ȡ��");
		Container c=this.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(l_oldPWD);
		c.add(t_oldPWD);
		c.add(l_newPWD);
		c.add(t_newPWD);
		c.add(l_newPWDAgain);
		c.add(t_newPWDAgain);
		c.add(b_ok);
		c.add(b_cancel);
		b_ok.addActionListener(this);
		b_cancel.addActionListener(this);
		this.setResizable(false);
		this.setSize(280,160);
		Dimension screen = this.getToolkit().getScreenSize();
	    this.setLocation((screen.width-this.getSize().width)/2,(screen.height-this.getSize().height)/2);
		this.show();
	}

	public void actionPerformed(ActionEvent e) {
		if(b_cancel==e.getSource()){
             dispose();
		}else if(b_ok==e.getSource()){  //???????
			try {
				File F=new File("D:\\workspace\\bighomework\\src\\pwd.txt");
				BufferedReader readpwd1 = new BufferedReader(new FileReader("D:\\workspace\\bighomework\\src\\pwd.txt"));
				String line = null;
				while(readpwd1.readLine()!=null){
					line=readpwd1.readLine();
				}
				if(line.equals(String.valueOf(t_oldPWD.getPassword()))){
					if(String.valueOf(t_newPWD.getPassword()).equals(String.valueOf(t_newPWDAgain.getPassword()))) {
						StringBuffer buf = new StringBuffer();
						String str = null;
						BufferedReader readpwd2 = new BufferedReader(new FileReader("D:\\workspace\\bighomework\\src\\pwd.txt"));
						while ((str = readpwd2.readLine()) != null) {
							if (!str.equals(line)) {
								buf.append(str + '\n');
							} else {
								buf.append(String.valueOf(t_newPWD.getPassword()));
							}
						}
						FileOutputStream fos = new FileOutputStream(F);
						PrintWriter pw = new PrintWriter(F);
						pw.write(buf.toString().toCharArray());
						pw.flush();
						pw.close();
						fos.flush();
						fos.close();
						dispose();
						JOptionPane.showMessageDialog(null,"?????????", "",JOptionPane.PLAIN_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(null,"????????????????", "????",JOptionPane.ERROR_MESSAGE);
					}
				}
                 else{
					 JOptionPane.showMessageDialog(null,"????????", "????",JOptionPane.ERROR_MESSAGE);
                 }
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
