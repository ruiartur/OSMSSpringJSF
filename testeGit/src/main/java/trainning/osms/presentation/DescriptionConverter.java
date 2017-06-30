package trainning.osms.presentation;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringEscapeUtils;


@FacesConverter("descriptionContentConverter")
public class DescriptionConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String content = (String) value;
		String escapedContent = StringEscapeUtils.escapeHtml4(content);		
		StringBuilder builder = new StringBuilder("<p>");
		for (int i = 0; i < escapedContent.length(); ++i) {
			char c = escapedContent.charAt(i);
			if (c == '\n') {
				builder.append("</p>");
				builder.append("<p>");
			} else if (c == '\r') {
			} else {
				builder.append(c);
			}
		}
		builder.append("</p>");		
		return builder.toString();		
	}
	

}
