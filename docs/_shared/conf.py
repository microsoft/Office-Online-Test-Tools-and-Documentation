# -*- coding: utf-8 -*-
import io
import os
import sys
from path import Path


def common_setup(app):
    app.add_stylesheet('custom.css')
    app.add_javascript('custom.js')
    app.add_javascript('https://cdnjs.cloudflare.com/ajax/libs/jquery-timeago/1.4.3/jquery.timeago.js')
    app.add_javascript('https://cdn.rawgit.com/showdownjs/showdown/1.2.3/dist/showdown.min.js')


def setup(app):
    common_setup(app)

# Add WOPI doc module path to the system path for module imports
sys.path.insert(0, Path('../../_wopi_sphinx/').abspath())

# Path setup
shared_content_path = Path('../../_shared/').abspath()
native_doc_path = Path('../../native/').abspath()
officeonline_doc_path = Path('../../online/').abspath()
rest_doc_path = Path('../../rest/').abspath()

local_object_inventory_path = 'build/html/objects.inv'
rtd_object_inventory_path = 'en/latest/objects.inv'

html_static_path = [Path('../../_static/').abspath()]
html_extra_path = [Path('../../_extra/').abspath()]

# -- General configuration -----------------------------------------------------

needs_sphinx = '1.2'

# The short X.Y version.
version = '1.1'
# The full version, including alpha/beta/rc tags.
release = '1.1'

on_rtd = os.environ.get('READTHEDOCS', None) == 'True'
if not on_rtd:  # only import and set the theme if we're building docs locally
    import sphinx_rtd_theme

    html_theme = 'sphinx_rtd_theme'
    html_theme_path = [sphinx_rtd_theme.get_html_theme_path()]

# The suffix of source filenames.
source_suffix = '.rst'

# The master toctree document.
master_doc = 'index'

# noinspection PyShadowingBuiltins
copyright = u'2015, Microsoft'

# List of patterns, relative to source directory, that match files and
# directories to ignore when looking for source files.
exclude_patterns = [
    '_fragments/*'
]

# The name of the Pygments (syntax highlighting) style to use.
pygments_style = 'sphinx'

# -- Extension configuration -----------------------------------------------------

# Add any Sphinx extension module names here, as strings. They can be extensions
# coming with Sphinx (named 'sphinx.ext.*') or your custom ones.
extensions = [
    'sphinx.ext.extlinks',
    'sphinx.ext.intersphinx',
    'sphinx.ext.todo',
    'sphinxcontrib.httpdomain',
    'sphinxcontrib.spelling',
    'sphinx_git',
    'issue_directive',
    'wopi_domain',
]

# Configure built-in extensions
numfig = True
numfig_format = {
    'figure': 'Figure %s',
    'table': 'Table %s',
    'code-block': 'Code sample %s'
}

with io.open(shared_content_path / 'rst_prolog.rst', encoding='utf-8') as prolog_file:
    rst_prolog = prolog_file.read()

# Configure sphinx.ext.extlinks
extlinks = {
    'issue': ('https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/issues/%s',
              'issue #')
}

# Configure sphinx.ext.todo
todo_include_todos = False

# Configure sphinxcontrib.httpdomain
http_strict_mode = True
http_headers_ignore_prefixes = ['X-WOPI-']

# Configure sphinxcontrib.spelling
spelling_show_suggestions = True
spelling_word_list_filename = (shared_content_path / 'spelling_wordlist.txt').abspath().normpath()
