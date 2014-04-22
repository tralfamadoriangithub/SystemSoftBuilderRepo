package systemsoftbuilder;

import java.io.File;
import java.util.LinkedHashSet;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import systemsoftbuilder.entity.SoftEntity;

public class XMLFileBuilder
{
	private static XMLFileBuilder xmlFileBuilder;

	private XMLFileBuilder()
	{

	}

	public static XMLFileBuilder getInstance()
	{
		if (xmlFileBuilder == null)
		{
			xmlFileBuilder = new XMLFileBuilder();
		}
		return xmlFileBuilder;
	}

	public void writeToXML(LinkedHashSet<SoftEntity> softSet)
	{
		File file = new File("config.xml");
		try
		{
			if (file.exists())
				file.delete();
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			Element rootElement = doc.createElement("CONFIG");
			Element childRoot = null;
			for (SoftEntity item : softSet)
			{
				Element child;
				childRoot = doc.createElement("SOFT_ITEM");
				child = doc.createElement("CATEGORY");
				child.appendChild(doc.createTextNode(item.getCategory()));
				childRoot.appendChild(child);

				child = doc.createElement("NAME");
				child.appendChild(doc.createTextNode(item.getName()));
				childRoot.appendChild(child);

				child = doc.createElement("SIZE");
				child.appendChild(doc.createTextNode("" + new Double(item.getSize()).longValue()));
				childRoot.appendChild(child);

				child = doc.createElement("PRICE");
				child.appendChild(doc.createTextNode("" + item.getPrice()));
				childRoot.appendChild(child);

				rootElement.appendChild(childRoot);
			}
			doc.appendChild(rootElement);

			Transformer tr = TransformerFactory.newInstance().newTransformer();
			tr.setOutputProperty(OutputKeys.METHOD, "xml");
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.transform(new DOMSource(doc), new StreamResult(file));
		} catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		} catch (TransformerConfigurationException e)
		{
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e)
		{
			e.printStackTrace();
		} catch (TransformerException e)
		{
			e.printStackTrace();
		}
	}
}
