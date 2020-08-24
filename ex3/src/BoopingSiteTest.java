import org.junit.*;

import static org.junit.Assert.*;

import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.Random;

public class BoopingSiteTest {
	private final static int ARRAY_SIZE = 10;
	private final static int LATITUDE_THRESHOLD = 90;
	private final static int LONGITUDE_THRESHOLD = 180;
	private static final String bigData = "hotels_dataset.txt";
	private static final String smallData = "hotels_tst1.txt";
	private static final String emptyData = "hotels_tst2.txt";
	private static final String[] badCities = {"Tel Aviv", "Jerusalem", "madeUpCity"}; //not in the data
	private static final String[] goodCities = {"manali", "dwarka", "porbandar", "kutch", "somnath",
			"malvan", "mumbai"}; //in data

	private BoopingSite bigSite;
	private Hotel[] bigHotels;
	private BoopingSite smallSite;
	private Hotel[] smallHotels;
	private BoopingSite emptySite;
	private Hotel[] emptyHotels;

	/**
	 * initializes the data before the tests begin
	 */
	@Before
	public void initialize() {
		bigSite = new BoopingSite(bigData);
		smallSite = new BoopingSite(smallData);
		emptySite = new BoopingSite(emptyData);

		bigHotels = HotelDataset.getHotels(bigData);
		smallHotels = HotelDataset.getHotels(smallData);
		emptyHotels = HotelDataset.getHotels(emptyData);
	}

	/**
	 * tests the initialization process
	 */
	@Test
	public void testInitialization() {
		assertNotNull("big site initialization failed", bigSite);
		assertNotNull("small site initialization failed", smallSite);
		assertNotNull("empty site initialization failed", emptySite);
	}

	/**
	 * tests the file with no data (tst2)
	 */
	@Test
	public void testEmpty() {
		for (String city : goodCities) {
			Hotel[] hotels = emptySite.getHotelsInCityByRating(city);
			assertEquals(0, hotels.length);
			hotels = emptySite.getHotelsByProximity(0, 0);
			assertEquals(0, hotels.length);
			hotels = emptySite.getHotelsInCityByProximity(city, 0, 0);
			assertEquals(0, hotels.length);
		}

	}

	/*
	 * the main function that tests the get hotels in city by rating method
	 * @param site - one of the data sets (small or large)
	 * @param city - the city to check on
	 */
	private void testHelperGetHotelInCityByRating(BoopingSite site, String city) {
		Hotel[] sorted = site.getHotelsInCityByRating(city);
		Hotel currHotel = sorted[0];
		for (Hotel hotel : sorted) {
			if (!currHotel.equals(hotel)) {
				if (currHotel.getStarRating() == hotel.getStarRating()) {
					String property1 = currHotel.getPropertyName();
					String property2 = hotel.getPropertyName();
					assertTrue("Badly sorted: not according to property name",
							   property1.compareTo(property2) <= 0);
				}
				assertTrue("Badly sorted: not according to ratings",
						   currHotel.getStarRating() >= hotel.getStarRating());
			}
			currHotel = hotel;
		}
	}

	/**
	 * tests the GetHotelsInCityByRating method for both big and small data
	 */
	@Test
	public void testGetHotelsInCityByRating() {
		for (String city : badCities) { // testing for cities that arent in the data
			Hotel[] smallSorted = smallSite.getHotelsInCityByRating(city);
			assertEquals(0, smallSorted.length);
			Hotel[] bigSorted = bigSite.getHotelsInCityByRating(city);
			assertEquals(0, bigSorted.length);
		}
		// now checking the small data (city = manali)
		testHelperGetHotelInCityByRating(smallSite, goodCities[0]);

		for (String city : goodCities) { // checking large data, with various cities
			testHelperGetHotelInCityByRating(bigSite, city);
		}
	}

	/*
	calculates distance between two dots represented by (x1,y1) and (x2,y2)
	 */
	private double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
	}

	/*
	 * the main function that tests the get hotels in city by rating method
	 * @param site - one of the data sets (small or large)
	 * @param city - the city to check on
	 */
	private void testHelperGetHotelsProximity(BoopingSite site, double latitude, double longitude) {
		Hotel[] sorted = site.getHotelsByProximity(latitude, longitude);
		Hotel curr = sorted[0];
		for (Hotel hotel : sorted) {
			if (!curr.equals(hotel)) {
				double distance1 = distance(latitude, longitude, curr.getLatitude(), curr.getLongitude());
				double distance2 = distance(latitude, longitude, hotel.getLatitude(), hotel.getLongitude());
				if (distance1 == distance2) {
					int poi1 = curr.getNumPOI();
					int poi2 = hotel.getNumPOI();
					assertTrue("Badly sorted: not according to POI", poi1 >= poi2);
				}
				assertTrue("Badly sorted: not according to proximity",
						   distance1 <= distance2);
			}
			curr = hotel;
		}
	}

	/*
	 * @return - an array of randomly generated doubles, within some range [minValue,maxValue]
	 */
	private double[] doublesGenerator(int minValue, int maxValue) {
		double[] array = new double[ARRAY_SIZE]; // will contain 10 random double values
		Random r = new Random();
		for (int i = 0; i < array.length; i++) {
			array[i] = minValue + (maxValue - minValue) * r.nextDouble();
		}
		return array;
	}

	/**
	 * tests the hotels proximity method by iterating over various coordinates
	 */
	@Test
	public void testGetHotelsProximity() {
		// first we check valid coordinates:
		double[] longitudeValues = doublesGenerator(-LONGITUDE_THRESHOLD, LONGITUDE_THRESHOLD);
		double[] latitudeValues = doublesGenerator(-LATITUDE_THRESHOLD, LATITUDE_THRESHOLD);
		for (int i = 0; i < ARRAY_SIZE; i++) {
			testHelperGetHotelsProximity(smallSite, latitudeValues[i], longitudeValues[i]);
			testHelperGetHotelsProximity(bigSite, latitudeValues[i], longitudeValues[i]);
		}
		// now we check invalid coordinates
		double[] invalidValues = doublesGenerator(LONGITUDE_THRESHOLD, 2 * LONGITUDE_THRESHOLD);
		for (int i = 0; i < ARRAY_SIZE; i++) {
			assertEquals(0, smallSite.getHotelsByProximity(invalidValues[i], invalidValues[i]).length);
			assertEquals(0, bigSite.getHotelsByProximity(invalidValues[i], invalidValues[i]).length);
		}
	}


	private void testHelperGetHotelsInCityByProximity(BoopingSite site, double latitude, double longitude,
													  String city) {
		Hotel[] sorted = site.getHotelsInCityByProximity(city, latitude, longitude);
		Hotel curr = sorted[0];
		for (Hotel hotel : sorted) {
			if (!curr.equals(hotel)) {
				double distance1 = distance(latitude, longitude, curr.getLatitude(), curr.getLongitude());
				double distance2 = distance(latitude, longitude, hotel.getLatitude(), hotel.getLongitude());
				if (distance1 == distance2) {
					int poi1 = curr.getNumPOI();
					int poi2 = hotel.getNumPOI();
					assertTrue("Badly sorted: not according to POI", poi1 >= poi2);
				}
				assertTrue("Badly sorted: not according to proximity",
						   distance1 <= distance2);
			}
			curr = hotel;
		}
	}

	@Test
	public void testGetHotelsInCityByProximity() {

		//  invalid cities
		for (String city : badCities) {
			assertEquals(0, bigSite.getHotelsInCityByProximity(city, 0, 0).length);
			assertEquals(0, smallSite.getHotelsInCityByProximity(city, 0, 0).length);
		}

		// invalid coordinates
		double[] invalidValues = doublesGenerator(LONGITUDE_THRESHOLD, 2 * LONGITUDE_THRESHOLD);
		for (int i = 0; i < ARRAY_SIZE; i++) {
			assertEquals(0, smallSite.getHotelsInCityByProximity(goodCities[0], invalidValues[i],
																 invalidValues[i]).length);
			assertEquals(0, bigSite.getHotelsInCityByProximity(goodCities[0], invalidValues[i],
															   invalidValues[i]).length);
		}

		double[] longitudeValues = doublesGenerator(-LONGITUDE_THRESHOLD, LONGITUDE_THRESHOLD);
		double[] latitudeValues = doublesGenerator(-LATITUDE_THRESHOLD, LATITUDE_THRESHOLD);
		for (double latitude : latitudeValues) {
			for (double longitude : longitudeValues) {
				// checking the small data ( city = manali )
				testHelperGetHotelsInCityByProximity(smallSite,latitude,longitude,goodCities[0]);
				smallSite.getHotelsInCityByProximity(goodCities[0], latitude, longitude);
				// checking the big data ( for various cities)
				for (String city : goodCities) {
					testHelperGetHotelsInCityByProximity(bigSite,latitude,longitude,city);
				}
			}
		}
	}


}
