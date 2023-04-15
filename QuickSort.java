package Assignment3_Final;

public class QuickSort{

	/**
	 * This method is used to swat the two elements
	 * 
	 * @param arr[],i,j
	 * @return void
	 */
	private static void swap(long[] arr, int i, int j)
	{
		long temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	/**
	 * This method is used to partition the elements into two parts, smaller and larger than the pivot element
	 * 
	 * @param arr[],low,high
	 * @return int
	 */
	private static int partition(long[] arr, int low, int high)
	{

		long pivot = arr[high];

		int i = (low - 1);
		for(int j = low; j <= high - 1; j++)
		{
			if (arr[j] < pivot)
			{
				i++;
				swap(arr, i, j);
			}
		}
		swap(arr, i + 1, high);
		return (i + 1);
	}

	/**
	 * This method provides the pivot element by calling the partition method and then recursively calls itself until the low is less than high
	 * 
	 * @param arr[],low,high
	 * @return void
	 */
	private static void quickSort(long[] arr, int low, int high)
	{
		if (low < high)
		{
			int pi = partition(arr, low, high);
			quickSort(arr, low, pi - 1);
			quickSort(arr, pi + 1, high);
		}
	}
	
	/**
	 * This method acts as a wrapper to the quicksort method
	 * 
	 * @param arr[]
	 * @return long[]
	 */
	public static long[] getSortedArr(long[] arr) {
		int n = arr.length;
		quickSort(arr, 0, n - 1);
		return arr;
	}
}
