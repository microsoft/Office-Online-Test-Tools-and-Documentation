
..  _common issues:

Common implementation problems
==============================

When an |wac| application loads, it immediately displays a 'Session expired' error
----------------------------------------------------------------------------------

This is typically caused by supplying an invalid :term:`access_token_ttl` value. Despite its misleading name, it does
*not* represent a duration of time for which the access token is valid. See
:term:`the documentation on access_token_ttl<access_token_ttl>` for more information.


The |wac| applications don't load in the correct mode; e.g. they load in view mode instead of edit mode
-------------------------------------------------------------------------------------------------------

The most common cause of this behavior is that the :ref:`action URL<Action URLs>` for the application is not being
generated properly. Check that your action URLs match the templates provided in the **urlsrc** property in
:ref:`discovery`. Common mistakes include removing required parameters from the query string or not properly
removing/filling in :ref:`placeholder values`.


After editing a document in |wac|, opening the same document in view mode doesn't contain the changes
-----------------------------------------------------------------------------------------------------

The |wac| applications utilize a :ref:`cache<cache>`, so the most likely reason for seeing stale content in view mode
is because you have not properly updated the :term:`Version` value you return in :ref:`CheckFileInfo`.

If you are providing a :term:`SHA256` value, ensure that this value is also recalculated properly when the file content
changes.


When co-authoring in |wac|, all users have the name "Guest"
-----------------------------------------------------------

|wac| applications use the :term:`UserFriendlyName` if provided, so check that you are providing that value
in :ref:`CheckFileInfo`.
