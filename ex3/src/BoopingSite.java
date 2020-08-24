import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class BoopingSite {

	private final static int LATITUDE_THRESHOLD = 90;
	private final static int LONGITUDE_THRESHOLD = 180;

	private final ArrayList<Hotel> allHotels;

	/**
	 * reads the data set and converts it to an arraylist
	 * @param name - the name of the dataset
	 */
	public BoopingSite(String name) {
		this.allHotels = new ArrayList<>();
		allHotels.addAll(Arrays.asList(HotelDataset.getHotels(name)));
	}

	/*
	returns all hotels from data set that are located in given city
	 */
	private ArrayList<Hotel> getHotelsInCity(String city) {
		ArrayList<Hotel> hotels = new ArrayList<>();
		for (Hotel hotel : allHotels) {
			if (hotel.getCity().equals(city)) {
				hotels.add(hotel);
			}
		}
		return hotels;
	}

	/**
	 * sorts the hotels in some city by rating
	 * @param city - a city to check on
	 * @return - an array of hotels located in the given city, sorted from the highest star-rating to the
	 * 		lowest.
	 */
	public Hotel[] getHotelsInCityByRating(String city) {
		// fetching hotels that are relevant the the city:
		ArrayList<Hotel> hotels = getHotelsInCity(city);
		hotels.sort(new Comparator<Hotel>() {
			@Override
			public int compare(Hotel h1, Hotel h2) {
				Integer stars1 = h1.getStarRating();
				Integer stars2 = h2.getStarRating();
				if (stars1.equals(stars2)) { // compare by name
					return h2.getPropertyName().compareTo(h1.getPropertyName());
				}
				return stars1.compareTo(stars2); // compare by star rating
			}
		});
		Collections.reverse(hotels); // we sort from highest rating to lowest
		int size = hotels.size();
		return hotels.toArray(new Hotel[size]);
	}

	/*
	calculates distance between two dots represented by (x1,y1) and (x2,y2)
	 */
	private double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
	}

	/*
	 * checks if the given coordinates exceeds the limits
	 * @param latitude - latitude coordinates
	 * @param longitude - longitude coordinates
	 * @return - true: bad coordinates. false: good coordinates
	 */
	private boolean badCoordinates(double longitude, double latitude) {
		return longitude > LONGITUDE_THRESHOLD || longitude < -LONGITUDE_THRESHOLD ||
			   latitude > LATITUDE_THRESHOLD || latitude < -LATITUDE_THRESHOLD;
	}

	/**
	 * sorts hotels according to distance from some point
	 * @param latitude - latitude coordinates
	 * @param longitude - longitude coordinates
	 * @return - an array of hotels sorted according to their euclidean distance from the given geographic
	 * 		location, in ascending order
	 */
	public Hotel[] getHotelsByProximity(double latitude, double longitude) {
		ArrayList<Hotel> hotels = new ArrayList<>();
		if (badCoordinates(longitude,latitude)) {
			//bad coordinates so we return an empty array
			return hotels.toArray(new Hotel[0]);
		}
		hotels = allHotels;
		hotels.sort(new Comparator<Hotel>() {
			@Override
			public int compare(Hotel h1, Hotel h2) {
				Double distance1 = distance(latitude, longitude, h1.getLatitude(), h1.getLongitude());
				Double distance2 = distance(latitude, longitude, h2.getLatitude(), h2.getLongitude());
				if (distance1.equals(distance2)) { // compare according to p-o-i
					Integer poi1 = h1.getNumPOI();
					Integer poi2 = h2.getNumPOI();
					return poi2.compareTo(poi1);
				}
				return distance1.compareTo(distance2);
			}
		});
		int size = hotels.size();
		return hotels.toArray(new Hotel[size]);
	}

	/**
	 * sorts hotels according to distance from some point, within a certain city
	 * @param city - a city to check on
	 * @param latitude - latitude coordinates
	 * @param longitude - longitude coordinates
	 * @return - an array of hotels sorted according to their euclidean distance from the given geographic
	 * 		location, in ascending order
	 */
	public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude) {
		// fetching hotels that are relevant the the city:
		ArrayList<Hotel> hotels = new ArrayList<>();
		if (badCoordinates(longitude,latitude)) {
			//bad coordinates so we return an empty array
			return hotels.toArray(new Hotel[0]);
		}
		hotels = getHotelsInCity(city);
		hotels.sort(new Comparator<Hotel>() {
			@Override
			public int compare(Hotel h1, Hotel h2) {
				Double distance1 = distance(latitude, longitude, h1.getLatitude(),
											h1.getLongitude());
				Double distance2 = distance(latitude, longitude, h2.getLatitude(),
											h2.getLongitude());
				if (distance1.equals(distance2)) { // compare according to p-o-i
					Integer poi1 = h1.getNumPOI();
					Integer poi2 = h2.getNumPOI();
					return poi2.compareTo(poi1);
				}
				return distance1.compareTo(distance2);
			}
		});
		int size = hotels.size();
		return hotels.toArray(new Hotel[size]);
	}
}
