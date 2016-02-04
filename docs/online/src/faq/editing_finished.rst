
How does a WOPI host know when an editing session is finished?
==============================================================

In order to tell when an editing session is complete, a host should use the :ref:`Unlock` WOPI operation.

WOPI clients will always call :ref:`Unlock` at the end of an editing session, unless something happens that prevents
the session from closing cleanly (e.g. browser crash, network dropouts, etc.) In those cases the lock eventually times
out, which is fundamentally equivalent to an explicit Unlock request.
