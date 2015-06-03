
..  _Performance:

|stub-icon| Performance
=======================

..  include:: /fragments/stub.rst

..  |issue| issue:: 5


..  _Preloading static content:

Preloading static content
-------------------------

One way to improve the load time performance of Office Online applications is to preload Office Online's static content
(JavaScript, CSS, and images) into the user's browser cache. This will help ensure that when the user opens a
document in Office Online, they can use the previously cached static content and do not need to download that data
when they first try to load Office Online.

To support preloading static content, Office Online provides two WOPI actions in its discovery XML, one to preload
static content for the :wopi:action:`view` action (:wopi:action:`preloadview`), and a second to preload static
content for the :wopi:action:`edit` action (:wopi:action:`preloadedit`).

Hosts can use these URLs just like they use other :ref:`Action URLs`, by pointing iframes in their pages at the action
URL. Hosts can include both :wopi:action:`preloadview` and :wopi:action:`preloadedit` in their pages to preload static
content for both. Note that the static content preload actions contain the :term:`UI_LLCC` placeholder value, which
should be replaced with an appropriate language for the user so that the proper localized static content is preloaded.


..  _View performance:

|stub-icon| Optimizing document viewing for high volume
-------------------------------------------------------

..  todo:: :issue:`5`
