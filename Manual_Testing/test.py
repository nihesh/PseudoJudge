import os
import time

maxtime = 0.0
maxtest = 1

def runtestcase(program, file, output, tl):
	global maxtest
	global maxtime
	flag = 1
	start = time.time()
	os.system("timeout 5 cat ./"+program+"/"+program+"_input/"+output+" | java -classpath ./Results/"+file+" "+file+" > ./Results/"+file+"/"+"output"+output)
	end = time.time()
	file1 = open("./"+program+"/"+program+"_output/"+output, "r")
	file2 = open("./Results/"+file+"/"+"output"+output, "r")
	line1 = []
	line2 = []
	for line in file1:
		line = line.rstrip("\n")
		line = line.rstrip(" ")
		line1.append(line)
	for line in file2:
		line = line.rstrip("\n")
		line = line.rstrip(" ")
		line2.append(line)
	if(len(line1) == len(line2)):
		for i in range(len(line1)):
			if(line1[i]==line2[i]):
				continue
			else:
				flag = 0
				break
	else:
		flag = 0

	if(end-start>maxtime):
		maxtime = end-start
		maxtest = output

	if(flag == 1):
		os.remove("./Results/"+file+"/"+"output"+output)
		if(tl<end-start):
			return 2
		else:
			return 1
	return 0

if __name__ == "__main__":
	flag = 0
	file = input("File name: ")
	program = input("Program name: ")
	tl = int(input("Time limit (in seconds): "))
	os.system("mkdir ./Results/"+file)
	os.system("mv "+file+".java ./Results/"+file+"/")	
	os.system("javac ./Results/"+file+"/"+file+".java")
	os.system("ls ./"+program+"/"+program+"_input > temp.txt")
	files = []
	data = open("temp.txt", "r")
	for line in data:
		files.append(line.rstrip("\n"))
	os.remove("temp.txt")
	k=0
	for filename in files:
		status = runtestcase(program, file, filename, tl)
		if(status == 0):
			k+=1
		if(status == 2):
			flag = 1

	print("The code failed in "+str(k)+" out of "+str(len(files))+" testcases\nMaximum runtime = "+str(maxtime)+"\nTestCase = "+str(maxtest)+"\n")
	if(flag == 1):
		print("Time limit exceeded! Max runtime = "+str(maxtime)+" seconds")