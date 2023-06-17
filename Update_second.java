import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Update_second {
	private boolean compare_signal=false;
	private Stack<String> stack = new Stack<>();
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
	public  boolean isContain(String source, String subItem){
        String pattern = "\\b"+subItem+"\\b";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        return m.find();
   }
	
	
	
	public String getCompare (String R_line) {
		StringBuilder comparestring = new StringBuilder("");	//setComapre ()
		StringBuilder pertialAVcode = new StringBuilder();
		StringBuilder key = new StringBuilder();
		String subscript[] = new String[2];
		int counter =0;
		StringBuilder temp = new StringBuilder();
		boolean blockGrid=false;
		boolean stateChange =false;
		boolean firstpush=false;
		if (compare_signal) {
			for (int i=0;i<R_line.length();i++) {
				if (R_line.charAt(i)=='#') {
					key.setLength(0);
					blockGrid=true ;
				}
				else if (blockGrid) {
					if (R_line.charAt(i)==' ') {
						pertialAVcode.append(GlobalVariable.GlobalValueReplacement.get(key.toString()));
						blockGrid=false;
					}
					key.append(R_line.charAt(i));	
				}
				else {
					pertialAVcode.append(R_line.charAt(i));
				}
			}
			comparestring.append("setCompare(");
			for (int i=0;i<pertialAVcode.length();i++) {
				if (pertialAVcode.charAt(i)=='[' || stateChange) {
					if (pertialAVcode.charAt(i)=='[') {
						stack.push("[");
						stateChange=true;
						if (!firstpush) {
							firstpush=true ;
							continue;
						}
					}
					if (pertialAVcode.charAt(i)==']') {
						stack.pop();
						if (!stack.empty())
							temp.append(pertialAVcode.charAt(i));
					}
					else
						temp.append(pertialAVcode.charAt(i));
					if (stack.isEmpty()) {
						subscript[counter++]=temp.toString();
						temp.setLength(0);
						stateChange=false;
						firstpush=false;
					}
				}
			}
			comparestring.append(subscript[0]+","+subscript[1]+",");
			return comparestring.toString();
		}
		else 
			return comparestring.toString();
	}
	
	
	
	public String find_Operator (String R_line ) {
		Pattern pattern = Pattern.compile("[<>=!]+");
		Matcher match = pattern.matcher(R_line);
		match.find();
		return match.group();
	}
	
	public String find_status (String R_Line) {
		StringBuilder Binary=new StringBuilder ("0b");
		String operator = find_Operator(R_Line);
		String array ="Data.ARRAY";
		int arraytracer=0;
		boolean checkoccure=false;
		StringBuilder temp = new StringBuilder();
		String[] BinaryPattern = new String[2];
		int counter=0;
		int loc = R_Line.indexOf(operator);
		if (operator.length()==2){
			loc=loc+1;
			//System.out.println(loc);
		}
		for (int i=0;i<R_Line.length();i++) {
			if (R_Line.charAt(i)=='#') {
				BinaryPattern[counter]="1";
				counter++;
				i=loc;
				arraytracer =0;
				temp.setLength(0);
				if(checkoccure)
					break;
				checkoccure=true;
			}
			else if (array.charAt(arraytracer)==R_Line.charAt(i)) {
				arraytracer++;
				temp.append(R_Line.charAt(i));
				if (temp.length()==array.length()) {
					BinaryPattern[counter]="0";
					counter++;
					i=loc;
					arraytracer =0;
					temp.setLength(0);
					if(checkoccure)
						break;
					checkoccure=true;
				}
			}
			else {
				arraytracer =0;
				temp.setLength(0);
			}
		}
		Binary.append(BinaryPattern[0]+BinaryPattern[1]);
		return Binary.toString();
	}
	
	
	
	public String rewriteGraphicsCode(String R_line) {
		StringBuilder pertialAVcode = new StringBuilder();
		StringBuilder AVcode = new StringBuilder ();
		StringBuilder compareCode=new StringBuilder(); ;
		StringBuilder key=new StringBuilder ();
		String operator;
		String BinaryPos;
		boolean makeCompare =false;
		if (compare_signal) {
			operator = find_Operator (R_line);
			BinaryPos=find_status(R_line);
			compareCode.append(getCompare (R_line));
			compareCode.append(BinaryPos+",\""+operator+"\");");
			makeCompare=true;
		}
	
		boolean blockGrid =false;
		for (int i=0;i<R_line.length();i++) {
			if (R_line.charAt(i)=='#') {
				key.setLength(0);
				blockGrid=true ;
			}
			else if (blockGrid) {
				if (R_line.charAt(i)==' ') {
					pertialAVcode.append(GlobalVariable.GlobalValueReplacement.get(key.toString()));
					blockGrid=false;
				}
				key.append(R_line.charAt(i));	
			}
			else {
				pertialAVcode.append(R_line.charAt(i));
			}
		}
		if (makeCompare) {
			AVcode.append(compareCode);
			AVcode.append("repaint();blocks.delay(delay);");
			compare_signal=false;
			AVcode.append(pertialAVcode);
			makeCompare=false;
		}
		else {
			AVcode.append("repaint();blocks.delay(delay);");
			AVcode.append(pertialAVcode);
		}
			
		return AVcode.toString();
	}
	public String convertAVCode (String line) {
		String R_line = whiteRemove(line);
		if (R_line!=null){
			R_line=whiteRemove(R_line);
			if (R_line.charAt(0)=='@'){
				return R_line.substring(1);
			}
			else if (isContain(R_line,"if")||isContain( R_line,"else if")) {
				compare_signal=true;
			}
		return rewriteGraphicsCode(R_line);
		}
		return "";
	}
}
