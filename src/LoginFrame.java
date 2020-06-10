
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//��¼����
//JOptionPane.showMessageDialog(null,"�û����������", "����", //JOptionPane.ERROR_MESSAGE);

public class LoginFrame extends JFrame implements ActionListener {
    private JTextField t_user;//�û����ı���
    private JPasswordField t_pwd; //�����ı���
    private JButton b_ok,b_cancel; //��¼��ť���˳���ť

    public LoginFrame(){
        super("��ӭʹ�ø�������˱�!");
        JLabel l_user = new JLabel("�û�����", JLabel.RIGHT);
        //�û�����ǩ�������ǩ
        JLabel l_pwd = new JLabel(" ���룺", JLabel.RIGHT);
        t_user=new JTextField(31);
        t_pwd=new JPasswordField(31);
        b_ok=new JButton("��¼");
        b_cancel=new JButton("�˳�");
        //���ַ�ʽFlowLayout��һ����������һ��
        Container c=this.getContentPane();
        c.setLayout(new FlowLayout());
        c.add(l_user);
        c.add(t_user);
        c.add(l_pwd);
        c.add(t_pwd);
        c.add(b_ok);
        c.add(b_cancel);
        //Ϊ��ť��Ӽ����¼�
        b_ok.addActionListener(this);
        b_cancel.addActionListener(this);
        //�����С���ɵ��� 
        this.setResizable(false);
        this.setSize(455,150);

        //������ʾ����
        Dimension screen = this.getToolkit().getScreenSize();
        this.setLocation((screen.width-this.getSize().width)/2,(screen.height-this.getSize().height)/2);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {//��¼����Ĳ������������롢�˳�

        if(b_cancel==e.getSource()){//�˳�������
            System.exit(0);
        }
        else if(b_ok==e.getSource()){//У���û���������
            try {
                BufferedReader readpwd = new BufferedReader(new FileReader("D:\\workspace\\bighomework\\src\\pwd.txt"));
                String user=readpwd.readLine();
                String pwd=readpwd.readLine();
                 if(t_user.getText().equals(user)&&String.valueOf(t_pwd.getPassword()).equals(pwd)){
                new MainFrame(t_user.getText().trim());
                 dispose();
                }
                else
                {
                   JOptionPane.showMessageDialog(null,"�û����������", "����", JOptionPane.ERROR_MESSAGE); //JOptionPane.ERROR_MESSAGE);
                }
                readpwd.close();
            } catch (FileNotFoundException ex) {
                System.out.println("δ�ҵ������ļ�");
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
}