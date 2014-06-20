import libardrone
import emotiv
import gevent
import pygame
import Leap, sys

class LeapListener(Leap.Listener):
  
  def on_init(self, controller):
    self.drone = libardrone.ARDrone()
    self.drone.speed = 0.2
    
    
  def on_connect(self, controller):
    print "Connected"
    
  def on_disconnect(self, controller):
    print "Disconnected"
    
  def on_frame(self, controller):
    frame = controller.frame()
    
    if not frame.hands.is_empty:
      hand = frame.hands[0]
      
      fingers = hand.fingers
      normal = hand.palm_normal
      direction = hand.direction
      
      print "Hand pitch: %f degrees, roll: %f degrees, yaw: %f degrees" % (
                direction.pitch * Leap.RAD_TO_DEG,
                normal.roll * Leap.RAD_TO_DEG,
                direction.yaw * Leap.RAD_TO_DEG)
      
      if normal.roll * Leap.RAD_TO_DEG > 10:
	self.drone.move_left()
	print "Move left"
	
      elif normal.roll * Leap.RAD_TO_DEG < -10:
	self.drone.move_right()
	print "Move right"
      
      
    

def main():
  listener = LeapListener()
  headset = emotiv.Emotiv()
  controller = Leap.Controller()
  
  gevent.spawn(headset.setup)
  gevent.sleep(1)
  pygame.init()
  W, H = 320, 240
  screen = pygame.display.set_mode((W, H))
  
  direction = {}
  direction['Yaw'] = 0
  direction['Pitch'] = 0
  yawCounter = 0
  pitchCounter = 0
  
  isRunning = True
  
  controller.add_listener(listener)
  listener.drone.takeoff()
    
  while isRunning:
      packet = headset.dequeue()
      
      if (yawCounter > 0) and (packet.gyroX < -5):
	yawCounter = 0
      elif(yawCounter < 0) and (packet.gyroX > 5):
	yawCounter=0
      elif(packet.gyroX > 5):
	yawCounter += 1
      elif(packet.gyroX < -5):
	yawCounter -= 1
      elif(packet.gyroX==0):
	yawCounter=0

      if(packet.gyroX > 50):
	direction['Yaw']=0
	yawCounter=0
			
      elif(packet.gyroX < -50):
	direction['Yaw']=0
	yawCounter=0
		
      elif(yawCounter > 20) and (direction['Yaw']==1):
	direction['Yaw']=0
	yawCounter=0
			
      elif(yawCounter > 40):
	
	if(direction['Yaw']==1): 
	  direction['Yaw']=0
	else:
	  direction['Yaw']=-1
	  yawCounter=0
			
      elif(yawCounter < -20) and (direction['Yaw']==-1):
	direction['Yaw']=0
	yawCounter=0
			
      elif(yawCounter < -40):
	if(direction['Yaw']==-1): 
	  direction['Yaw']=0
	else: 
	  direction['Yaw']=1
	  yawCounter=0
			
		

      if(pitchCounter > 0) and (packet.gyroY < -3):
	pitchCounter=0
      elif(pitchCounter < 0) and (packet.gyroY > 2):
	pitchCounter=0
      elif(packet.gyroY > 2):
	pitchCounter+=1
      elif(packet.gyroY < -3):
	pitchCounter-=1
      elif(packet.gyroY==0):
	pitchCounter=0

      if(packet.gyroY > 40):
	direction['Pitch']=0
	pitchCounter=0
			
      elif(packet.gyroY < -40):
	direction['Pitch']=0
	pitchCounter=0
			
      elif(pitchCounter > 20) and (direction['Pitch']==1):
	direction['Pitch']=0
	pitchCounter=0
			
      elif(pitchCounter > 30):
	if(direction['Pitch']==1):
	  direction['Pitch']=0
	else:
	  direction['Pitch']=-1
	  pitchCounter=0
			
      elif(pitchCounter < -20) and (direction['Pitch']==-1):
	direction['Pitch']=0
	pitchCounter=0
			
      elif(pitchCounter < -40):
	if(direction['Pitch']==-1):
	  direction['Pitch']=0
	else:
	  direction['Pitch']=1
	  pitchCounter=0
	  
      if(direction['Yaw'] == 0) and (direction['Pitch'] == 0):
	listener.drone.hover()
	
      else:
	if(direction['Pitch'] == 1):
	  listener.drone.move_forward()
	elif(direction['Pitch'] == -1):
	  listener.drone.move_backward()
	elif(direction['Yaw'] == 1):
	  listener.drone.turn_right()
	elif(direction['Yaw'] == -1):
	  listener.drone.turn_left()
      
      print "Gyro X: %d Gyro Y: %d" % (packet.gyroX, packet.gyroY)
      print "Yaw: %d Pitch: %d" % (direction['Yaw'], direction['Pitch'])
	  
      for event in pygame.event.get():
	if event.type == pygame.KEYDOWN:
	  if event.key == pygame.K_SPACE:
	    listener.drone.land()
	    isRunning = False
      
   
  headset.close()
  controller.remove_listener(listener)
  print "Shutting down...",
  listener.drone.halt()
  print "Success"
  
if __name__ == '__main__':
    main()
    

			
    
  
  
    