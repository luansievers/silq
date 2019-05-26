package br.ufsc.silq.core.parser.attribute;

import br.ufsc.silq.core.parser.dto.ParserSet;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class AttributeGetter {

	public static List<String> iterateNodes(ParserSet set, Node raizLocal) {
		List<String> infos = new ArrayList<>();
		NodeList qualisList = raizLocal.getChildNodes();

		if (set.getSetName().contains(raizLocal.getNodeName().toLowerCase())) {
			return AttributeGetter.getAttribute(raizLocal, set.getAttributeNames());
		} else if (qualisList.getLength() > 0) {
			for (int i = 0; i < qualisList.getLength(); i++) {
				Node nodoFilho = qualisList.item(i);
				infos.addAll(iterateNodes(set, nodoFilho));
			}
		}

		return infos;
	}

	public static List<String> getAttribute(Node nodo, List<String> attrList) {
		NamedNodeMap attributes = nodo.getAttributes();
		List<String> resultado = new ArrayList<>();

		for (int g = 0; g < attributes.getLength(); g++) {
			Attr attribute = (Attr) attributes.item(g);
			if (attrList.contains(attribute.getName().toLowerCase())) {
				resultado.add(attribute.getValue());
			}
		}

		return resultado;
	}
}
