
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//登录界面
//JOptionPane.showMessageDialog(null,"用户名密码出错", "警告", //JOptionPane.ERROR_MESSAGE);

public class LoginFrame extends JFrame implements ActionListener {
    private JTextField t_user;//用户名文本框
    private JPasswordField t_pwd; //密码文本框
    private JButton b_ok,b_cancel; //登录按钮，退出按钮

    public LoginFrame(){
        super("欢迎使用个人理财账本!");
        JLabel l_user = new JLabel("用户名：", JLabel.RIGHT);
        //用户名标签，密码标签
        JLabel l_pwd = new JLabel(" 密码：", JLabel.RIGHT);
        t_user=new JTextField(31);
        t_pwd=new JPasswordField(31);
        b_ok=new JButton("登录");
        b_cancel=new JButton("退出");
        //布局方式FlowLayout，一行排满排下一行
        Container c=this.getContentPane();
        c.setLayout(new FlowLayout());
        c.add(l_user);
        c.add(t_user);
        c.add(l_pwd);
        c.add(t_pwd);
        c.add(b_ok);
        c.add(b_cancel);
        //为按钮添加监听事件
        b_ok.addActionListener(this);
        b_cancel.addActionListener(this);
        //界面大小不可调整 
        this.setResizable(false);
        this.setSize(455,150);

        //界面显示居中
        Dimension screen = this.getToolkit().getScreenSize();
        this.setLocation((screen.width-this.getSize().width)/2,(screen.height-this.getSize().height)/2);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {//登录界面的操作，输入密码、退出

        if(b_cancel==e.getSource()){//退出并保存
            System.exit(0);
        }
        else if(b_ok==e.getSource()){//校验用户名和密码
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
                   JOptionPane.showMessageDialog(null,"用户名密码出错", "警告", JOptionPane.ERROR_MESSAGE); //JOptionPane.ERROR_MESSAGE);
                }
                readpwd.close();
            } catch (FileNotFoundException ex) {
                System.out.println("未找到密码文件");
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
}