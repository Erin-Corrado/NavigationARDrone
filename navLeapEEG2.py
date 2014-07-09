import libardrone
import emotiv
import gevent
import pygame
import Leap, sys

  
def flightMode(normal):
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
	  
      if (direction['Pitch'] == 0):
	print "here"
	
      else:
	if(direction['Pitch'] == 1):
	  drone.move_forward()
	elif(direction['Pitch'] == -1):
	  drone.move_backward()
      
      print "Gyro X: %d Gyro Y: %d" % (packet.gyroX, packet.gyroY)
      print "Pitch: %d" % (direction['Pitch'])
      
      frame = controller.frame()
      
      if not frame.hands.is_empty:
	hand = frame.hands[0]
      
	fingers = hand.fingers
	normal = hand.palm_normal
	handdirection = hand.direction
      
	print "Hand pitch: %f degrees, roll: %f degrees, yaw: %f degrees" % (
                handdirection.pitch * Leap.RAD_TO_DEG,
                normal.roll * Leap.RAD_TO_DEG,
                handdirection.yaw * Leap.RAD_TO_DEG+normal.yaw)
      
	if handdirection.yaw * Leap.RAD_TO_DEG > 10+normal.yaw:
	  drone.turn_right()
	  print "Turn right"
	
	elif handdirection.yaw * Leap.RAD_TO_DEG < -10+normal.yaw:
	  drone.turn_left()
	  print "Turn left"
	  
	elif normal.roll * Leap.RAD_TO_DEG > 10+normal.yaw:
	  drone.move_right()
	  print "Move right"
	  
	elif normal.roll * Leap.RAD_TO_DEG < -10+normal.yaw:
	  drone.move_left()
	  print "Move left"
	  
	else:
	  drone.hover()
	  print "Hover"
	  
      elif (direction['Pitch'] == 0) and frame.hands.is_empty:
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
  
def trainingMode():
  print "Trlaining mode. Place hand on leap motion."
  isTraining = True
  controller = Leap.Controller()
  
  while isTraining == True:
    frame = controller.frame()
    
    while frame.hands.is_empty:
      print "Hand not detected."
      frame = controller.frame()
      
    if not frame.hands.is_empty:
      
      hand = frame.hands[0]
      handdirection = hand.direction
      
      normal = handdirection.yaw * Leap.RAD_TO_DEG
      isTraining = False
  
  print "Normal position is %f" % (normal)
  return normal
      
    
  
  
def main():
  print "Welcome to Drone Navigation."
  print "Press t for training mode, or f for flight mode. Hit space to exit."
  
  mode = raw_input()
  normal = Leap.Vector.zero
  
  while mode is not ' ':
    
    if mode == 't':
      normal = trainingMode()
    
    elif mode == 'f':
      flightMode(normal)
    
    print "Press t for training, f for flight, or space to exit: "
    mode = raw_input()
  
  
if __name__ == '__main__':
    main()
    

			
    
  
  
    