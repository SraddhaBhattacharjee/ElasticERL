package Assignment3_Final;

class MergeSort
{
	/**
	 * This method is used to merge the values from temporary arrays to the original array by comparing the values present in temporary arrays.
	 * 
	 * @param arr[],left,middle,right
	 * @return void
	 */
	private static void merge(long arr[], int l, int m, int r)
	{
		int n1 = m - l + 1;
		int n2 = r - m;
		
		long L[] = new long[n1];
		long R[] = new long[n2];

		for (int i = 0; i < n1; ++i)
			L[i] = arr[l + i];
		for (int j = 0; j < n2; ++j)
			R[j] = arr[m + 1 + j];

		int i = 0, j = 0;
		int k = l;
		while (i < n1 && j < n2) {
			if (L[i] <= R[j]) {
				arr[k] = L[i];
				i++;
			}
			else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}

		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}

		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}

	/**
	 * This method is splits the array until its size becomes one and then calls the merge method
	 * 
	 * @param arr[],left,right
	 * @return void
	 */
	private static void sort(long arr[], int l, int r)
	{
		if (l < r) {
			int m =l+ (r-l)/2;
			sort(arr, l, m);
			sort(arr, m + 1, r);
			merge(arr, l, m, r);
		}
	}
	
	/**
	 * This static method is used to call the sort method
	 * 
	 * @param arr
	 * @return long[]
	 */
	public static long[] getSortedArr(long[] arr) {
		sort(arr, 0, arr.length - 1);
		return arr;
	}

}
