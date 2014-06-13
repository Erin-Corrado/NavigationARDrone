## AR Drone Navigation Project
# Needed Dependencies
* [Emokit](https://github.com/openyou/emokit)
* [python-ARDrone](https://github.com/venthur/python-ardrone)
* Pygame

# Note
In the emotiv.py file change:

'''
self.gyroX = ((ord(data[29]) << 4) | (ord(data[31]) >> 4))
self.gyroY = ((ord(data[30]) << 4) | (ord(data[31]) & 0x0F))
'''

to

'''
self.gyroX = ord(data[29]) - 106
self.gyroY = ord(data[30]) - 105
'''