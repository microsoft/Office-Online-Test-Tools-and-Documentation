
.. _style guide:

Office Online Documentation Style Guide
=======================================

Whitespace guidelines
---------------------

* Use spaces everywhere for whitespace. Do not use tabs.
* Use a single space in between sentences.
* Tab length: 4 spaces


Heading styles
--------------

The Office Online documentation should use the following characters for header underlines:

1.  ``=====`` (equals sign)
2.  ``-----`` (dashes)
3.  ``~~~~~`` (tildes)
4.  ``^^^^^`` (carets)
5.  ``"""""`` (double-quotes)
6.  ``*****`` (asterisks)

Overlines should never be used.

Example
~~~~~~~

..  code-block:: rst

    Header level 1
    ==============

    Header level 2
    --------------

    Header level 3
    ~~~~~~~~~~~~~~

    Header level 4
    ^^^^^^^^^^^^^^

    Header level 5
    """"""""""""""

    Header level 6
    **************


Note/admonition styles
----------------------

Note/admonition sections will be styled appropriately so they stand out from the rest of the text in a section. The
standard reStructuredText directives such as ``..  note::`` can be used, as well as some custom directives using
the ``..  admonition::`` directive. See the below examples for more information.

Examples
~~~~~~~~

..  note::

    This is a note using the ``..  note::`` directive.

    ..  code-block:: rst

        ..  note::

            This is a note using the ``..  note::`` directive.


..  tip::

    This is a tip using the ``..  tip::`` directive.

    ..  code-block:: rst

        ..  tip::

            This is a tip using the ``..  tip::`` directive.


..  warning::

    This is a warning using the ``..  warning::`` directive.

    ..  code-block:: rst

        ..  warning::

            This is a warning using the ``..  warning::`` directive.


..  danger::

    This is a danger message using the ``..  danger::`` directive.

    ..  code-block:: rst

        ..  danger::

            This is a warning using the ``..  danger::`` directive.


..  admonition:: OneNote Online Note

    This is an OneNote Online note using the ``..  admonition::`` directive.

    ..  code-block:: rst

        ..  admonition:: OneNote Online Note

            This is an Office Online note using the ``..  admonition::`` directive.


..  admonition:: Excel Online Note

    This is an Excel Online note using the ``..  admonition::`` directive.

    ..  code-block:: rst

        ..  admonition:: Excel Online Note

            This is an Excel Online note using the ``..  admonition::`` directive.


..  admonition:: Office Online Tip

    This is an Office Online tip using the ``..  admonition::`` directive.

    ..  code-block:: rst

        ..  admonition:: Office Online Tip

            This is an Office Online tip using the ``..  admonition::`` directive.


..  admonition:: Pre-release Content

    This is a pre-release content warning using the ``..  admonition::`` directive.

    ..  code-block:: rst

        ..  admonition:: Pre-release Content

            This is a pre-release content warning using the ``..  admonition::`` directive.


..  admonition:: Pre-release Feature

    This is a pre-release feature warning using the ``..  admonition::`` directive.

    ..  code-block:: rst

        ..  admonition:: Pre-release Feature

            This is a pre-release feature warning using the ``..  admonition::`` directive.
