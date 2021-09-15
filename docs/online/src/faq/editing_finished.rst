
.. meta::
    :robots: noindex

How does a WOPI host know when an editing session is finished?
==============================================================

While editing a file, a WOPI client will always maintain a WOPI lock on the file. When editing is complete, the file
will be unlocked using the :ref:`Unlock` WOPI operation. Thus, once the file is successfully unlocked, the editing
session is completed.

WOPI clients will always call :ref:`Unlock` at the end of an editing session, unless something happens that prevents
the session from closing cleanly, such as a browser crash or a network dropout. In those cases the WOPI lock eventually
times out, which is fundamentally equivalent to an explicit Unlock request.

..  seealso::

    In :ref:`co-authoring sessions<coauth>`, the :ref:`Unlock` operation will only be called when the last user editing
    the document ends their session. Thus, if hosts wish to track what users are editing a document, they cannot rely
    completely on the locked state of the document. Hosts can use the :data:`Edit_Notification` message to help gauge
    activity within a co-authoring session.
