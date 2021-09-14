
.. meta::
    :robots: noindex

..  _Performance:

Performance
===========

..  _Preloading static content:

Preloading static content
-------------------------

One way to improve the load time performance of |wac| applications is to preload |wac|'s static content
(JavaScript, CSS, and images) into the user's browser cache. This will help ensure that when the user opens a
document in |wac|, they can use the previously cached static content and do not need to download that data
when they first try to load |wac|.

To support preloading static content, |wac| provides two WOPI actions for each |wac| application in its
discovery XML, one to preload static content for the :wopi:action:`view` action (:wopi:action:`preloadview`), and a
second to preload static content for the :wopi:action:`edit` action (:wopi:action:`preloadedit`).

Hosts can use these URLs just like they use other :ref:`Action URLs`, by pointing iframes in their pages at the action
URL. Unlike most action URLs, the WopiSrc and access token do not need to be specified in order to use these
actions.

Hosts can include both :wopi:action:`preloadview` and :wopi:action:`preloadedit` in their pages to preload static
content for both. Note that the static content preload actions contain the :term:`UI_LLCC` placeholder value, which
should be replaced with an appropriate language for the user so that the proper localized static content is preloaded.

..  tip::

    If you wish to preload static content for all applications and both view and edit modes, you must load multiple
    actions, one for each application/mode combination.


..  _View performance:

|stub-icon| Optimizing document viewing for high volume
-------------------------------------------------------

..  include:: ../../_shared/stub.rst

..  |issue| issue:: 5
