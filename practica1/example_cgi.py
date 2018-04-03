#! /usr/bin/python

# Llibreries
import cgi, os, re, sys, string, time

# Programa principal
print "Content-type: text/html\n\n"
print "Hello world!!\n\n"
form = cgi.FieldStorage()
value = form.getvalue("NOM", "")
print "<p> Form field NOM = %s</p>"%(value)
