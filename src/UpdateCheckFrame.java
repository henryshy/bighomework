import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class UpdateCheckFrame extends JFrame implements ActionListener {

    private JButton yes;
    private JTable table;
    private int index;
    private JComboBox c_type;
    private JComboBox c_item;
    private  JTextField t_id,t_date,t_bal;

    public UpdateCheckFrame(int index, JTable table, JTextField t_id,JTextField t_date,JTextField t_bal,JComboBox c_type,JComboBox c_item) {
        super("�޸�ȷ��");
        this.index = index;
        this.table = table;
        this.c_item=c_item;
        this.c_type=c_type;
        this.t_bal=t_bal;
        this.t_date=t_date;
        this.t_id=t_id;
        Container c = this.getContentPane();
        c.setLayout(new GridLayout(5, 1, 1, 1));

        BalFile balFile = InformationTable.TableData.get(index);
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        JPanel jp4 = new JPanel();
        JPanel jp5 = new JPanel();

        JLabel l_warning = new JLabel("��ȷ��Ҫ����Ϣ�������޸���");
        JLabel l_d1 = new JLabel("��ţ�" + balFile.getId() + "  ���ڣ�" + balFile.getDate() + "  ���ͣ�" + balFile.getType() + "  ���ݣ�" + balFile.getItem() + "  ��" + balFile.getBal());
        JLabel l_d2 = new JLabel("�޸�Ϊ��");
        JLabel l_d3 = new JLabel("��ţ�" + t_id.getText() + "  ���ڣ�" + t_date.getText() + "  ���ͣ�" + c_type.getSelectedItem() + "  ���ݣ�" + c_item.getSelectedItem() + "  ��" + t_bal.getText());

        yes = new JButton("ȷ��");
        JButton no = new JButton("ȡ��");
        jp1.add(l_warning);
        jp2.add(l_d1);
        jp3.add(l_d2);
        jp4.add(l_d3);
        jp5.add(yes);
        jp5.add(no);

        c.add(jp1);
        c.add(jp2);
        c.add(jp3);
        c.add(jp4);
        c.add(jp5);

        this.setResizable(false);
        this.setSize(400, 200);
        Dimension screen = this.getToolkit().getScreenSize();
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        this.setVisible(true);

        yes.addActionListener(this);
        no.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(yes==e.getSource()){
            BalFile it = InformationTable.TableData.get(index);
            it.setBal(Double.parseDouble(t_bal.getText()));
            it.setDate(Integer.parseInt(t_date.getText()));
            it.setItem((String) c_item.getSelectedItem());
            it.setType((String) c_type.getSelectedItem());
            SwingUtilities.invokeLater(() -> table.updateUI());
            JOptionPane.showMessageDialog(null, "�޸ĳɹ���", "", JOptionPane.PLAIN_MESSAGE);
            Serialization<ArrayList<BalFile>> s = new Serialization<>();
            try {
                s.Serialize(InformationTable.TableData);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            t_id.setText("");
            t_id.setEditable(true);
            t_date.setText("");
            t_bal.setText("");
        }
        dispose();
    }
}


