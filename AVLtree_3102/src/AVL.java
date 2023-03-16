import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AVL
{
	public static Node array[];
	
	//index counter
	private static int i = 0;
	
	//Insert element like regular BST insert, then arrange
	public static void IN(int key, int data) 
	{
		Node inputNode = new Node(key, data); 
		while(!(array[i] == null)) 
		{
			if(inputNode.compareTo(array[i]) == -1 || inputNode.compareTo(array[i]) == 0) 
			{
				i = 2*i+1;
			}
			else if(inputNode.compareTo(array[i]) == 1) 
			{
				i = 2*i+2;
			}
		}
		array[i] = inputNode;
		arrange();
	}
	
	//minimum key between the two given keys
	public static int RM(int key1, int key2) 
	{		
		return array[0].getData();
	}
	
	//4 cases: RL	RR	LR	LL
	public static void arrange() 
	{
		//rotate right
		for(int j = 0; j < array.length; j++) 
		{
			if(calculateLeftHeight(j) - calculateRightHeight(j) == 2) 
			{
				//RR
				if(calculateLeftHeight(2*j+1) == 1)
				{	
					swap(j, 2*j+1);
					swap(2*j+1, 2*(2*j+1)+1);
					swap(2*(2*j+1)+1, 2*j+2);
				}
				//LR
				if(calculateRightHeight(2*j+1) == 1)
				{	
					swap(2*j+1, 2*(2*j+1)+2);
					swap(2*(2*j+1)+1, 2*(2*j+1)+2);
					swap(j, 2*j+1);
					swap(2*j+1, 2*(2*j+1)+1);
					swap(2*(2*j+1)+1, 2*j+2);
				}
			}
		
			//rotate left
			if(calculateRightHeight(j) - calculateLeftHeight(j) == 2) 
			{
				//LL
				if(calculateRightHeight(2*j+2) == 1)
				{	
					swap(j, 2*j + 1);
					swap(2*j+2, j);
					swap(2*(2*j+1)+2, 2*j+2);
				}
				//LR
				if(calculateLeftHeight(2*j+2) == 1)
				{	
					swap(2*(2*j+1)+1, 2*j+2);
					swap(2*(2*j+1)+1, 2*(2*j+1)+1);
					swap(j, 2*j + 1);
					swap(2*j+2, j);
					swap(2*(2*j+1)+2, 2*j+2);
				}
			}
		}
		//reset index counter
		i = 0;
	}
	
	//return left height of any given node
	public static int calculateLeftHeight(int index) 
	{
		if(index*2+1 >= array.length)
			return 0;
		if(array[index*2 + 1] == null)
			return 0;
		if(2*(2*index+1) + 1 >= array.length)
			return 1;
		if(array[2*(2*index+1) + 1] == null && array[2*(2*index+1) + 2] == null)
				return 1;
		else
				return 2;
	}
	
	//return right height of any given node
	public static int calculateRightHeight(int index) 
	{
		if(index*2+2 >= array.length)
			return 0;
		if(array[index*2 + 2] == null)
			return 0;
		if(2*(2*index+2) + 2 >= array.length)
			return 1;
		if(array[2*(2*index+2) + 1] == null && array[2*(2*index+2) + 2] == null)
				return 1;
		else
				return 2;
	}
	
	//returns index of any given node
	public static int nodeIndex(Node node) 
	{
		for(int i = 0; i < array.length; i++) 
		{
			if(array[i] == null && node == null)
				return i;
			if(array[i] == null || node == null)
				continue;
			if(array[i].compareTo(node) == 0)
				return i;
		}
		return -1;
	}
	
	public static void swap(int a, int b) 
	{
		Node temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		//read file: inputFile.txt
		File inputFile = new File("C:\\Users\\trevs\\eclipse-workspace\\AVLtree_3102\\src\\inputFile.txt");
		Scanner scan = new Scanner(inputFile);
		
		//set height equal to the max height of the AVL tree using formula 1.44 * log_2(n) + 1
		int height = 0;
		if(scan.hasNextInt())
			height = (int) Math.ceil(1.44*Math.log(scan.nextInt())) + 1;
		
		//set size of the array based on the number of instructions given
		//example: if there are 9 instructions, the max amount of nodes to input is 9 
		array = new Node[(int)Math.pow(2, height) - 1];
		
		// going through the input file and executing insert
		// for "IN" and rangeMinimum for "RM"
		while(scan.hasNextLine()) 
		{
			String line[] = scan.nextLine().split(" ");
			for(int i = 0; i < line.length; i++) 
			{
				if(line[i].equals("IN")) 
				{
					IN(Integer.parseInt(line[i + 1]), Integer.parseInt(line[i + 2]));
					break;
				}
				if(line[i].equals("RM")) 
				{ 
					System.out.println(RM(Integer.parseInt(line[i + 1]), Integer.parseInt(line[i + 2])));
					break;
				}
			}
		}		
		scan.close();
	}
	
}

class Node
{

	private int key, data;
	
	public Node(int key, int data) 
	{
		this.key = key;
		this.data = data;
	}
	
	public int compareTo(Node a) 
	{
		if(a == null || this == null) 
		{
			return 0;
		}
		if(this.getKey() > a.getKey())
			return 1;
		if(this.getKey() < a.getKey())
			return -1;
		else
			return 0;
	}
	
	public int getKey() 
	{
		return this.key;
	}
	public int getData() 
	{
		return this.data;
	}
	public void setKey(int key) 
	{
		this.key = key;
	}	
	public void setData(int data) 
	{
		this.data = data;
	}
	
	public String toString() 
	{
		return Integer.toString(this.getKey());
	}
	
}

