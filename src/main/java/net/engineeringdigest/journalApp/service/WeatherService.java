package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    @Value("${weather.service.api}")
    private  String apiKey ;

    private static final String API = "http://api.weatherstack.com/current?access_key=KEY&query=CITY";


    public WeatherResponse getWeather(String city){


      WeatherResponse weatherResponse=  redisService.get("Weather_of_"+ city,WeatherResponse.class);

      if(weatherResponse !=null){
          return weatherResponse;
      }else{
          String finalAPI = API.replace("KEY",apiKey).replace("CITY",city);
          ResponseEntity<WeatherResponse> exchange = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
          WeatherResponse response = exchange.getBody();

          //if this not exit in redis then save it
          if(response != null){
              redisService.set("Weather_of_"+city,response,300l);
          }
          return response;
      }


    }
}
