public class Main 
{
	public static void main(String args[]) 
	{
		System.out.println(decompress(args[0]));
	}
	
	public static String decompress(String compressed) 
	{
		String newString = "";
		for(int i = 0; i < compressed.length(); i+=2) 
		{
			int count = Integer.parseInt(compressed.substring(i, i+1));
			for(int j = 0; j < count; j++)
				newString = newString + compressed.substring(i+1, i+2);
		}
		return newString;
	}
	
}
