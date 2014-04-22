package systemsoftbuilder;

public class StartPoint
{
	static String filePath = "systemsoft.xml";
	
	public static void main(String[] args)
	{
		SoftBuilder.buildSoftSet(args.length > 0 ? args[0] : filePath);
	}

}
