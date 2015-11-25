# -*- coding: utf-8 -*-
from path import Path


# Load shared config file
execfile(Path('../../_shared/conf.py').abspath())


def setup(app):
    # noinspection PyUnresolvedReferences
    common_setup(app)

    app.add_stylesheet('rest.css')


# -- General configuration -----------------------------------------------------

project = u'WOPI REST Documentation'

# Configure sphinx.ext.intersphinx
# noinspection PyUnresolvedReferences
intersphinx_mapping = {
    'officeonline':
        ('https://wopi.readthedocs.org/en/latest/',
         (
             # Try to load from the locally built docs first
             officeonline_doc_path / local_object_inventory_path,

             # Fall back to loading from the built docs on readthedocs
             'https://wopi.readthedocs.org/' + rtd_object_inventory_path
         )),
}
