from django.shortcuts import render
from django.core.files.storage import FileSystemStorage
from django.http import HttpResponse, HttpResponseRedirect
import os
import time
from wsgiref.util import FileWrapper
from shutil import make_archive
from . import urls

maxtime = 0.0

activePrograms = ['Lab12_normal','Lab12_bonus']			# stats file has to created like stats<program>.txt for each program and default counts are to be set

def execute(program, output, name, file):
	os.system("timeout 5 cat ./"+program+"/"+program+"_input/"+output+" | java -classpath ./Results/"+name+" "+file[:file.find(".")]+" > ./Results/"+name+"/Failures/"+"output"+output+" 2>&1")

def runtestcase(program, file, output, tl, name):
	global maxtime
	flag = 1
	start = time.time()
	execute(program, output, name, file)
	end = time.time()
	file1 = open("./"+program+"/"+program+"_output/"+output, "r")
	file2 = open("./Results/"+name+"/Failures/"+"output"+output, "r")
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

	if(flag == 1):
		os.remove("./Results/"+name+"/Failures/"+"output"+output)
		if(tl<end-start):
			return 2
		else:
			return 1
	return 0

def process(file, program, timelimit, name):
	file2=open("./Results/"+name+"/Result.txt","w")
	flag = 0	
	os.system("javac ./Results/"+name+"/"+file)
	os.system("ls -r --sort=size ./"+program+"/"+program+"_input > temp.txt")
	files = []
	data = open("temp.txt", "r")
	for line in data:
		files.append(line.rstrip("\n"))
	os.remove("temp.txt")
	for filename in files:
		status = runtestcase(program, file, filename, timelimit, name)
		if(status == 0):
			settingsfile=open("./settings/stats"+program+".txt","r")
			settingstemp=open("./settings/temp2.txt","w")
			os.system("cp ./"+program+"/"+program+"_input/"+filename+" ./Results/"+name+"/"+program+"_input/")
			os.system("cp ./"+program+"/"+program+"_output/"+filename+" ./Results/"+name+"/"+program+"_output/")
			file2.write("Wrong Answer")
			settingstemp.write(settingsfile.readline())
			count=int(settingsfile.readline().rstrip("\n"))
			count+=1
			settingstemp.write(str(count)+"\n")
			settingstemp.write(settingsfile.readline())			
			settingsfile.close()
			settingstemp.close()
			os.remove("./settings/stats"+program+".txt")
			os.rename("./settings/temp2.txt","./settings/stats"+program+".txt")
			file2.close()
			return
		if(status == 2):
			settingsfile=open("./settings/stats"+program+".txt","r")
			settingstemp=open("./settings/temp2.txt","w")
			file2.write("Time limit exceeded!")
			file2.close()
			settingstemp.write(settingsfile.readline())
			settingstemp.write(settingsfile.readline())
			count=int(settingsfile.readline().rstrip("\n"))
			count+=1			
			settingstemp.write(str(count)+"\n")
			settingsfile.close()
			settingstemp.close()
			os.remove("./settings/stats"+program+".txt")
			os.rename("./settings/temp2.txt","./settings/stats"+program+".txt")
			return

	
	settingsfile=open("./settings/stats"+program+".txt","r")
	settingstemp=open("./settings/temp2.txt","w")
	file2.write("Success")
	count=int(settingsfile.readline().rstrip("\n"))
	count+=1			
	settingstemp.write(str(count)+"\n")
	settingstemp.write(settingsfile.readline())
	settingstemp.write(settingsfile.readline())
	settingsfile.close()
	settingstemp.close()
	os.remove("./settings/stats"+program+".txt")
	os.rename("./settings/temp2.txt","./settings/stats"+program+".txt")
	file2.close()

def index(request):
	counter=1
	dic={}
	for program in activePrograms:
		file=open("./settings/stats"+program+".txt","r")
		success=int(file.readline().rstrip("\n"))
		failure=int(file.readline().rstrip("\n"))
		tle=int(file.readline().rstrip("\n"))
		file.close()
		dic["s"+str(counter)] = success
		dic["f"+str(counter)] = failure
		dic["t"+str(counter)] = tle
		counter+=1
	return render(request,"index.html",dic)

def download(request,file_name=""):
	file_path = "./Results/"+file_name
	path_to_zip = make_archive(file_path,"zip",file_path)
	response = HttpResponse(FileWrapper(open(path_to_zip,'rb')), content_type='application/zip')
	file_name=file_name[:file_name.rfind("_")]
	response['Content-Disposition'] = 'attachment; filename='+file_name.replace(" ","_")+'.zip'
	return response

def result(request):
	if(request.method=="POST" and request.FILES['code']):
		file=request.FILES['code']
		if(file.size>1000000):
			return HttpResponseRedirect("/")							# Max upload 1MB - prevents bypassers
		name=request.POST.get("name")
		name=name.replace("(","")
		name=name.replace(")","")
		name=name.replace("/","")
		if(name.count(" ")!=0):
			name=name[:name.find(" ")]
		if(name.count("_")!=0):
			name=name[:name.find("_")]
		if(name==""):													# Filtering out bypassed empty names
			return HttpResponseRedirect("/")
		serial = open("./settings/serial.txt","r")
		line = serial.readline()
		line = line.rstrip("\n")
		name = name+"_"+line
		line=int(line)+1
		serial2 = open("./settings/temp.txt","w")
		serial2.write(str(line))
		serial.close()
		serial2.close()
		os.remove("./settings/serial.txt")
		os.rename("./settings/temp.txt","./settings/serial.txt")
		program=request.POST.get("program")
		tl=request.POST.get("timelimit")
		if(tl==None):
			tl=3														# default time-limit when tle input is frozen
		fs = FileSystemStorage()
		filename = fs.save(file.name,file)
		os.system("mkdir ./Results/"+name)
		os.system("mkdir ./Results/"+name+"/Failures")
		os.system("mv ./"+file.name+" ./Results/"+name+"/")
		os.system("mkdir ./Results/"+name+"/"+program+"_input/")
		os.system("mkdir ./Results/"+name+"/"+program+"_output/")
		process(file.name, program, int(tl), name)
		maxtime=0.0
		response = download(request, name)
		os.system("rm ./Results/"+name+".zip")
		file.close()
		return response