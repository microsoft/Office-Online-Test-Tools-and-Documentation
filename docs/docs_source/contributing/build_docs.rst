
.. _building docs:

Building this documentation locally
===================================

..  spelling::

    exe


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

Checking spelling
-----------------

If you want to check the spelling of the documentation, use the ``check_spelling.bat`` command. This will output a
list of potentially misspelled words, along with the file in which the word was found and suggested replacement
words. The output of the spell check will also be in the ``build/spelling/output.txt`` file::

    contributing\build_docs.rst:47: (spellling) ["spelling", "spell ling"]
    contributing\build_docs.rst:52: (mispelled) ["misspelled", "dispelled", "mi spelled", "spelled", "misspell", "misperceived", "misplayed"]
    contributing\build_docs.rst:58: (mispelled) ["misspelled", "dispelled", "mi spelled", "spelled", "misspell", "misperceived", "misplayed"]

..  tip::
    The spell checker is not aware of reStructuredText includes, which are used often in the documentation. This
    means that the line numbers reported by the spell checker will likely be incorrect. In addition, spelling errors
    within any included fragment will be reported as coming from the pages in which they're included. If an included
    fragment is used in multiple pages, each page in which it is included will report the error.

Adding words to the ignore list
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

You can add words to the ``docs_source/spelling_wordlist.txt`` file to globally ignore the word as misspelled. Add a
single word per line in alphabetical order.

Alternatively, you can use the ``spelling`` directive to add a list of known words to a specific file::

    ..  spelling::

        wopi
        CheckFileInfo
