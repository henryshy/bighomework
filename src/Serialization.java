import java.io.*;
import java.util.ArrayList;

//序列化
public class Serialization<T>{
		//对传入的T类对象进行序列化
	void Serialize(T o) throws IOException {
		ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(new File("account.txt")));
		out.writeObject(o);
		out.close();
	}
	//对文件进行反序列化，返回T类对象
    T DeSerialize() throws IOException, ClassNotFoundException {
		try{
		ObjectInputStream in=new ObjectInputStream(new FileInputStream(new File("account.txt")));
	    T input=(T)in.readObject();
	    in.close();
	    return input;
		}
		catch (EOFException e){
			return (T) new ArrayList<BalFile>();
		}
	}
}
