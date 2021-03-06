/*******************************************************************
This is the main executable.
Do not edit this file.
Edit the file named MyCache.java
Then, compile your program: javac *.java
Then, run this program: java Project2
You can override the default values.
By default:
 The size of Main Memory is 8 integers.
 The size of Cache is 2 integers.
 The file of test read/write commands is cachetest.txt
Override these on the command line:
 java Project2 -m 128 -c 16 -t mynewtest.txt
That will change memory size to 128, cache to 16, and the test file.

When you run this, you will see a report of caches and saves.
You want to reduce those as much as possible.

Please note that when your program is tested, it will use a much
larger size of memory/cache and a much longer set of read/write
requests - specifically designed to trick your algorithm into making
poor cache decisions.
********************************************************************/

import java.io.BufferedReader;
import java.io.FileReader;

public class Project2
{
	public static void main(String[] args) throws Exception
	{
		int memsize=8;
		int cachesize=2;
		String testfile="cachetest.txt";
		for(int i=0; i<args.length-1; i++)
		{
			if(args[i].equals("-m")) memsize=Integer.parseInt(args[i+1]);
			if(args[i].equals("-c")) cachesize=Integer.parseInt(args[i+1]);
			if(args[i].equals("-t")) testfile=args[i+1];
		}
		MyCache sim = new MyCache(memsize, cachesize);

		BufferedReader in = new BufferedReader(new FileReader(testfile));
		String b;
		String[] a;
		while((b = in.readLine()) != null)
		{
			b = b.toLowerCase().trim();
			a = b.split(",");
			System.out.println(a[0]);
			if(a[0].equals("write"))
			{
				for(int i=2; i<a.length; i++)
					sim.write(Integer.parseInt(a[1])+i-2, Integer.parseInt(a[i]));
			}
			if(a[0].equals("read"))
			{
				for(int i=0; i<Integer.parseInt(a[2]); i++)
					sim.read(Integer.parseInt(a[1])+i);
			}
		}
		in.close();
		sim.report();
	}

}
