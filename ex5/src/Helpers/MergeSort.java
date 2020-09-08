package Helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

/*
a class that implements merge sort
 */
public class MergeSort {

	/**
	 * divide and conquer method to split an array to singletons and sort it
	 * @param data - data to sort
	 * @param left - left index
	 * @param mid - middle index
	 * @param right - right index
	 * @param comparator - comparator to compare with
	 */
	private static void merge(ArrayList<File> data, int left, int mid, int right, Comparator<File> comparator) {
		int sizeLeft = mid - left + 1;
		int sizeRight = right - mid;
		File[] ArrayLeft = new File[sizeLeft];
		File[] ArrayRight = new File[sizeRight];

		for (int i = 0; i < sizeRight; i++) {
			ArrayRight[i] = data.get(i + mid + 1);
		}
		// copying the data to the two arrays
		int startPosition = 0;
		if (sizeRight >= 0) {
			for(int i=0;i<sizeRight;i++){
				ArrayRight[i] = data.get(mid+i+1);
			}
		}
		if (sizeLeft >= 0) {
			for(int i=0;i<sizeLeft;i++){
				ArrayLeft[i] = data.get(left+i);
			}		} //sorting
		int leftIndex = 0, rightIndex = 0, resultIndex = left;
		while (leftIndex < sizeLeft && rightIndex < sizeRight) {
			if (comparator.compare(ArrayLeft[leftIndex], ArrayRight[rightIndex]) < 0) {
				data.set(resultIndex, ArrayLeft[leftIndex]);
				leftIndex++;
			} else {
				data.set(resultIndex, ArrayRight[rightIndex]);
				rightIndex++;
			}
			resultIndex++;
		}
		// copying leftover data
		while (leftIndex < sizeLeft) {
			data.set(resultIndex, ArrayLeft[leftIndex]);
			leftIndex++;
			resultIndex++;
		}
		while (rightIndex < sizeRight) {
			data.set(resultIndex, ArrayLeft[rightIndex]);
			rightIndex++;
			resultIndex++;
		}
	}

	/**
	 * merge sort algorithm
	 * @param data - data to sort
	 * @param left - left index
	 * @param right - right index
	 * @param comparator - an object to compare with
	 */
	public void mergeSort(ArrayList<File> data, int left, int right, Comparator<File> comparator) {
		if (left < right) {
			int mid = (right + left) / 2;
			mergeSort(data, left, mid, comparator);
			mergeSort(data, mid + 1, right, comparator);
			merge(data, left, mid, right, comparator);
		}
	}
}
