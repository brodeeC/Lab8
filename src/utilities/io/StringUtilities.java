package utilities.io;

public class StringUtilities
{
	/**
	 * @param level -- levels to which we indent
	 * @return a string containg the corresponding number of spaces requested
	 */
	public static String indent(int level)
	{
		String str = "    ";
		return repeat(str, level);
	}

	private static String repeat(String str, int level){
		for (int i = 0; i < level; i++){
			str += str;
		}
		return str;
	}
}
