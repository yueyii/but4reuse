package org.but4reuse.adapters.proteins.fca;

import java.io.IOException;

import fr.labri.galatea.Attribute;
import fr.labri.galatea.Entity;
import fr.labri.galatea.io.GenerateCode;

public class GenarateHTMLProtein extends GenerateCode{
	
	private ContextProtein context;

	public GenarateHTMLProtein(ContextProtein context) {
		this.context = context;
	}
	
	public static void toFile(ContextProtein context,String path) throws IOException {
		GenarateHTMLProtein g = new GenarateHTMLProtein(context);
		g.generateCode();
		g.toFile(path);
	}
	
	@Override
	public void generateCode() {
		
		initBuffer();

		appendHeader();

		appendLine("<table><tr><td></td>");
		for( Attribute attr: context.getAttributes() )
			appendLine("<td>" + attr.toString() + "</td>");
		appendLine("</tr>");

		for( Entity ent: context.getEntities() ) {
			appendLine("<tr><td>" + ent.getName() + "</td>");
			for( Attribute attr: context.getAttributes() ) {
				if ( context.getAttributes(ent).contains(attr))				
				{	appendLine("<td>"+context.getproteinPercentageFromAttribute(ent,attr)+"</td>");
				}else
					appendLine("<td></td>");
			}
			appendLine("</tr>");
		}

		appendLine("</table>");

		appendFooter();
	}
	
	private void appendHeader() {
		appendLine("<html><head><title>Relational Context Family</title><style type=\"text/css\">" +
				"body { background-color: white; color: black; font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; font-size:small; }" +
				"table { border: medium solid black; border-collapse: collapse; empty-cells:show; }" +
				"td, th { border: thin solid black; font-size:small; text-align: center; padding: 5px;}" +
		"</style></head><body>");
	
	}

	private void appendFooter() {
		appendLine("</body></html>");
	}
}
