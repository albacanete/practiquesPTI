# Common Gateway Interface (CGI)

## 1. Introduction

The goal of this session is to create a dynamic web page using the Apache HTTP Server and CGIs. The description of the web page to develop is provided in section 3. You can choose the programming language 
you'd like to use (C, Python, Perl, PHP, etc.) and you can install and configure the Apache Server the way you want. However, in order to help you, we provide an example using Python, and, in Section 2, we explain one possible way to install and configure the Apache HTTP Server.


## 2. Apache and CGIs. Quick Start

### 2.1 Booting the machine 

Conventional room: Select a Linux image and login with your credentials.

Operating Systems room: Select the latest Ubuntu imatge (e.g. Ubuntu 14) with credentials user=alumne and pwd:=sistemes


### 2.2 Install Apache with root privileges (in your laptop or at one Operating Systems room)

IMPORTANT: If you don't have root access follow instructions in ANNEX 1 (also copy the examples as explained there)

Open a terminal (CTRL+ALT+T) and type:

    sudo apt-get update
    sudo apt-get install apache2 #password = sistemes

Install python (if necessary):

    sudo apt-get install python

Check with version is installed:

    apachectl -V

test in browser: http://localhost:80


### 2.3 Install examples

Install git (if necessary):

    sudo apt-get install git

Download the examples:

    cd $HOME       
    #git clone https://github.com/rtous/pti.git
    git clone https://gitlab.fib.upc.edu/pti/pti.git
    cd pti/p1_cgi
    ls
  
#### 2.3.1 Static html page
    
    sudo mkdir /var/www/html/p1
    sudo cp *.html /var/www/html/p1

test in browser: http://localhost:80/p1/example.html

#### 2.3.2 Dynamic content with a CGI (a Python script)

enable CGIs:

    sudo a2enmod cgi
    sudo service apache2 restart    

copy examples:
	    
    sudo cp *.py /usr/lib/cgi-bin
    sudo chmod 055 /usr/lib/cgi-bin/*

test in browser: http://localhost/cgi-bin/example_cgi.py?NOM=Bruce

Take a look at the code of example_cgi.py. You can use this file as a template for the two CGIs that you will need to program in Section 3.

### 2.5 Form+CGI template

test in browser: http://localhost:80/p1/formulari.html and submit. The script that is processing the request is /usr/lib/cgi-bin/template_cgi.py. 

This is a more sophisticated example from which you can get some ideas for the CGIs that you will need to program in Section 3.

### 2.6 Troubleshooting

Check errors with:

    cat /var/log/apache2/error.log

Check config at:

    cat /etc/apache2/sites-enabled/000-default
    cat /etc/apache2/apache2.conf 

NOTE: Restart apache after changing the configuration with:

    sudo service apache2 restart

Apache documentation at http://httpd.apache.org/docs/2.2/

Error "end of script output before headers": Did you change file permissions? Is Python installed? 

Error "Failed to flush CGI output to client": A python cgi handling a POST (e.g. list.py) need to do "form = cgi.FieldStorage()""

## 3. Creating your own car rental web page 

As an example CGI you will create a simple car rental web page. It will consist in two functionalities:

- Request a new rental: A form to enter a new rental order. Input fields will include the car maker, car model, number of days and number of units. If all data is correct the total price of the rental will be returned to the user along with the data of the requested rental.
 
- Request the list of all rentals: A form asking a password (as only the administrator can see this information) that will return the list of all saved rental orders. 

Both functionalities will consist in a request form plus a response page. In case of invalid input data the request form will be shown again but alerting about the error. While the request forms may be static HTML pages, it is better to generate them from CGIs (this way they can show error messages). 

In order to keep the rentals data (to be able to list them) you will need to save the data to the disk. A single text file where each line represents a rental will be enough (though not in a real scenario). 

NOTE: Files carrental_home.html, carrental_form_new.html and carrental_form_list.html show a possible user interface. It's not compulsory to use these files within the solution (you may generate the forms dynamically from the CGIs).

### 3.1 Directory structure

There are several ways to solve the problem and you are free to choose the one you prefer. A simple approach would be to program two CGIs:

    /usr/lib/cgi-bin/new.py
    /usr/lib/cgi-bin/list.py

Each one will:

	1) If there's no input data just generate the form.
	2) If there's input data validate it and return the result (some info or a message error plus the form again)

You don't need to program the CGIs from scratch, you can replicate template_cgi.py.

In order to write/read the orders to a disk file you can use a comma-separated values format (CSV) and the csv python module. Take a look to ANNEX2 for an example.

## ANNEX 1: Compiling and installing Apache 2.4 from sources

	(replace PREFIX by the installation directory, e.g. /home/rtous)

	wget ftp://ftp.csx.cam.ac.uk/pub/software/programming/pcre/pcre-8.37.tar.gz
   	tar -xvzf pcre-8.37.tar.gz
	cd  pcre-8.37
	./configure --prefix=PREFIX/pcre
  	make
	make install
	cd ..	
	wget http://ftp.cixug.es/apache//httpd/httpd-2.4.18.tar.gz
	tar -xvzf httpd-2.4.18.tar.gz
	wget http://apache.rediris.es//apr/apr-1.5.2.tar.gz
	wget http://apache.rediris.es//apr/apr-util-1.5.4.tar.gz
	tar -xvzf apr-1.5.2.tar.gz 
 	tar -xvzf apr-util-1.5.4.tar.gz
	mv apr-1.5.2 httpd-2.4.18/srclib/apr
   	mv apr-util-1.5.4 httpd-2.4.18/srclib/apr-util
	cd httpd-2.4.18	
	./configure --prefix=PREFIX/apache2 --with-included-apr --with-pcre=PREFIX/pcre
	make
	make install
	cd
	gedit PREFIX/apache2/conf/httpd.conf

		#Replace listening port to 2345: Listen 2345 
		#uncomment the following line: LoadModule cgid_module modules/mod_cgid.so
	
   	PREFIX/apache2/bin/apachectl -k start
    (check http://localhost:2345 in your browser to see if it works)

Now you have to place the examples within PREFIX/apache2/htdocs or PREFIX/apache2/cgi-bin:

    cd       
    git clone https://github.com/rtous/pti.git
    sudo cp pti/p1_cgi/*.html PREFIX/apache2/htdocs
    sudo cp pti/p1_cgi/*.py PREFIX/apache2/cgi-bin
    chmod 764 PREFIX/apache2/cgi-bin/* 

Now check if CGIs are properly configured: In your browser http://localhost:2345/cgi-bin/example_cgi.py (you should see only "Hello World").

Now you can check how an HTML file and a CGI work together: Open http://localhost:2345/formulari.html and submit. The script that is processing the request is PREFIX/apache2/cgi-bin/template_cgi.py. 

If everything works you can go directly to Section 3 and start working on your car rental web page. 
    
  
## ANNEX 2: Read and Write CSV files

Writing:

    import csv
    c = csv.writer(open("MYFILE.csv", "a"))
    c.writerow(["Name","Address","Telephone","Fax","E-mail","Others"])

Reading:

    cr = csv.reader(open("MYFILE.csv","rb"))
    for row in cr:    
        print row[0], row[1]

## ANNEX 3: With Docker

You may find it more convenient to do the work within a Docker container. This way you can replicate the work in other platforms (OSX, Windows, etc.) without the pain of running a virtual machine. 

Check if docker is installed:

    docker -v

If not, let's install it:

    sudo apt-get update
    wget -qO- https://get.docker.com/ | sh
    sudo usermod -aG docker $(whoami)

It's necessary to LOGOUT to let the usermod command have effect.

Within the FIB's teaching lab need to fix a problem with the DNS (Docker replicates the nameservers from /etc/resolv.conf but ignores the localhost entries, the public nameservers do not work because of the firewall).  
    
    nmcli dev show | grep 'IP4.DNS'
    sudo vi /etc/docker/daemon.json
        {
            "dns": ["147.83.30.71", "8.8.8.8"]
        }
    sudo service docker restart

Install git (if necessary):

    sudo apt-get install git

Download the PTI required files:

    cd $HOME       
    git clone https://gitlab.fib.upc.edu/pti/pti.git
    cd pti/p1_cgi

Build the PTI image:

    docker build -t pti - < Dockerfile_pti

Run a container:

    docker run -it --name pti \
       -v $HOME/pti/p1_cgi:/userVolume \
       -p 80:80 \
       pti bash 

(within the container) Run apache:

    service apache2 start

(within the container) Move to the shared space:

    cd /userVolume/

/userVolume maps to your $HOME/pti/p1_cgi, so you can edit the files there with your usual tools.

Perform steps starting from Section 2.3.1 without using "sudo".

## ANNEX 3.1: Useful docker commands

Exit a container shell:

    CTRL+P+Q

Reenter a conteiner shell:

    docker exec -it pti bash

List docker images in the machine:

    docker images

List running/stopped containers:

    docker ps -a

Stop a container:

    docker stop pti

Start a stopped container:

    docker start pti

Remove a container:

    docker rm pti
















