package systemsoftbuilder;

public class StartPoint
{
	static String filePath = "C:\\_Folder\\workspace\\SystemSoftBuilder\\systemsoft.xml";
	
	public static void main(String[] args)
	{
		SoftBuilder.buildSoftSet(args.length > 0 ? args[0] : filePath);
	}

}
