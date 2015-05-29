
..  _Host page:

|stub-icon| Building a host page
================================

..  include:: /fragments/stub.rst

.. |issue| issue:: 4



..  _Passing access tokens securely:

|stub-icon| Passing access tokens securely
------------------------------------------

..  todo:: :issue:`35`

..  _wd Parameters:

Passing through wd* parameters
------------------------------

Office Online will sometimes pass additional query string parameters to your host page. These query string parameters
are of the form ``wd*``. When you receive these query string parameters on your host page URLs, you must pass them,
unchanged, to the Office Online iframe.

In addition, if the `replaceState`_ method from the HTML5 History API is available in the user's browser, you should
remove the following parameters from your outer frame URL after passing them to the Office Online iframe:

* wdPreviousSession
* wdPreviousCorrelation

Other ``wd*`` parameters do not need to be removed from your outer frame URL.

..  Hyperlinks

..  _replaceState: https://developer.mozilla.org/en-US/docs/Web/Guide/API/DOM/
                   Manipulating_the_browser_history#The_replaceState()_method
