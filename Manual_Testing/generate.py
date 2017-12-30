import random
import os
import numpy

def generateCases(program, identity):
	file = open("./"+program+"/"+program+"_input/test"+str(identity)+".txt", "w")
	file.write(str(30)+" "+str(random.randint(0,1000000))+"\n")
	for i in range(30):
		file.write(str(random.randint(0,8))+" ")
	file.close()


if __name__=="__main__":
	program = input("Program name: ")
	os.system("mkdir "+program)
	os.system("mkdir ./"+program+"/"+program+"_input")
	
	for i in range(2000):
		generateCases(program, i+1)
	