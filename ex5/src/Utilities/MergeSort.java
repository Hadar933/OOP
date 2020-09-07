package Utilities;

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
	private static void merge(File[] data, int left, int mid, int right, Comparator<File> comparator) {
		int sizeLeft = mid - left + 1;
		int sizeRight = right - mid;
		File[] ArrayLeft = new File[sizeLeft];
		File[] ArrayRight = new File[sizeRight];

		for (int i = 0; i < sizeRight; i++) {
			ArrayRight[i] = data[i + mid + 1];
		}
		// copying the data to the two arrays
		int startPosition = 0;
		if (sizeRight >= 0) {
			System.arraycopy(data, mid + 1, ArrayRight, startPosition, sizeRight);
		}
		if (sizeLeft >= 0) {
			System.arraycopy(data, left, ArrayLeft, startPosition, sizeLeft);
		} //sorting
		int leftIndex = 0, rightIndex = 0, resultIndex = left;
		while (leftIndex < sizeLeft && rightIndex < sizeRight) {
			if (comparator.compare(ArrayLeft[leftIndex], ArrayRight[rightIndex]) < 0) {
				data[resultIndex] = ArrayLeft[leftIndex];
				leftIndex++;
			} else {
				data[resultIndex] = ArrayRight[rightIndex];
				rightIndex++;
			}
			resultIndex++;
		}
		// copying leftover data
		while (leftIndex < sizeLeft) {
			data[resultIndex] = ArrayLeft[leftIndex];
			leftIndex++;
			resultIndex++;
		}
		while (rightIndex < sizeRight) {
			data[resultIndex] = ArrayLeft[rightIndex];
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
	public void mergeSort(File[] data,int left,int right,Comparator<File> comparator){
		if(left<right){
			int mid = (right+left)/2;
			mergeSort(data,left,mid,comparator);
			mergeSort(data,mid+1,right,comparator);
			merge(data,left,mid,right,comparator);
		}
	}
}
