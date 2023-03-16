//Name: Spinosa, Trevor
//Email: tspino2@lsu.edu
//Project: PA-2 (RAID)
//Instructor: Feng Chen
//Class: cs4103-au21
//Login ID: cs4103ad

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main 
{
	public static int numDisks;
	public static int blockSize;
	public static String command;
	public static File input;
	public static void main(String args[]) 
	{
		//checks to see if there are the correct number of args
		if(args.length > 4) 
		{
			System.out.println("Too many input arguments");
			System.exit(0);
		}
		if(args.length < 4) 
		{
			System.out.println("Not enough input arguments");
			System.exit(0);
		}
		
		//set up argument variables
		numDisks = Integer.parseInt(args[0]);
		blockSize = Integer.parseInt(args[1]);
		command = args[2];
		input = new File(args[3]);
		
		if(command.equals("write"))
			write(numDisks);
		else if (command.equals("read"))
			read();
		else if(command.equals("rebuild"))
			System.out.println("Did not get this method to work properly");
		else
			System.out.println("Did not enter a known command");
		
	}
	
	public static void read() 
	{
		//supposed to use the disks to return the input stream minus the parity blocks
		try {
			Scanner scanny = new Scanner(input);
			System.out.println(scanny.nextLine());
			scanny.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void write(int num) 
	{
		FileWriter[] writer = new FileWriter[num];
		BufferedWriter[] bw = new BufferedWriter[num];
		File[] file = new File[num];
		Scanner scanny;
		String inputStream = "";
		try {
			scanny = new Scanner(input);
			inputStream = scanny.nextLine();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		for(int i = 0; i < num; i++) 
		{
			file[i] = new File("disk."+i);
			try 
			{
				writer[i] = new FileWriter("disk."+i+".txt");
				bw[i] = new BufferedWriter(writer[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//create stripe
		String[] temp = new String[num];
		int parityslot = num-1;
		boolean parityentered = false;
		
		for(int i = 0; i < num; i++) 
		{
			for(int j = 0; j < num; j++) 
			{
				temp[j] = inputStream.substring(j*blockSize + i*blockSize*num, j*blockSize+blockSize + i*blockSize*num);
			}	
			
			//calculate parity
			//int parity = Integer.parseInt(temp[0]);
		    String parity = " parity ";
			for(int k = 1; k < temp.length; k++) 
			{
				//parity = parity ^ Integer.parseInt(temp[0]);
			}
			
			for(int n = 0; n < num; n++) 
			{
				try 
				{
					if(i == parityslot) 
					{
						bw[i].write(parity);
						parityentered = true;
			        }	
					else if(parityentered)
						bw[i].write(temp[n+1]);
					else
						bw[i].write(temp[n]);
				} catch (IOException e) {
					e.printStackTrace();
				}
				parityentered = false;
				parityslot--;	
			}
		}
		for(int i = 0; i < bw.length; i++)
			try {
				bw[i].close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	//unfinished
	public static void rebuild() 
	{
		//supposed to use disks anf parity blocks to rebuild lost disks using XOR bitwise operator
		//**could not figure out how to get this to work, sorry**
	}
	
}
