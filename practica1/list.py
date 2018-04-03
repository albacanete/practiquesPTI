#! /usr/bin/python

# Llibreries
import cgi, os, re, sys, string, time, csv

# Funcions
def print_error(reason):
    print "<TITLE>List of rental orders</TITLE>\n"
    print "<H1>Error: %s\n"%reason
    sys.exit()

# Programa principal
print "Content-type: text/html\n\n"

print "<br>List of rental orders \n</br>"
form = cgi.FieldStorage()
userid = form.getvalue("userid", "")
password = form.getvalue("password", "")

if (userid != "admin" and password != "admin"):
	print_error("incorrect user")

f = open("BD.csv", "rb")
c = csv.reader(f)
for row in c:
	print "<p> %s \n</p"%row

f.close()



