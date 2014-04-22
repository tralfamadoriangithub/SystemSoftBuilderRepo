package systemsoftbuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Random;

import systemsoftbuilder.entity.SoftEntity;
import systemsoftbuilder.parser.*;

public class SoftBuilder implements SoftParserHandler.OnParsedXML
{
	private static SoftBuilder softBuilder;
	private LinkedHashSet<SoftEntity> softSet;
	private XMLFileBuilder xmlFileBuilder;

	public static void buildSoftSet(String path)
	{
		if (softBuilder == null)
			softBuilder = new SoftBuilder();
		if (softBuilder.isPathExists(path))
			SoftParserHandler.runParser(path, softBuilder);
		else
			System.out.println("File does not exists");
	}

	private boolean isPathExists(String path)
	{
		return new File(path).exists();
	}

	public void onParsedXML(HashMap<String, ArrayList<SoftEntity>> softList)
	{
		buildSoftSet(softList);
		showSoftSet();
		saveConfigInXML();
	}

	private SoftEntity getRandomItem(ArrayList<SoftEntity> items)
	{
		Random rnd = new Random();
		return items.get(rnd.nextInt(items.size()));
	}

	private void buildSoftSet(HashMap<String, ArrayList<SoftEntity>> softList)
	{
		double totalSize = 0, totalPrice = 0;
		softSet = new LinkedHashSet<SoftEntity>();
		for (Entry<String, ArrayList<SoftEntity>> softItem : softList
				.entrySet())
		{
			SoftEntity item;
			if (!(softItem.getKey().equalsIgnoreCase("virus")))
			{
				item = getRandomItem(softItem.getValue());
				softSet.add(item);
				totalSize += item.getSize();
				totalPrice += item.getPrice();
			}
		}
		softSet.add(new SoftEntity("Total", "", totalPrice, totalSize));
	}

	private void saveConfigInXML()
	{
		if (softSet != null)
		{
			xmlFileBuilder = XMLFileBuilder.getInstance();
			xmlFileBuilder.writeToXML(softSet);
		}
	}

	private void showSoftSet()
	{
		System.out.println("Soft List Contains\n");
		for (SoftEntity item : softSet)
		{
			if (!item.getCategory().equalsIgnoreCase("total"))
			{
				System.out.println("Soft Category: " + item.getCategory());
				System.out.println("Soft Name: " + item.getName());
				System.out.println("Size: " + item.getFormatSize());
				System.out.println("Price: $" + item.getPrice());
				System.out.println();
			}else{
				System.out.println("Total: ");
				System.out.println("Total size: " + item.getFormatSize());
				System.out.println("Total price: $" + item.getPrice());
			}
		}
	}

}
