import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//信息显示框
public class InformationTable extends AbstractTableModel {
	private List<String> Title;
	public static ArrayList<BalFile> TableData;
    public ArrayList<BalFile> list;

	static {
		try {
			File f=new File("account.txt");
			//若文件存在则对TableData进行反序列化，否则新建
			if(f.exists()) {
				TableData = new Serialization<ArrayList<BalFile>>().DeSerialize();
			}
			else {
				f.createNewFile();
				TableData= new ArrayList<>();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	public InformationTable(File f) {
		Title= new ArrayList<>();
		Title.add("编号");
		Title.add("日期");
		Title.add("类型");
		Title.add("内容");
		Title.add("金额");
	}
	public  InformationTable(ArrayList<BalFile> list){
		Title=new ArrayList<>();
		Title.add("编号");
		Title.add("日期");
		Title.add("类型");
		Title.add("内容");
		Title.add("金额");
		this.list=list;
	}


	@Override
	public int getRowCount() {
		return TableData.size()+20;
	}

	@Override
	public int getColumnCount() {
		return Title.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(list==null) {
			if (rowIndex > TableData.size() - 1) {
				return "";
			}
			BalFile bf = TableData.get(rowIndex);
			return getObject(columnIndex, bf);
		}
		else{
			if (rowIndex > list.size() - 1) {
				return "";
			}
			BalFile bf = list.get(rowIndex);
			return getObject(columnIndex, bf);
		}
	}

	private Object getObject(int columnIndex, BalFile bf) {
		switch (columnIndex) {
			case 0:
				return String.valueOf(bf.getId());
			case 1:
				return String.valueOf(bf.getDate());
			case 2:
				return String.valueOf(bf.getType());
			case 3:
				return String.valueOf(bf.getItem());
			case 4:
				return String.valueOf(bf.getBal());
			default:
				return "";
		}
	}

	public String getColumnName(int column) {
		switch (column){
			case 0:return "编号";
			case 1:return "日期";
			case 2:return "类型";
			case 3:return "内容";
			case 4:return "金额";
			default:return "";
		}
	}
}
