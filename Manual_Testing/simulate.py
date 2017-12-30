import os

def runscript(file):
	os.system("cat ./"+program+"/"+program+"_input/"+file+" | java -classpath ./"+program+" "+program+" > ./"+program+"/"+program+"_output/"+file)

if __name__ == "__main__":
	program = input("Program name: ")
	os.system("javac ./"+program+"/"+program+".java")
	os.system("mkdir ./"+program+"/"+program+"_output")
	os.system("ls ./"+program+"/"+program+"_input > temp.txt")
	files = []
	data = open("temp.txt", "r")
	for line in data:
		files.append(line.rstrip("\n"))
	os.remove("temp.txt")
	for file in files:
		runscript(file)
	