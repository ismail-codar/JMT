package org.jmt;

public class Parser {
	public String DELIMETER_OPEN = "<%";
	public String DELIMETER_CLOSE = "%>";

	public String parse(String className, String str) {
		String args = "";
		String[] imports = null;
		
		str = str.trim();
		if(str.startsWith("@@tags")) {
			String[] tags = str.substring(7, str.indexOf("\n")).split("\\|");
			DELIMETER_OPEN = tags[0].trim();
			DELIMETER_CLOSE = tags[1].trim();
			str = str.substring(tags[0].length() + tags[1].length() + 9, str.length()).trim();
		}
		if(str.startsWith("@@imports")) {
			imports = str.substring(str.indexOf("(") + 1, str.indexOf(")")).trim().split("\n");
			str = str.substring(str.indexOf(")") + 1).trim();
		}
		if(str.startsWith("@@args")) {
			args = str.substring(7, str.indexOf("\n")).trim();
			str = str.substring(args.length() + 8, str.length()).trim();
		}
		
		StringBuilder sb = new StringBuilder();
		if(imports != null) {
			for(String imp : imports) {
				sb.append(String.format("import %s;\n", imp.trim()));
			}
			sb.append("\n");
		}
		sb.append("public class ");
		sb.append(className);
		sb.append(" {\n");
		sb.append("\tpublic String render(");
		sb.append(args);
		sb.append("){\n");
		sb.append("\tStringBuilder _sb = new StringBuilder();\n");
		scanTemplate(sb, str);
		sb.append("\n\treturn _sb.toString();");
		sb.append("\n}\n");
		sb.append("}");
		return sb.toString();
	}

	private void scanTemplate(StringBuilder sb, String str) {
		int idx1 = 0;
		int idx2 = - DELIMETER_CLOSE.length();
		while(true) {
			idx1 = str.indexOf(DELIMETER_OPEN, idx2);
			if(idx1 == -1) {
				appendText(sb, str, idx2, str.length());
				break;
			}
			else {
				appendText(sb, str, idx2, idx1);
			}
			idx2 = str.indexOf(DELIMETER_CLOSE, idx1);
			if(idx2 == -1)
				break;
			appendCode(sb, str, idx1, idx2);
		}
	}

	private void appendText(StringBuilder sb, String str, int idx1, int idx2) {
		sb.append("\t\t_sb.append(\"");
		sb.append(str.substring(idx1 + DELIMETER_CLOSE.length(), idx2).replaceAll("\"", "\\\\\"").replaceAll("(\r\n|\n)", "\\\\n"));
		sb.append("\");\n");
	}

	private void appendCode(StringBuilder sb, String str, int idx1, int idx2) {
		String code = str.substring(idx1 + DELIMETER_OPEN.length(), idx2).trim();
		if(code.startsWith("=")) {
			sb.append("\t\t_sb.append(");
			sb.append(code.substring(1).trim());
			sb.append(");\n");
		}
		else {
			sb.append(code);
		}
	}
}
