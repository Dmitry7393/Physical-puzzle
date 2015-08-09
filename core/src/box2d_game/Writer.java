package box2d_game;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class Writer {
	String path = "";
	public Writer(String path1)
	{
		path = path1;
	}
	public void write(String text)
	{
    	try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true))))
    	{
    		out.println(text);
    	}
    	catch (IOException e)
    	{
    	    //exception handling left as an exercise for the reader
    	}
	}
}
