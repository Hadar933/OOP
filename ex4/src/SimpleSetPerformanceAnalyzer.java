import java.util.ArrayList;

/**
 * a class that tests the runtime of the hash sets
 */
public class SimpleSetPerformanceAnalyzer {
	/*
	general constants for time unit conversions and percentage display
	 */
	private static final int NUM_OF_SETS = 10;
	private static final int MILLISECONDS = 1000000;
	private static final int PERCENTAGE = 10000;

	/*
	some relevant indexes to the sets array we operate on
	 */
	private static final int DATA_1_LINKED_LIST_INDEX = 3;
	private static final int DATA_2_LINKED_LIST_INDEX = 8;
	private static final int DATA_1_START_INDEX = 0;
	private static final int DATA_1_END_INDEX = 4;
	private static final int DATA_2_START_INDEX = 5;
	private static final int DATA_2_END_INDEX = 9;

	/*
	number of iterations we perform for contain:
	 */
	private static final int LINKED_LIST_ITERATIONS = 7000;
	private static final int GENERAL_ITERATIONS = 70000;

	/*
	 * all the sets that will be used in the tests, represented as an array
	 */
	private final SimpleSet[] sets = new SimpleSet[NUM_OF_SETS];

	public SimpleSetPerformanceAnalyzer() {
		// data 1 :
		this.sets[0] = new OpenHashSet();
		this.sets[1] = new ClosedHashSet();
		this.sets[2] = new CollectionFacadeSet(new java.util.TreeSet<>());
		this.sets[3] = new CollectionFacadeSet(new java.util.LinkedList<>());
		this.sets[4] = new CollectionFacadeSet(new java.util.HashSet<>());

		// data 2:
		this.sets[5] = new OpenHashSet();
		this.sets[6] = new ClosedHashSet();
		this.sets[7] = new CollectionFacadeSet(new java.util.TreeSet<>());
		this.sets[8] = new CollectionFacadeSet(new java.util.LinkedList<>());
		this.sets[9] = new CollectionFacadeSet(new java.util.HashSet<>());
	}


	/*
	a basic method that prints the percentage of the test passed
	(prints a message every 10%)
	*/
	private void printPercentage(int index, int dataSize) {
		if (index % PERCENTAGE == 0) {// printing precentage
			System.out.println((float) index / dataSize * 100 + "%");
		}
	}

	/*
	 * a loop that performs contain operation on the data
	 * @param start - index to start from in sets array
	 * @param end - index to end in sets array
	 * @param linkedListIndex - either 3 or 8 (for data1 and data2 respectively)
	 * @param item - item to check contains on
	 */
	private void containsLoop(int start, int end, int linkedListIndex, String item) {
		for (int i = start; i < end + 1; i++) {
			System.out.println("data structure number " + i);
			if (i == linkedListIndex) {
				long timeBefore = System.nanoTime();
				for (int j = 0; j < LINKED_LIST_ITERATIONS; j++) {
					sets[i].contains(item);
					printPercentage(j, LINKED_LIST_ITERATIONS);
				}
				long difference = System.nanoTime() - timeBefore;
				System.out.println("Avg Time: " + (difference / LINKED_LIST_ITERATIONS) + "ns\n");
			} else { // not a linked list
				warmUp(start, end, item, i);
				long timeBefore = System.nanoTime();
				for (int j = 0; j < GENERAL_ITERATIONS; j++) {
					sets[i].contains(item);
					printPercentage(j, GENERAL_ITERATIONS);
				}
				long difference = System.nanoTime() - timeBefore;
				System.out.println("Avg Time: " + (difference / GENERAL_ITERATIONS) + "ns\n");
			}
		}
	}

	/*
	 * performs 70,000 of unmeasured contains on every data that is not a linked list
	 * @param start - start index in sets array
	 * @param end - end index in sets array
	 * @param item - item to check contain on
	 */
	private void warmUp(int start, int end, String item, int dataIndex) {
		System.out.println("Warming up");
		for (int j = 0; j < GENERAL_ITERATIONS; j++) {
			sets[dataIndex].contains(item);
		}
		System.out.println("finished warm up");
	}

	/*
	 * checks the runtime for the contains method
	 * @param item - some item to check on
	 * @param fileName - data1 or data2
	 */
	private void containsRunTime(String item, String fileName) {
		System.out.println("Contains " + item + " in file " + fileName);
		if (fileName.equals("data1.txt")) {
			containsLoop(DATA_1_START_INDEX, DATA_1_END_INDEX, DATA_1_LINKED_LIST_INDEX, item);
		} else { //data 2
			containsLoop(DATA_2_START_INDEX, DATA_2_END_INDEX, DATA_2_LINKED_LIST_INDEX, item);
		}
	}

	/*
	 * a method that performs addition, according to the given file data
	 * @param start - start index to the sets array (0 or 5)
	 * @param end - end index to the sets array (4 or 9)
	 * @param data - data1 or data2 represented as an array
	 */
	private void additionLoop(int start, int end, String[] data) {
		for (int i = start; i < end + 1; i++) {
			long timeBefore = System.nanoTime();
			System.out.println("data structure number " + i);
			for (int j = 0; j < data.length; j++) {
				sets[i].add(data[j]);
				printPercentage(j, data.length);
			}
			long difference = System.nanoTime() - timeBefore;
			System.out.println("Insert time for dast " + i + "= " + (difference / MILLISECONDS) + "ms\n");
		}
	}

	/*
	 * @param fileName - data1 or data2
	 * @param dataNumber - 1 or 2
	 */
	private void additionRunTime(String fileName) {
		String[] data = Ex4Utils.file2array(fileName);
		System.out.println("Inserting to file " + fileName + "\n");
		if (fileName.equals("data1.txt")) {
			additionLoop(DATA_1_START_INDEX, DATA_1_END_INDEX, data);
		} else { //data2.txt
			additionLoop(DATA_2_START_INDEX, DATA_2_END_INDEX, data);
		}

	}


	/**
	 * main method to run the tests
	 */
	public static void main(String[] args) {
		String data1 = "data1.txt";
		String data2 = "data2.txt";
		SimpleSetPerformanceAnalyzer analyzer = new SimpleSetPerformanceAnalyzer();
		//step 1
		analyzer.additionRunTime(data1);
		//step 2
		analyzer.additionRunTime(data2);
		//step 3
		analyzer.containsRunTime("hi", data1);
		//step 4
		analyzer.containsRunTime("-13170890158", data1);
		//step 5
		analyzer.containsRunTime("23", data2);
		//step 6
		analyzer.containsRunTime("hi", data2);

	}
}


