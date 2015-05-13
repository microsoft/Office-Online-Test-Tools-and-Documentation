
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
        Downloading/unpacking sphinxcontrib-findanything>=0.2.5 (from -r requirements.txt (line 4))
          Running setup.py (path:C:\Users\tylerbu\.virtualenvs\tmp\build\sphinxcontrib-findanything\setup.py) egg_info for package sphinxcontrib-findanything

        Downloading/unpacking sphinxcontrib-httpdomain>=1.3.0 (from -r requirements.txt (line 5))
          Downloading sphinxcontrib_httpdomain-1.3.0-py2.py3-none-any.whl
        Downloading/unpacking Jinja2>=2.3 (from Sphinx>=1.3.1->-r requirements.txt (line 1))
          Running setup.py (path:C:\Users\tylerbu\.virtualenvs\tmp\build\Jinja2\setup.py) egg_info for package Jinja2

            warning: no files found matching '*' under directory 'custom_fixers'
            warning: no previously-included files matching '*' found under directory 'docs\_build'
            warning: no previously-included files matching '*.pyc' found under directory 'jinja2'
            warning: no previously-included files matching '*.pyc' found under directory 'docs'
            warning: no previously-included files matching '*.pyo' found under directory 'jinja2'
            warning: no previously-included files matching '*.pyo' found under directory 'docs'
        Downloading/unpacking alabaster>=0.7,<0.8 (from Sphinx>=1.3.1->-r requirements.txt (line 1))
          Downloading alabaster-0.7.4-py2-none-any.whl
        Downloading/unpacking babel>=1.3 (from Sphinx>=1.3.1->-r requirements.txt (line 1))
          Running setup.py (path:C:\Users\tylerbu\.virtualenvs\tmp\build\babel\setup.py) egg_info for package babel

            warning: no previously-included files matching '*' found under directory 'docs\_build'
            warning: no previously-included files matching '*.pyc' found under directory 'tests'
            warning: no previously-included files matching '*.pyo' found under directory 'tests'
        Downloading/unpacking six>=1.4 (from Sphinx>=1.3.1->-r requirements.txt (line 1))
          Downloading six-1.9.0-py2.py3-none-any.whl
        Downloading/unpacking Pygments>=2.0 (from Sphinx>=1.3.1->-r requirements.txt (line 1))
        Downloading/unpacking snowballstemmer>=1.1 (from Sphinx>=1.3.1->-r requirements.txt (line 1))
          Running setup.py (path:C:\Users\tylerbu\.virtualenvs\tmp\build\snowballstemmer\setup.py) egg_info for package snowballstemmer

            warning: no files found matching '*.py' under directory 'src'
        Downloading/unpacking docutils>=0.11 (from Sphinx>=1.3.1->-r requirements.txt (line 1))
          Running setup.py (path:C:\Users\tylerbu\.virtualenvs\tmp\build\docutils\setup.py) egg_info for package docutils

            warning: no files found matching 'MANIFEST'
            warning: no files found matching '*' under directory 'extras'
            warning: no previously-included files matching '.cvsignore' found under directory '*'
            warning: no previously-included files matching '*.pyc' found under directory '*'
            warning: no previously-included files matching '*~' found under directory '*'
            warning: no previously-included files matching '.DS_Store' found under directory '*'
        Downloading/unpacking colorama (from Sphinx>=1.3.1->-r requirements.txt (line 1))
          Downloading colorama-0.3.3.tar.gz
          Running setup.py (path:C:\Users\tylerbu\.virtualenvs\tmp\build\colorama\setup.py) egg_info for package colorama

        Downloading/unpacking markupsafe (from Jinja2>=2.3->Sphinx>=1.3.1->-r requirements.txt (line 1))
          Downloading MarkupSafe-0.23.tar.gz
          Running setup.py (path:C:\Users\tylerbu\.virtualenvs\tmp\build\markupsafe\setup.py) egg_info for package markupsafe

        Downloading/unpacking pytz>=0a (from babel>=1.3->Sphinx>=1.3.1->-r requirements.txt (line 1))
        Installing collected packages: Sphinx, sphinx-rtd-theme, sphinxcontrib-findanything, sphinxcontrib-httpdomain, Jinja2, alabaster, babel, six, Pygments, snowballstemmer, docutils, colorama, markupsafe, pytz
          Running setup.py install for sphinxcontrib-findanything

            Skipping installation of C:\Users\tylerbu\.virtualenvs\tmp\Lib\site-packages\sphinxcontrib\__init__.py (namespace package)
            Installing C:\Users\tylerbu\.virtualenvs\tmp\Lib\site-packages\sphinxcontrib_findanything-0.2.5-py2.7-nspkg.pth
          Running setup.py install for Jinja2

            warning: no files found matching '*' under directory 'custom_fixers'
            warning: no previously-included files matching '*' found under directory 'docs\_build'
            warning: no previously-included files matching '*.pyc' found under directory 'jinja2'
            warning: no previously-included files matching '*.pyc' found under directory 'docs'
            warning: no previously-included files matching '*.pyo' found under directory 'jinja2'
            warning: no previously-included files matching '*.pyo' found under directory 'docs'
          Running setup.py install for babel

            warning: no previously-included files matching '*' found under directory 'docs\_build'
            warning: no previously-included files matching '*.pyc' found under directory 'tests'
            warning: no previously-included files matching '*.pyo' found under directory 'tests'
            Installing pybabel-script.py script to C:\Users\tylerbu\.virtualenvs\tmp\Scripts
            Installing pybabel.exe script to C:\Users\tylerbu\.virtualenvs\tmp\Scripts
            Installing pybabel.exe.manifest script to C:\Users\tylerbu\.virtualenvs\tmp\Scripts
          Running setup.py install for snowballstemmer

            warning: no files found matching '*.py' under directory 'src'
          Running setup.py install for docutils

            warning: no files found matching 'MANIFEST'
            warning: no files found matching '*' under directory 'extras'
            warning: no previously-included files matching '.cvsignore' found under directory '*'
            warning: no previously-included files matching '*.pyc' found under directory '*'
            warning: no previously-included files matching '*~' found under directory '*'
            warning: no previously-included files matching '.DS_Store' found under directory '*'
          Running setup.py install for colorama

          Running setup.py install for markupsafe

            building 'markupsafe._speedups' extension
            ==========================================================================
            WARNING: The C extension could not be compiled, speedups are not enabled.
            Failure information, if any, is above.
            Retrying the build without the C extension now.


            ==========================================================================
            WARNING: The C extension could not be compiled, speedups are not enabled.
            Plain-Python installation succeeded.
            ==========================================================================
        Successfully installed Sphinx sphinx-rtd-theme sphinxcontrib-findanything sphinxcontrib-httpdomain Jinja2 alabaster babel six Pygments snowballstemmer docutils colorama markupsafe pytz
        Cleaning up...
        (tmp)C:\Users\tylerbu\code\Office-Online-Test-Tools-and-Documentation\docs [tylerbu +33 ~0 -0 | +0 ~3 -0]> pip install sphinxcontrib-domaintools
        Downloading/unpacking sphinxcontrib-domaintools
          Downloading sphinxcontrib-domaintools-0.1.tar.gz
          Running setup.py (path:C:\Users\tylerbu\.virtualenvs\tmp\build\sphinxcontrib-domaintools\setup.py) egg_info for package sphinxcontrib-domaintools

        Requirement already satisfied (use --upgrade to upgrade): Sphinx>=1.0 in c:\users\tylerbu\.virtualenvs\tmp\lib\site-packages (from sphinxcontrib-domaintools)
        Installing collected packages: sphinxcontrib-domaintools
          Running setup.py install for sphinxcontrib-domaintools

            Skipping installation of C:\Users\tylerbu\.virtualenvs\tmp\Lib\site-packages\sphinxcontrib\__init__.py (namespace package)
            Installing C:\Users\tylerbu\.virtualenvs\tmp\Lib\site-packages\sphinxcontrib_domaintools-0.1-py2.7-nspkg.pth
        Successfully installed sphinxcontrib-domaintools
        Cleaning up...

#.  Run the command ``pip install sphinxcontrib-domaintools`` (this is necessary due to a bug in the
    sphinxcontrib-domaintools installer).
#.  Now that all the pre-requisites are installed, you can build the documentation using the following command:
    ``make.bat html``. The built documentation will be output to ``build/html``.
