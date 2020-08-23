import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.*;

public class BoopingSite {
    private final static double LATITUDMIN = -90;
    private final static double LATITUDEMAX = 90;
    private final static double LONGITUDEMIN = - 180;
    private final static double LONGITUDEMAX = 190;

    /**
     * holds an Arraylist of all resorts on the given dataset.
     */
    private ArrayList<Hotel> hotelList;

    public BoopingSite(String name) {
        this.hotelList = new ArrayList<>();
        this.hotelList.addAll(Arrays.asList(HotelDataset.getHotels(name)));
    }

    /**
     * This method sorts a list of resorts according to a given city.
     * @param cityName - The name of the city the user would like to stay at.
     * @return - an unsorted list of all resorts at the given city.
     */
    private ArrayList<Hotel> sortByCity(String cityName){
        ArrayList<Hotel> cityList = new ArrayList<>();
        for(Hotel tempHotel : this.hotelList){
            if(tempHotel.getCity().equals(cityName)){
                cityList.add(tempHotel);
            }
        }
        return cityList;
    }

    /**
     * This method sorts all resorts in a given city according to the rating of the resorts. if two resorts
     * have the same rating then they are sorting alphabetically.
     * @param city - The city we are checking.
     * @return - returns a sorted array of all resorts in the given city, an empty array if there are no
     * resorts at that city.
     */
    public Hotel[] getHotelsInCityByRating(String city){
        ArrayList<Hotel> sortedList = this.sortByCity(city);
        if(sortedList.isEmpty()){ //no resorts in that city, returns an empty array.
            return sortedList.toArray(new Hotel[sortedList.size()]);
        }
        Collections.sort(sortedList, new Comparator<Hotel>(){
            @Override
            public int compare(Hotel hotel1, Hotel hotel2){ //sorts resorts from lowest ranking to highest.
                Integer firstHotelStars = hotel1.getStarRating();
                Integer SecHotelStars = hotel2.getStarRating();
                if(firstHotelStars.equals(SecHotelStars)){ // if the star ranking is the same.
                    String name1 = hotel1.getPropertyName();
                    String name2 = hotel2.getPropertyName();
                    return name2.compareTo(name1);
                }
                return firstHotelStars.compareTo(SecHotelStars);
            }
        });
        Collections.reverse(sortedList); //reverses arrayList to a descending list.
        return sortedList.toArray(new Hotel[sortedList.size()]);
    }

    /**
     * This method sorts an array of hotels according to their distance from a given location. in case the
     * distance is the same, sorts according to the amount of points of interest around the resort.
     * @param latitude - the latitude coordinates of the location.
     * @param longitude - the longitude coordinates of the location.
     * @return - returns an array sorted according to the distance.
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude){
        ArrayList<Hotel> resortListCopy = this.hotelList;
        if(LATITUDMIN > latitude | latitude > LATITUDEMAX |
                LONGITUDEMIN > longitude | LONGITUDEMAX < longitude){
            resortListCopy.clear(); //returns an empty array.
            return resortListCopy.toArray(new Hotel[resortListCopy.size()]);
        }
        else if(resortListCopy.isEmpty()){ //no resorts in that city, returns an empty array.
            return resortListCopy.toArray(new Hotel[resortListCopy.size()]);
        }
        Collections.sort(resortListCopy, new Comparator<Hotel>() {
            @Override
            public int compare(Hotel hotel1, Hotel hotel2) {
                Double distance1 = getDistanceFrom(latitude, longitude, hotel1);
                Double distance2 = getDistanceFrom(latitude, longitude, hotel2);
                if(distance1.equals(distance2)){ //compares by amount of POI's.
                    Integer POI1 = hotel1.getNumPOI();
                    Integer POI2 = hotel2.getNumPOI();
                    return POI2.compareTo(POI1);
                }
                else {
                    return distance1.compareTo(distance2);
                    //compares according to distance from given location.
                }
            }
        });
        return resortListCopy.toArray(new Hotel[resortListCopy.size()]);
    }

    /**
     * This method sorts a list of resorts of the same city according to their distance from a given
     * geographic location. if the distance is the same, sorts by the amount of points of interest around
     * the given city.
     * @param city - The city which the hostels are in.
     * @param latitude - the latitude coordinates of the location.
     * @param longitude - the longitude coordinates of the location.
     * @return - an array of hotels in the given city sorted according to their distance from a given location
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude){
        ArrayList<Hotel> sortedByCity = this.sortByCity(city);
        if(LATITUDMIN > latitude | latitude > LATITUDEMAX |
                LONGITUDEMIN > longitude | LONGITUDEMAX < longitude) {
            sortedByCity.clear(); //returns an empty array.
            return sortedByCity.toArray(new Hotel[sortedByCity.size()]);
        }
        if(sortedByCity.isEmpty()){ //no resorts in that city, returns an empty array.
            return sortedByCity.toArray(new Hotel[sortedByCity.size()]);
        }
        Collections.sort(sortedByCity, new Comparator<Hotel>() {
            @Override
            public int compare(Hotel hotel1, Hotel hotel2) {
                Double distance1 = getDistanceFrom(latitude, longitude, hotel1);
                Double distance2 = getDistanceFrom(latitude, longitude, hotel2);
                if(distance1.equals(distance2)){ //in case the distance is the same, compares POI'S.
                    Integer POI1 = hotel1.getNumPOI();
                    Integer POI2 = hotel2.getNumPOI();
                    return POI2.compareTo(POI1);
                }
                else{
                    return distance1.compareTo(distance2); //compares by distance.
                }
            }
        });
        return sortedByCity.toArray(new Hotel[sortedByCity.size()]);
    }

    /**
     * This method is used to calculate the euclidean distance between a given point and an hotel.
     * @param latitude - The latitude of the point.
     * @param longitude - The longitude of the point.
     * @param hotel - The hotel who's distance from the point we would like to find.
     * @return - a double representing the distance.
     */
    private double getDistanceFrom(double latitude, double longitude, Hotel hotel){
        double latDiffSquared = Math.pow(latitude - hotel.getLatitude(), 2);
        double longDiffSquared = Math.pow(longitude - hotel.getLongitude(), 2);
        return Math.sqrt(latDiffSquared + longDiffSquared);
    }
}
