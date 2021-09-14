
.. meta::
    :robots: noindex

..  _changelog:

What's New
==========

..  _2016.01.27:

Version 2016.01.27
------------------

*January 27, 2016*

* |wac| co-authoring is now supported across all WOPI hosts. See :ref:`coauth` for more information.
* Several new endpoints and operations have been added to WOPI to support |Office iOS|. Some documentation is
  still in progress. See :ref:`wopirest:index` for more information.


Commits
~~~~~~~

..  git_changelog::
    :rev-list: e4d1a90..105ef26


..  _2015.12.18:

Version 2015.12.18
------------------

*December 18, 2015*

The documentation of the WOPI REST operations has been split from the |wac| integration documentation. This
was done to prepare for the expansion of the WOPI REST API that will come in a future commit.


Commits
~~~~~~~

..  git_changelog::
    :rev-list: b624ab9..c6a8b86


..  _2015.08.03:

Version 2015.08.03
------------------

*August 3, 2015*

|wac| now supports a new WOPI operation, :ref:`PutUserInfo`, as well as the following supporting
:ref:`CheckFileInfo` properties:

* :term:`SupportsUserInfo`
* :term:`UserInfo`

These operations and properties can be used to improve the :ref:`subscription verification experience <Business
editing>` for business users.


Commits
~~~~~~~

..  git_changelog::
    :rev-list: 918ef79..6771d9a
