//Name: Spinosa, Trevor
//Email: tspino2@lsu.edu
//Project: PA-1 (Multithreading)
//Instructor: Feng Chen
//Class: cs4103-sp21
//Login ID: cs4103ad

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main 
{
	public static File file;
	public static Scanner s;
	public static int[][] array;
	
	//creates multiple threads
	public static RowThread thread1;
	public static ColThread thread2;
	public static SubThread[] thread3;
	
	public static Semaphore sem;
	
	public static void main(String[] args) throws InterruptedException 
	{
		
		if(args.length != 1) 
		{
			System.out.println("Please give exactly one file path as an argument");
			System.exit(0);
		}
		sem = new Semaphore(1);
		findInputFile(args[0]);
		createSodukoArray(9, 9);
		
		thread3 = new SubThread[9];
		
		//starts 1st thread
		thread1 = new RowThread(sem);
		thread1.start();
		
		//starts 2nd thread
		thread2 = new ColThread(sem);
		thread2.start();
		
		//synchronizes threads
		thread1.join();
		thread2.join();
		
		int counter = 0;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				//starts subgrid threads
				thread3[counter] = new SubThread(i, j, counter, sem);
				thread3[counter].start();
				
				//synchronizes threads
				thread3[counter].join();
				counter++;
			}
		}
		
	}
	
	//reads given sudoku file
	public static void findInputFile(String s) 
	{
	    file = new File(s);
	}
	
	//uses information in the sudoku file to create a 2D array containing the grid
	public static void createSodukoArray(int w, int h) 
	{
		try {
			s = new Scanner(file);
			array = new int[w][h];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < w; i++)
			for(int j = 0; j < h; j++)
				array[i][j] = s.nextInt();
	}
	
}

class RowThread extends Thread
{
	Semaphore sem;
	public RowThread(Semaphore sem) 
	{
		this.sem = sem;
	}
	
	//checks each individual row in the sudoku array
	public void run()
	{
		//uses semaphore to control synchronization of threads
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < 9; i++)
		{
			boolean valid = true;
			for(int j = 0; j < 9; j++)
				for(int k = j + 1; k < 9; k++)
				{
					if(Main.array[i][j] == Main.array[i][k])
						valid = false; 
				}
			if(valid)
				System.out.println("Thread " + Thread.currentThread().getId() + ", Row " + (i+1) + ", Valid");
			else
				System.out.println("Thread " + Thread.currentThread().getId() + ", Row " + (i+1) + ", Invalid");
			sem.release();
		}	
    }
}

class ColThread extends Thread
{
	Semaphore sem;
	public ColThread(Semaphore sem) 
	{
		this.sem = sem;
	}
	
	//checks each individual column in the sudoku array
	public void run()
	{
		//uses semaphore to control synchronization of threads
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < 9; i++)
		{
			boolean valid = true;
			for(int j = 0; j < 9; j++)
				for(int k = j + 1; k < 9; k++)
				{
					if(Main.array[j][i] == Main.array[k][i])
						valid = false; 
				}
			if(valid)
				System.out.println("Thread " + Thread.currentThread().getId() + ", Column " + (i+1) + ", Valid");
			else
				System.out.println("Thread " + Thread.currentThread().getId() + ", Column " + (i+1) + ", Invalid");
			sem.release();
		}
    }
}

class SubThread extends Thread
{
	Semaphore sem;
	int x, y, z;
	int[] subarray;
	
	//creates a thread with a specific task using gridpoints
	public SubThread(int x, int y, int z, Semaphore sem) 
	{
		this.sem = sem;
		this.x = x*3;
		this.y = y*3;
		this.z = z;
		subarray = new int[9];
	}
	
	//checks each individual subgrid in the sudoku array
	public void run()
	{
		//uses semaphore to control synchronization of threads
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int counter = 0;
		for(int j = 0; j < 3; j++)
			for(int k = 0; k < 3; k++)
				subarray[counter++] = Main.array[x+j][y+k];
		
		boolean valid = true;
		for(int i = 0; i < 9; i++)
		{
			for(int j = i+1; j < 9; j++) 
			{
				if(subarray[i] == subarray[j]) 
					valid = false;
			}
		}
		if(valid)
			System.out.println("Thread " + Thread.currentThread().getId() + " " + (z+3) + ", Subgrid R" + (x+1) + (x+2) + (x+3) + "-C" + (y+1) + (y+2) + (y+3) + ", Valid");
		else
			System.out.println("Thread " + Thread.currentThread().getId() + " " + (z+3) + ", Subgrid R" + (x+1) + (x+2) + (x+3) + " C-" + (y+1) + (y+2) + (y+3) + ", Invalid");
		sem.release();
	}
}
