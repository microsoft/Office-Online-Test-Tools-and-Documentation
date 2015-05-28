
..  _Host page:

Building a host page
====================

In order to instantiate the Office Online applications, a host must create an HTML page that will host an ``iframe``
element within it pointing to a particular :ref:`WOPI action URL <WOPI Actions>`. A host page provides a number of
benefits, including:

#. Since the Office Online application is hosted within a host page, the host page can communicate with the frame
   easily using :ref:`PostMessage <PostMessage>`. This allows a richer integration between the host and Office Online.
#. URLs displayed in the user's browser are host URLs. This means that from a user's perspective, they are
   interacting with the host, not Office Online. This also ensures that URLs that are copied and pasted by users are
   host URLs, not Office Online URLs.

The host page is typically very simple; it must meet only the following requirements:

* It must use a ``form`` element and POST the :term:`access token` and access_token_ttl values to the Office Online
  iframe :ref:`for security purposes <Passing access tokens securely>`.
* It must include any JavaScript needed to interact with the Office Online iframe using
  :ref:`PostMessage <PostMessage>`.
* It must manage :ref:`wd* query string parameters <wd Parameters>`.
* It must apply some specific CSS styles to the ``body`` element and Office Online iframe to avoid visual bugs.
* It must declare a ``viewport`` meta tag to avoid visual and functional problems in mobile browsers.

Host page example
-----------------

The `Office Online GitHub repository <repo>`_ contains a `minimal example host page <SampleHostPage.html>`_. Note
that it does not illustrate managing :ref:`wd* query string parameters <wd Parameters>` or :ref:`PostMessage`. The
sections below will refer to it in more detail.


..  _Passing access tokens securely:

Passing access tokens securely
------------------------------

It is important, for security purposes, that access tokens not be passed to the Office Online iframe as a query
string parameter. Doing so would greatly increase the likelihood of token leakage. To avoid this problem, hosts
should pass the access token and access_token_ttl values to the Office Online iframe using a form POST. This technique
is illustrated in the sample host page:

..  literalinclude:: ../../samples/SampleHostPage.html
    :caption: Markup from `SampleHostPage.html`_ illustrating how to submit access tokens securely
    :language: html
    :linenos:
    :lineno-match:
    :lines: 36-45

In an actual implementation, the ``<OFFICE_ONLINE_ACTION_URL>``, ``<ACCESS_TOKEN_VALUE>``, and
``<ACCESS_TOKEN_TTL_VALUE>`` strings should be replaced with appropriate values.


..  _wd Parameters:

Passing through wd* parameters
------------------------------

Office Online will sometimes pass additional query string parameters to your host page. These query string parameters
are of the form ``wd*``. When you receive these query string parameters on your host page URLs, you must pass them,
unchanged, to the Office Online iframe.

In addition, if the `replaceState`_ method from the HTML5 History API is available in the user's browser, you should
remove the following parameters from your host page URL after passing them to the Office Online iframe:

* wdPreviousSession
* wdPreviousCorrelation

Other ``wd*`` parameters must not be removed from the host page URL.


Applying appropriate CSS styles
-------------------------------

To ensure that the Office Online applications behave appropriately when displayed through the host page, the host page
must apply some specific CSS styles to the Office Online iframe (lines 20-31) and the ``body`` element (lines 14-18) as
well as set a ``viewport`` meta tag for mobile browsers (line 11). All of these requirements are illustrated in the
sample host page:

..  literalinclude:: ../../samples/SampleHostPage.html
    :caption: Markup from `SampleHostPage.html`_ illustrating appropriate styles in a host page
    :language: html
    :linenos:
    :lineno-match:
    :lines: 3-33
    :emphasize-lines: 9, 11-29


..  Hyperlinks

..  _SampleHostPage.html:
    https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/SampleHostPage.html

..  _replaceState: https://developer.mozilla.org/en-US/docs/Web/Guide/API/DOM/
                   Manipulating_the_browser_history#The_replaceState()_method
