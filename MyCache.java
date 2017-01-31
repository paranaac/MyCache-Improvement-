/*******************************************************************
@name: Alejandro Parana
@date: 10/27/2015

You are required to improve the two functions provided.
Please note that the implementation you are given is about as bad as it can get. Just about anything you try will make it better.

Your goal is to reduce caches from main mamory and saves to main memory by using a cache. 
You may use other functions and variables to try and improve your algorithm.

To reach your goal, this is what you have to work with:
  - You have two caches, keys and vals.
  - The keys cache is initially all -1, meaning nothing is stored.
  - As you cache values from main memory, the keys cache will hold the address from memory of the value copied.
  - The vals cache is initially all 0.
  - As you cache values from main memory, the vals cache will hold the values from memory.

How you work with main memory...
To move a value from main memory to cache, use the cache function:  cache(MainMemoryAddress, CacheAddress);   Note: Both addresses are integers.
To move a value from cache to main memory, use the save function:   save(CacheAddress);
You only need the cache address because the keys cache has the main memory address and the vals cache has the value.

You can directly work with your cache using read and write: keys.read(2);    This will return the value at address 2 in keys.
You can read from vals also: vals.read(2); 
vals.write(1,16);  This writes a value of 16 to address 1 in vals.
You can write to keys also: keys.write(1,16);

Note: You are trying to reduce the total number of cache and save calls which use main memory. You can use as many read/write calls as you like on your local cache.
********************************************************************/

public class MyCache extends CacheSim
{
	int cacheSize = keys.getSize();
	static int index = 0;
	int[] modified = new int[cacheSize];

	/**
	* This function reads a value from main memory. 
	* However, you can't access main memory directly.
	* So, you either need to find the value in your cache, or you cache it from memory. 
	* Once you have it in cache, you can read the value.
	* @param address The address in main memory
	* @return The value from main memory
	*/
	public int read(int address)
	{
		System.out.print("Reading from address " + address + " ");

		for (int i = 0; i <= cacheSize; i++)
		{
			if ((keys.read(i) == address) && (modified[i] == 0))
			{
				System.out.print("Value: " + vals.read(i) + "\n");
				//System.out.println("Found in cache");
				return vals.read(i);
			}
		}
		if (index >= cacheSize)
		{
			index = 0;
		}
		cache(address,index);
		System.out.print("Value: " + vals.read(index) + "\n");
		return vals.read(index++);
	}

	/**
	* This function saves a value to memory. However, you can't access main memory directly. 
	* You can save the value in your cache. Then, you can write it from your cache to main memory.
	* @param address The address in main memory
	* @param value	The value to write
	*/
	public void write(int address, int value)
	{
		System.out.println("Writing "+ value + " to address " + address);
		if (index >= cacheSize)
		{
			index = 0;
		}
		boolean exists = false;
		for (int i = 0; i < cacheSize; i++)
		{
			if ((keys.read(i) == address) && (vals.read(i) == value))
			{
				//System.out.println("Don't Write");
				exists = true;
				break;
			}
			else if ((keys.read(i) == address) && (vals.read(i) != value))
			{
			    modified[i] = 1;
				keys.write(i, address);
				vals.write(i, value);
				save(i);
				//System.out.println("Replace Value");
				exists = true;
				break;
			}
				
		}
		if (!exists)
		{
			keys.write(index, address);
			vals.write(index, value);
			save(index);
			modified[index] = 0;
			index++;
		}	
	}

	/**
	* This function is called when the test is complete.
	* Use this to take care of any work you haven't finished.
	*/
	public void uncache()
	{
	}

	/**
	* This constructor is required. You must have it.
	* If you REALLY want to, you can initialize variables in here.
	* However, the call to super(i,j) is mandatory.
	*/
	public MyCache(int i, int j) { super(i,j); }
}

		