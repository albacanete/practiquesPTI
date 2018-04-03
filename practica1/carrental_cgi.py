#! /usr/bin/python

# Llibreries
import cgi, os, re, sys, string, time, csv

# Programa principal
print "Content-type: text/html\n\n"
print "TU ALQUILER: \n\n"
form = cgi.FieldStorage()
modelVehicle = form.getvalue("model_vehicle", "")
subModelVehicle = form.getvalue("sub_model_vehicle", "")
diesLloguer = form.getvalue("dies_lloguer", "")
numVehicle = form.getvalue("num_vehicles", "")
descompte = form.getvalue("descompte", "")

print "<p> Model del vehicle = %s</p>"%(modelVehicle)
print "<p> Submodel del vehicle = %s</p>"%(subModelVehicle)
print "<p> Dies de lloguer del vehicle = %s</p>"%(diesLloguer)
print "<p> Numero del vehicle = %s</p>"%(numVehicle)
print "<p> Descompte = %s</p>"%(descompte)

f = open("BD.csv", "a")
c = csv.writer(f)
c.writerow([modelVehicle,subModelVehicle,diesLloguer,numVehicle, descompte])
f.close()
