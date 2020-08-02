# PocketWeather2

This is a very simple implementation of the weather information at your current location. This was an exercise in implementing a RESTful client and location information. The main goal of this is to serve as a skeleton for project going forward

## Getting the app to run

Clone the repository and gradle sync needed dependencies if needed. Run it through Android Studio and it will work. Needs current location as well as internet connection.

## Possible issues

The location service on some devices, such as Huawei, are known to bug. Location may jump to where service provider thinks it is. Had an extreme case where phone was about 700km's off location (Huawei p20 lite on Vodacom).
Openweather free API has known issues due to user based weather stations. Found one at [25.687, -28.235] that gave information as raining on dry day.

## Things to make it better

* Allow the user to add custom location
* More weather icons to show partly cloudy information and so on.
* Forecast the weather for chosen location.
* Use a better API key to do more accurate calls (This unfortunately costs money)

### Notes

I used CircleCI for my CI implementation, this relies on a docker image on cloudsmith, but that URL wasn't committed in gradle. API key shown in gradle is an example only and will not work.