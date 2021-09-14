
.. meta::
    :robots: noindex

.. _building docs:

Building this documentation locally
===================================

If you want to build this documentation locally, use the following steps:

#.  Clone the repository.
#.  Install Python 2.7 and pip. You can use the guide at
    http://www.tylerbutler.com/2012/05/how-to-install-python-pip-and-virtualenv-on-windows-with-powershell/ if needed.
    You can choose to install virtualenv if you wish, but you don't have to.
#.  Once Python and pip are installed, open up a PowerShell or cmd.exe prompt and go to the ``docs`` folder in this
    repository.
#.  Type ``pip install -r requirements.txt``. You should see some output like this:

    ..  code-block:: text

        Downloading/unpacking Sphinx>=1.3.1 (from -r requirements.txt (line 1))
        Downloading/unpacking sphinx-rtd-theme>=0.1.8 (from -r requirements.txt (line 2))

        ...
        ...
        ...

        Successfully installed Sphinx sphinx-rtd-theme sphinxcontrib-findanything sphinxcontrib-httpdomain Jinja2 alabaster babel six Pygments snowballstemmer docutils colorama markupsafe pytz
        Cleaning up...

#.  Run the command ``pip install sphinxcontrib-domaintools`` (this is necessary due to a bug in the
    sphinxcontrib-domaintools installer).
#.  Now that all the prerequisites are installed, you can build the documentation using the following command:
    ``make.bat html``. The built documentation will be output to ``build/html``.
