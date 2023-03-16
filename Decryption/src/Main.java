public class Main
{

	public static void main(String args[]) 
	{
		System.out.println(decrypt(args[0]));
	}
	
	public static String decrypt(String enc) 
	{
		int largestECount = 0;
		String newString = "";
		String mostProbable = "";
		for(int j = 0; j < 26; j++) 
		{
			for(int i = 0; i < enc.length(); i++) 
			{
				if(enc.charAt(i) == ' ') 
				{
					newString = newString + " ";
				}
				else if((char)(enc.charAt(i) + j) < 122)
					newString = newString + (char)(enc.charAt(i) + j);
				else 
					newString = newString + (char)('`' + (char)(enc.charAt(i) + j) - 122);
			}
			int ecount = 0; 
			for(int k = 0; k < newString.length(); k++) 
			{
				if(newString.charAt(k) == 'e')
					ecount++;
			}
			if(ecount > largestECount) 
			{
				largestECount = ecount;
				mostProbable = newString;
			}
			newString = "";
			ecount = 0;
		}	
		return mostProbable;
	}
	
}
