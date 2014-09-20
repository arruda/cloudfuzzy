===================================
Cloudfuzzy
===================================

CloudFuzzy is a online Toolbox for Fuzzy Modeling.


Dependencies
------------

To setup you're going to need `npm` from NodeJS available to install the frontend code.


Setup
-----


1. Install Python Requirements

        pip install -r requirements/local.txt

2. Install Bower + Grunt

        npm install -g grunt-cli bower

3. Install Assets

        npm install
        bower install

4. Compile Assets

        grunt

5. Setup the Database

        ./manage.py syncdb

6. Run the Server

        ./manage.py runserver


Play Version
------------
Looking for the Play! framework version?
Check this branch: `Play Branch <https://github.com/arruda/cloudfuzzy/tree/play>`_.


LICENSE
=============
This software is distributed using MIT license, see LICENSE file for more details.
