# AR Drone Navigation Project

## Needed Dependencies For Python

* [Emokit](https://github.com/openyou/emokit)
* [python-ARDrone](https://github.com/venthur/python-ardrone)
* Pygame
* [Leap Motion SDK](https://developer.leapmotion.com/)

## Note

In the emotiv.py file from emokit change:

```
self.gyroX = ((ord(data[29]) << 4) | (ord(data[31]) >> 4))

self.gyroY = ((ord(data[30]) << 4) | (ord(data[31]) & 0x0F))
```

to

```
self.gyroX = ord(data[29]) - 106

self.gyroY = ord(data[30]) - 105
```

## Needed Dependencies for Java

* [YADrone](http://vsis-www.informatik.uni-hamburg.de/oldServer/teaching//projects/yadrone/)
* [Leap Motion SDK](https://developer.leapmotion.com/)
