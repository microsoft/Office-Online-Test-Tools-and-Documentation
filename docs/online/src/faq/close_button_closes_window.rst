
.. meta::
    :robots: noindex

..  _avoid CloseButtonClosesWindow:

Why should I avoid using the CloseButtonClosesWindow property in |wac|?
=======================================================================

Browsers prevent JavaScript from closing windows that aren't owned by the script that calls
``window.close``, so in many cases :term:`CloseButtonClosesWindow` will not behave properly. In such cases
an error may be logged on the browser developer console::

    Scripts may close only the windows that were opened by it.

The calling script is not reliably given any information that the ``close()`` call failed, so |wac| can't detect when
this happens and change behavior. Thus, hosts who wish to close the current window when the *Close* UI is activated
should prefer to use :term:`ClosePostMessage` and handle the :js:data:`UI_Close` message for reliable close behavior.
