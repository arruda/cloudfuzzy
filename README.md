Cloud Fuzzy
===========

CloudFuzzy is a online Toolbox for Fuzzy Modeling.

How it works?
-----------

Using Play! Framework for the web application and XFuzzy as the fuzzy engine, CloudFuzzy combines
this two to make an easy and versatile ToolBox that allows you to create your own Fuzzy Model.

Install
-------

Install VirtualBox, and Vagrant.

Install Vagrant-vbguest Plugin:

	$ vagrant plugin install vagrant-vbguest
	
Go to the project folder and run:

	$ vagrant up
	
Wait until it finishes setting up the virtual machine and then access the machine with:

	$ vagrant ssh
	
Now inside the virtual machine, navigate to the folder "/vagrant/cloudfuzzy":

	$ cd /vagrant/cloudfuzzy
	
And run the command:

	$ play run
	

Finally, in can access your app in your fisical machine on: http://localhost:900


Licence
-------

This software is distributed using GPL license, see LICENSE file for more details.
