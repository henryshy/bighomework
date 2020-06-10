import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class DeleteCheckFrame extends JFrame implements ActionListener {
    private JButton yes;
    private JTable table;
    private int index;

    public DeleteCheckFrame(int index,JTable table){
        super("ɾ��ȷ��");
        this.index=index;
        this.table=table;
        Container c=this.getContentPane();
        c.setLayout(new GridLayout(7,1,5,5));

        BalFile balFile = InformationTable.TableData.get(index);
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        JPanel jp4 = new JPanel();
        JPanel jp5 = new JPanel();
        JPanel jp6 = new JPanel();
        JPanel jp7 = new JPanel();
        JLabel l_warning = new JLabel("��ȷ��Ҫɾ��������֧��Ϣ��");
        JLabel l_d1 = new JLabel("��ţ�" + balFile.getId());
        JLabel l_d2 = new JLabel("���ڣ�" + balFile.getDate());
        JLabel l_d3 = new JLabel("���ͣ�" + balFile.getType());
        JLabel l_d4 = new JLabel("���ݣ�" + balFile.getItem());
        JLabel l_d5 = new JLabel("��" + balFile.getBal());
        yes=new JButton("ȷ��");
        JButton no = new JButton("ȡ��");
        jp1.add(l_warning);
        jp2.add(l_d1);
        jp3.add(l_d2);
        jp4.add(l_d3);
        jp5.add(l_d4);
        jp6.add(l_d5);
        jp7.add(yes);
        jp7.add(no);

        c.add(jp1);
        c.add(jp2);
        c.add(jp3);
        c.add(jp4);
        c.add(jp5);
        c.add(jp6);
        c.add(jp7);

        this.setResizable(false);
        this.setSize(280,280);
        Dimension screen = this.getToolkit().getScreenSize();
        this.setLocation((screen.width-this.getSize().width)/2,(screen.height-this.getSize().height)/2);
        this.setVisible(true);

        yes.addActionListener(this);
        no.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(yes==e.getSource()){
            InformationTable.TableData.remove(index);
            SwingUtilities.invokeLater(()->table.updateUI());
            JOptionPane.showMessageDialog(null,"ɾ���ɹ���", "",JOptionPane.PLAIN_MESSAGE);
            Serialization<ArrayList> s=new Serialization();
            try {
                s.Serialize(InformationTable.TableData);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            dispose();
        }
        else{
            dispose();
        }
    }
}
