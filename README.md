**Weather Applicaton**

Below are the details - weather based app where users can look up weather for a city.
**Public API**
Create a free account at openweathermap.org. Just takes a few minutes. Full documentation for the service below is on their site, be sure to take a few minutes to understand it.

https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid={API key}

**Built-in geocoding**
Please use Geocoder API if you need automatic convert city names and zip-codes to geo coordinates and the other way around.
Please note that API requests by city name, zip-codes and city id have been deprecated. Although they are still available for use, bug fixing and updates are no longer available for this functionality.
Built-in API request by city name
You can call by city name or city name, state code and country code. Please note that searching by states available only for the USA locations.

API call
https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
https://api.openweathermap.org/data/2.5/weather?q={city name},{country code}&appid={API key}
https://api.openweathermap.org/data/2.5/weather?q={city name},{state code},{country code}&appid={API key}
