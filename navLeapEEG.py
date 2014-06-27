import libardrone
import emotiv
import gevent
import pygame
import Leap, sys

  
def main():
  drone = libardrone.ARDrone()
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
  
  drone.takeoff()
    
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
	drone.hover()
	
      else:
	if(direction['Pitch'] == 1):
	  drone.move_forward()
	elif(direction['Pitch'] == -1):
	  drone.move_backward()
	elif(direction['Yaw'] == 1):
	  drone.turn_right()
	elif(direction['Yaw'] == -1):
	  drone.turn_left()
      
      print "Gyro X: %d Gyro Y: %d" % (packet.gyroX, packet.gyroY)
      print "Yaw: %d Pitch: %d" % (direction['Yaw'], direction['Pitch'])
      
      frame = controller.frame()
      
      if not frame.hands.is_empty:
	hand = frame.hands[0]
      
	fingers = hand.fingers
	normal = hand.palm_normal
	handdirection = hand.direction
      
	print "Hand pitch: %f degrees, roll: %f degrees, yaw: %f degrees" % (
                handdirection.pitch * Leap.RAD_TO_DEG,
                normal.roll * Leap.RAD_TO_DEG,
                handdirection.yaw * Leap.RAD_TO_DEG)
      
	if handdirection.yaw * Leap.RAD_TO_DEG > 10:
	  drone.move_left()
	  print "Move left"
	
	elif handdirection.yaw * Leap.RAD_TO_DEG < -10:
	  drone.move_right()
	  print "Move right"
	  
	else:
	  drone.hover()
	  print "Hover"
	  
      for event in pygame.event.get():
	 if event.type == pygame.KEYDOWN:
	  if event.key == pygame.K_SPACE:
	      drone.land()
	      isRunning = False
      
   
  headset.close()
  print "Shutting down...",
  drone.halt()
  print "Success"
  
if __name__ == '__main__':
    main()
    

			
    
  
  
    