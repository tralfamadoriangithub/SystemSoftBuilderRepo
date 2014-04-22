package systemsoftbuilder.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import systemsoftbuilder.entity.SoftEntity;


public class SoftParserHandler extends DefaultHandler
{
	private SoftEntity entity;
	private HashMap<String, ArrayList<SoftEntity>> softList;
	private String value;
	private static SoftParserHandler.OnParsedXML listenerLink;
	
	public static void runParser(String address, SoftParserHandler.OnParsedXML listener)
	{
		listenerLink = listener;
		SoftParserHandler handler = new SoftParserHandler();
		try
		{
			SAXParserFactory.newInstance().newSAXParser().parse(new File(address), handler);
		} catch (SAXException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void startDocument() throws SAXException
	{
		System.out.println("Parse file...");
		softList = new HashMap<String, ArrayList<SoftEntity>>();
	}
	
	@Override
	public void startElement(String uri, String localName,
            String qName, Attributes attributes) throws SAXException
	{
		if(qName.equalsIgnoreCase("soft_item"))
			entity = new SoftEntity();
		value = "";
	}
	
	@Override
	public void characters(char[] buffer, int start, int length) throws SAXException
	{
		value = new String(buffer, start, length);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		if(qName.equalsIgnoreCase("category"))
			entity.setCategory(value);
		else if(qName.equalsIgnoreCase("name"))
			entity.setName(value);
			else if(qName.equalsIgnoreCase("price"))
				entity.setPrice(Double.parseDouble(value));
				else if(qName.equalsIgnoreCase("size"))
					entity.setSize(Double.parseDouble(value));
		if(qName.equalsIgnoreCase("soft_item"))
		{
			if(softList.containsKey(entity.getCategory()))
				softList.get(entity.getCategory()).add(entity);
			else{
				ArrayList<SoftEntity> begin = new ArrayList<SoftEntity>();
				begin.add(entity);
				softList.put(entity.getCategory(), begin);
			}
			//System.out.println(softList);
		}
	}
	
	@Override
	public void endDocument() throws SAXException
	{
		showList();
		listenerLink.onParsedXML(softList);
	}
	
	private void showList()
	{
		for(Entry<String, ArrayList<SoftEntity>> entry : softList.entrySet())
		{
			System.out.println("\t" + entry.getKey() + ":");
			for(SoftEntity entity : entry.getValue())
			{
				System.out.println(entity);
			}
			System.out.println();
		}
		 
	}
	
	public interface OnParsedXML
	{
		public void onParsedXML(HashMap<String, ArrayList<SoftEntity>> softList);
	}
}
