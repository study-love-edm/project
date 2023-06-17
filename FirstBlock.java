
public class FirstBlock {
	
	public String initRemove(String line) {
		StringBuilder str = new StringBuilder ();
		line=whiteRemove(line);
		if (line!=null && line.contains("#init"))
				str.append(whiteRemove(line.replaceAll("#init","")));
		else {
			return null;
		}
		return str.toString();
	}
	public String whiteRemove(String line) {
		StringBuilder RemoveWhitespaceString = new StringBuilder();
		boolean RemovePermission =true;
		if (!line.isBlank()) {
			for (int i=0;i<line.length();i++) {
				if (line.charAt(i)==' '&& RemovePermission)
					continue;
				else {
					RemovePermission =false;
					RemoveWhitespaceString.append(line.charAt(i));
				}
			}
		}
		else 
			return null;
		return RemoveWhitespaceString.toString();
	}
	public String findVariableName(String line) {
		StringBuilder str = new StringBuilder();
		if (line!=null) {
			if (!line.isBlank()) {
				for (int i=0;i<line.length();i++) {
					if (line.charAt(i)!=' ')
						str.append(line.charAt(i));
					else
						break;
				}
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
		return str.toString();
	}
	public Integer findVariableValue(String VariableName,String line) {
		StringBuilder str = new StringBuilder();
		String temp=whiteRemove(line.replaceAll(VariableName,""));
		if (temp!=null) {
			if (!temp.isBlank())
			{		
					return Integer.parseInt(temp);
			}
			else
				return null;
		}
		else 
			return null;
	}
	
	public void loadVariable (String line){
		String InitRemoveLine = initRemove(line);
		if(InitRemoveLine!=null) {
			String VariableName = findVariableName(InitRemoveLine);
			if (VariableName.compareToIgnoreCase("array") == 0) {
				Integer ARRAYSIZE = findVariableValue(VariableName, InitRemoveLine);
				if (ARRAYSIZE != null) {
					if (ARRAYSIZE <= 20)
						GlobalVariable.GLOBALARRAYSIZE = ARRAYSIZE;
					else
						GlobalVariable.GLOBALARRAYSIZE = 20;
				} else
					GlobalVariable.GLOBALARRAYSIZE = 20;    //max size
				GlobalVariable.BinaryPosition.put("Data.ARRAY", "0");
				GlobalVariable.GlobalValueReplacement.put("Data.ARRAY", "Data.ARRAY");
			} else {
				GlobalVariable.BinaryPosition.put(VariableName, "1");
				GlobalVariable.Variable_Name[GlobalVariable.counter] = VariableName;
				Integer VariableValue = findVariableValue(VariableName, InitRemoveLine);
				if (VariableValue != null)
					GlobalVariable.Variable_Value[GlobalVariable.counter] = VariableValue;
				else
					GlobalVariable.Variable_Value[GlobalVariable.counter] = 0;
				GlobalVariable.GlobalValueReplacement.put(VariableName, "Data.VARIABLE_VALUE[" + GlobalVariable.counter + "]");
				GlobalVariable.GlobalMapping.put(VariableName, GlobalVariable.counter + "");
				GlobalVariable.counter++;
			}
		}
		else
			return ;
	}

	
	
	
	
	
	public static void main(String[] args) {
		FirstBlock demo = new FirstBlock();
		demo.loadVariable("#init a 1264474747");
		demo.loadVariable("#init b");
//		demo.loadVariable("algo:");		error
		System.out.println(GlobalVariable.GlobalValueReplacement.get("a"));
		System.out.println(GlobalVariable.GlobalValueReplacement.get("b"));
		System.out.println (GlobalVariable.GlobalMapping.get("a"));
	}
}
