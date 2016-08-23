
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
