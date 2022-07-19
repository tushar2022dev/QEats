
package com.crio.qeats.services;

import com.crio.qeats.dto.Restaurant;
import com.crio.qeats.exchanges.GetRestaurantsRequest;
import com.crio.qeats.exchanges.GetRestaurantsResponse;
import com.crio.qeats.repositoryservices.RestaurantRepositoryService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
// @Log4j2
public class RestaurantServiceImpl implements RestaurantService {

     private final Double peakHoursServingRadiusInKms = 3.0;
  private final Double normalHoursServingRadiusInKms = 5.0;


  @Autowired
  private RestaurantRepositoryService restaurantRepositoryService;


  @Override
  public GetRestaurantsResponse findAllRestaurantsCloseBy(
      GetRestaurantsRequest getRestaurantsRequest, LocalTime currentTime) {
     
        
      Double lat = getRestaurantsRequest.getLatitude() ; 
      Double lon = getRestaurantsRequest.getLongitude();
      List<Restaurant> lis = Collections.emptyList();
      GetRestaurantsResponse resp = new GetRestaurantsResponse(lis);


      Double radii = normalHoursServingRadiusInKms;
            LocalTime p1 = LocalTime.of(7, 59,59);
      LocalTime p2 = LocalTime.of(10,0,1);
      LocalTime p3 = LocalTime.of(12, 59,59);
      LocalTime p4 = LocalTime.of(14, 0,1);
      LocalTime p5 = LocalTime.of(18, 59,59);
      LocalTime p6 = LocalTime.of(21, 0,1);
      if((currentTime.isAfter(p1) && currentTime.isBefore(p2))||(currentTime.isAfter(p3) && currentTime.isBefore(p4))||(currentTime.isAfter(p5) && currentTime.isBefore(p6))){
        radii = peakHoursServingRadiusInKms;
      }
   List<Restaurant> getlist = restaurantRepositoryService.findAllRestaurantsCloseBy(lat, lon, currentTime, radii);
   resp.setRestaurants(getlist);

     return resp;
  }


}

