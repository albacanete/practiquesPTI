#! /usr/bin/python

# Llibreries
import cgi, os, re, sys, string, time

# Constants i variables globals
PASSWORD = "HTTPdRocKs"

sublist = (
"The Pepe Gourmet Sub",
"Big John Gourmet Sub",
"Sorry Charlie Gourmet Sub",
"Turkey Tom Gourmet Sub",
"Vito Gourmet Sub","Vegetarian Gourmet Sub",
"Gourmet Smoked Ham Club",
"Billy Club",
"Italian Night Club",
"Hunter's Club",
"Country Club",
"The Beach Club")

slimlist = (
"Ham and Cheese",
"Rare Roast Beef",
"California Tuna",
"Sliced Turkey",
"Salami and Capacola",
"Double Provolone")

sidelist = (
"Lay's Potato Chips",
"Jumbo Kosher Dill")

poplist = (
"Pepsi",
"Mountain Dew",
"Diet Pepsi",
"Iced Tea")

# Funcions
def print_order(order,allow,phone):
    print "<TITLE>Your Order</TITLE>\n"
    if (allow==1):
        print "<H1>Order Sent</H1>\n"
        print "<p>Your order has been sent to the UIUC e-mail to FAX gateway.</p>%s\n"%order
    else:
        print "<H1>Your order</H1>\n"
        print "<p>This is how your order would have looked if it had been sent, the password has incorrect or not set.</p>%s\n"%order
    print "<p>Please feel free to call me at %s if there is any problem. Thank you\n</p>"%phone

def print_error(reason):
    print "<TITLE>Form for Submarine Order</TITLE>\n"
    print "<H1>Error: You did not provide a %s\n"%reason
    sys.exit()

def dump_form():
    print "<TITLE>Form for Submarine Order</TITLE>\n"
    print "<H1>Jimmy John's Submarine Order Form</H1>\n"
    print "This form will send a faxed order to Jimmy John's in Champaign. Proper password is requred\n"
    print "for order to be submitted, otherwise a copy of the order that would have been submitted will\n"
    print "will be displayed.<P>\n"
    print "<HR>\n"
    print "<FORM ACTION=\"/cgi-bin/%s\" METHOD=\"POST\">\n"%'template_cgi.py'
    print "Password: <INPUT TYPE=\"text\" NAME=\"password\" MAXLENGTH=\"20\"><P>\n"
    print "<H3>Sub Type</H3>\n"
    print "Select which you would like of the following:<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sub\" VALUE=\"0\">The Pepe Gourmet Sub:\n"
    print "Smoked virginia ham and provolone cheese topped with lettuce, tomato, and mayo.<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sub\" VALUE=\"1\">Big John Gourmet Sub:\n"
    print "Medium rare shaved roast beef topped with mayo, lettuce, and tomato.<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sub\" VALUE=\"2\">Sorry Charlie Gourmet Sub:\n"
    print "Tuna, mixed with celery, onions, and sauce, topped with lettuce,\n"
    print "tomato, and alfalfa sprouts.<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sub\" VALUE=\"3\">Turkey Tom Gourmet Sub:\n"
    print "Turkey breast topped with lettuce, mayo, alfalfa sprouts, and mayo.<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sub\" VALUE=\"4\">Vito Gourmet Sub:\n"
    print "Genoa salami and provolone cheese topped with capacola, onion, lettuce, tomato, and Italian sauce.<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sub\" VALUE=\"5\">Vegetarian Gourmet Sub:\n"
    print "Layers of provolone cheese, separated by avocado, sprouts, lettuce, tomato, and mayo.<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sub\" VALUE=\"6\">Gourmet Smoked Ham Club:\n"
    print "1/4 pound of smoked ham, provolone cheese, topped with lettuce,\n"
    print "tomato, and mayo.<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sub\" VALUE=\"7\">Billy Club:\n"
    print "Shaved roast beef, provolone cheese, french dijon mustard, topped with shaved ham, lettuce,\n"
    print "tomato, and mayo.<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sub\" VALUE=\"8\">Italian Night Club:\n"
    print "Genoa salami, Italian capacola, smoked ham, and provolone cheese topped with lettuce,\n"
    print "tomato, onions, mayo, and Italian sauce.<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sub\" VALUE=\"9\">Hunter Club:\n"
    print "1/4 pound of sliced roast beef, provolone cheese, topped with lettuce, tomato, and mayo.<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sub\" VALUE=\"10\">Country Club:\n"
    print "Turkey breast, smoked ham, and provolonecheese topped with lettuce, tomato, and mayo.<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sub\" VALUE=\"11\">The Beach Club:\n"
    print "Turkey breast, avocado, and cheese topped with lettuce, mayo, alfalfa, and tomato.<P>\n"
    print "<H3>Slim Jim Subs</H3>\n"
    print "Subs without veggies or sauce.<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"slj\" VALUE=\"0\">Ham and Cheese<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"slj\" VALUE=\"1\">Rare Roast Beef<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"slj\" VALUE=\"2\">California Tuna<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"slj\" VALUE=\"3\">Sliced Turkey<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"slj\" VALUE=\"4\">Salami and Capacola<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"slj\" VALUE=\"5\">Double Provolone<P>\n"
    print "<H3>Side orders</H3>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sde\" VALUE=\"0\">Lays Potato Chips<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"sde\" VALUE=\"1\">Jumbo Kosher Dill<P>\n"
    print "<H3>Drinks</H3>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"pop\" VALUE=\"0\">Pepsi<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"pop\" VALUE=\"1\">Mountain Dew<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"pop\" VALUE=\"2\">Diet Pepsi<P>\n"
    print "<INPUT TYPE=\"checkbox\" NAME=\"pop\" VALUE=\"3\">Iced Tea<P>\n"
    print "<H3>Your Address, Phone Number, and Name</H3>\n"
    print "<INPUT TYPE=\"text\" NAME=\"name\" MAXLENGTH=\"32\">Name<P>\n"
    print "<INPUT TYPE=\"text\" NAME=\"address\" MAXLENGTH=\"64\">Address<P>\n"
    print "<INPUT TYPE=\"text\" NAME=\"phone\" MAXLENGTH=\"10\">Phone Number<P>\n"
    print "<INPUT type=\"submit\">\n"
    print "</FORM>\n"

# Programa principal
print "Content-type: text/html\n\n"
form = cgi.FieldStorage()

# Para debugar invocar
# http://......../jj-python.py?debug=1

if form.has_key('debug'):
    sys.stderr = sys.stdout
    for k in os.environ:
        print "%s = %s<br>"%(k,os.environ[k])
    for k in form.keys():
        value = form.getvalue(k, "")
        if isinstance(value, list):
            value = ",".join(value)
        print "<p> Form field %s = %s</p>"%(k, value)

if (os.environ['REQUEST_METHOD'] == "GET"):
    dump_form()
    sys.exit()

required_fields = ('name', 'address', 'phone', 'password')

for k in required_fields:
    if not form.has_key(k):
        print_error(k)

allow = (form['password'].value == PASSWORD)
sub_ordered=form.getvalue('sub', '')
slj_ordered=form.getvalue('slj', '')
sde_ordered=form.getvalue('sde', '')
pop_ordered=form.getvalue('pop', '')

order = "<p>My name is %s, and I would like to have the following\norder delivered to %s:</p>"%(form['name'].value,form['address'].value)
items = []
for sub in sub_ordered:
    items.append("(1) %s"%sublist[string.atoi(sub)])
for slj in slj_ordered:
    items.append("(1) %s"%slimlist[string.atoi(slj)])
for sde in sde_ordered:
    items.append("(1) %s"%sidelist[string.atoi(sde)])
for pop in pop_ordered:
    items.append("(1) %s"%poplist[string.atoi(pop)])

order = order + "<ul><li>%s</li></ul>"%string.join(items,"</li><li>")
print_order(order,allow,form['phone'].value)
