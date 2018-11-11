package Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestStoreByteArray {
	public static void main(String args[]) throws IOException {
	    ByteArrayOutputStream f = new ByteArrayOutputStream();
	    
	    byte buf[] = "this is a test".getBytes();
	    f.write(buf);
	    System.out.println(f.toString());
	    byte b[] = f.toByteArray();
	    for (int i = 0; i < b.length; i++) {
	      System.out.println((char) b[i]);
	    }
	    OutputStream f2 = new FileOutputStream("test.txt");
	    f.writeTo(f2);
	    f2.close();
	    f.reset();
	    for (int i = 0; i < 3; i++){
	      f.write('X');
	    }
	    System.out.println(f.toString());
	  }
}
