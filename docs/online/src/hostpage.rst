
.. meta::
    :robots: noindex

..  _Host page:

Building a host page
====================

In order to instantiate the |wac| applications, a host must create an HTML page that will host an ``iframe``
element within it pointing to a particular :ref:`WOPI action URL <WOPI Actions>`. A host page provides a number of
benefits, including:

#. Since the |wac| application is hosted within a host page, the host page can communicate with the frame
   easily using :ref:`PostMessage <PostMessage>`. This allows a richer integration between the host and |wac|.
#. URLs displayed in the user's browser are host URLs. This means that from a user's perspective, they are
   interacting with the host, not |wac|. This also ensures that URLs that are copied and pasted by users are
   host URLs, not |wac| URLs.

The host page is typically very simple; it must meet only the following requirements:

* It must use a ``form`` element and :http:method:`POST` the :term:`access token` and :term:`access_token_ttl`
  values to the |wac| iframe :ref:`for security purposes <Passing access tokens securely>`.
* It must include any JavaScript needed to interact with the |wac| iframe using
  :ref:`PostMessage <PostMessage>`.
* It must manage :ref:`wd* query string parameters <wd Parameters>`.
* It must apply some specific CSS styles to the ``body`` element and |wac| iframe to avoid visual bugs.
* It must declare a ``viewport`` meta tag to avoid visual and functional problems in mobile browsers.

Host page example
-----------------

The `GitHub repository`__ contains a `minimal example host page`__. Note that it does not illustrate managing
:ref:`wd* query string parameters <wd Parameters>` or :ref:`PostMessage`. The sections below will refer to it in more detail.

__  https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation
__  https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/SampleHostPage.html


..  _Passing access tokens securely:

Passing access tokens securely
------------------------------

It is important, for security purposes, that access tokens not be passed to the |wac| iframe as a query
string parameter. Doing so would greatly increase the likelihood of token leakage. To avoid this problem, hosts
must pass the :term:`access token` and :term:`access_token_ttl` values to the |wac| iframe using a form
:http:method:`POST`. This technique is illustrated, along with :ref:`dynamic iframe creation<iframe behavior>`, in
:numref:`code sample %s <secure-tokens-sample>`.


..  _iframe behavior:

Working around browser iframe behavior
--------------------------------------

Some browsers exhibit strange behavior with iframes when using bookmarks or the browser forward/back buttons. In some
cases, this will cause the |wac| iframe to be loaded twice in a single navigation. This in turn can cause
'file locked' or 'access token expired' errors for users. In addition, sometimes the iframe is not recreated at all,
which causes the |wac| application to load with the previous session's state. This may cause a session to
ultimately fail for a variety of reasons, including an expired :abbr:`CSRF (Cross-Site Request Forgery)` token.

In order to work around this behavior, hosts must dynamically create the |wac| iframe using JavaScript,
then dynamically submit it. This technique is illustrated in the sample host page:

..  literalinclude:: ../../../samples/SampleHostPage.html
    :caption: Markup from `SampleHostPage.html`_ illustrating how to dynamically create the |wac| iframe and
              pass access tokens securely
    :name: secure-tokens-sample
    :language: html
    :linenos:
    :lineno-match:
    :lines: 39-64

Note that in an actual implementation, the ``<OFFICE_ONLINE_ACTION_URL>``, ``<ACCESS_TOKEN_VALUE>``, and
``<ACCESS_TOKEN_TTL_VALUE>`` strings must be replaced with appropriate :ref:`action URL<Action URLs>`,
:term:`access token`, and :term:`access_token_ttl` values.


..  _wd Parameters:

Passing through wd* parameters
------------------------------

|wac| will sometimes pass additional query string parameters to your host page. These query string parameters
are of the form ``wd*``. When you receive these query string parameters on your host page URLs, you must pass them,
unchanged, to the |wac| iframe.

In addition, if the `replaceState`_ method from the HTML5 History API is available in the user's browser, you should
remove the following parameters from your host page URL after passing them to the |wac| iframe:

* wdPreviousSession
* wdPreviousCorrelation

Other ``wd*`` parameters must not be removed from the host page URL.


..  _Host page headers:

Host page headers
------------------------------

If the host page headers are not correctly set, some browsers may cache the response which can result in the host page
not properly reloading when the user navigates to it. This can result in errors if the user reloads a cached
page after the :term:`access token`, or :term:`access_token_ttl` have expired. One way this can happen is by reloading
the page using the forward/back buttons. For more information about cache management refer to :rfc:`7234`.

To prevent this, at the very least, the following headers should be set on the host page.

* :http:header:`Cache-Control`: no-cache, no-store
* :http:header:`Expires`: -1
* :http:header:`Pragma`: no-cache

Other headers, such as :http:header:`Date` and :http:header:`Vary` can be useful as well.


Applying appropriate CSS styles
-------------------------------

To ensure that the |wac| applications behave appropriately when displayed through the host page, the host page
must apply some specific CSS styles to the |wac| iframe (lines 22-33) and the ``body`` element (lines 15-20) as
well as set a ``viewport`` meta tag for mobile browsers (lines 11-12). In addition, the host page should set an
appropriate favicon for the page using the :ref:`favicon URL provided in WOPI discovery <favicons>`.

All of these requirements are illustrated in the
`sample host page <https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/SampleHostPage.html>`_:

..  literalinclude:: ../../../samples/SampleHostPage.html
    :caption: Markup from `SampleHostPage.html`_ illustrating appropriate styles and favicons in a host page
    :language: html
    :linenos:
    :lineno-match:
    :lines: 3-38
    :emphasize-lines: 9-10, 12-13, 16-21, 23-34


..  Hyperlinks

..  _SampleHostPage.html:
    https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/SampleHostPage.html

..  _replaceState: https://developer.mozilla.org/en-US/docs/Web/Guide/API/DOM/
                   Manipulating_the_browser_history#The_replaceState()_method
