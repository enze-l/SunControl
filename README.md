# SunControl

An android application designed to control an artificial sun light.

## Components

- a artificial sunlight controlled by an esp32 -> https://github.com/zink-l/SunNode. For better understanding referenced as just "sun"
- a companion app as an interface to configure preferences of the sun. This functionality is represented by this application

## Accessible Features

- toggle between automatic and manual lighting mode
- set times for sunrise and sundown
- read out current lightlevel and history
- set threshold for turning the sun on

## Automatic features

- Device running this application should be discoverable inside an wifi environment. This enables the sun to automatically turn off if nobody is around 
- The Application changes between day and nightmode depending on the lighting conditions reported by the sun
