
..  _validator:

WOPI Validation application
===========================

..  admonition:: Status

    :html:`<div id="validator_issues"></div>`

To assist hosts in verifying their WOPI implementation, Office Online provides a WOPI Validation application that
executes a test suite against a host's WOPI implementation. The test suite verifies a variety of things, including that
the semantics for all of the WOPI operations (:ref:`CheckFileInfo`, :ref:`GetFile`, :ref:`PutFile`, etc.) are correct
and that request/response headers are set properly. New tests are added regularly.

The WOPI Validation application is an Office Online application similar to Word Online or PowerPoint Online.
It uses the ``.wopitest`` file extension. The WOPI Validation application is included in the :ref:`discovery`
XML just like all other Office Online applications.

..  code-block:: xml

    <app name="WopiTest" checkLicense="true">
        <action name="view" urlsrc="https://onenote.officeapps-df.live.com/hosting/WopiTestFrame.aspx" ext="wopitest"/>
        <action name="getinfo" urlsrc="https://onenote.officeapps-df.live.com/hosting/GetWopiTestInfo.ashx" ext="wopitest"/>
    </app>

The validation application provides two :ref:`WOPI Actions`, :wopi:action:`view` and :wopi:action:`getinfo`, which
can be used to trigger the test suite.


Interactive WOPI validation
---------------------------

The simplest way to use the validation application is to use the *view* action. To use the *view* action hosts should
treat ``.wopitest`` files the same way other Office documents are treated. In other words, hosts should do the
following:

#. Launch a :term:`host page` pointed at the ``.wopitest`` file. Ideally, this should be the same host page used to
   host regular Office Online sessions. This will allow the validation application to test things like PostMessage and
   do some validation on the way the Office Online iframe was loaded.
#. The host page will create and navigate the Office Online iframe to the *view* action URL provided in
   :ref:`WOPI discovery`. The :term:`WOPIsrc` and :term:`access token` should be provided just like with all other
   actions.
#. Office Online will do some basic validation (e.g. confirm the file really has the ``.wopitest`` extension) and then
   will start the WOPI test suite.
#. The test suite will test operations like :ref:`PutFile`, so the contents of the file will be destroyed.
#. Each test and its results will be listed on the page. The tests can be executed again simply by refreshing the page.

..  figure:: /images/validator.png
    :alt: An image showing WOPI validation results.

    Example WOPI validation results


..  tip::

    For ease of testing, we strongly recommend that hosts support the ``.wopitest`` file extension just like all other
    file extensions supported by Office Online and included in :ref:`WOPI discovery`. This is especially important
    while testing, since it provides any user a quick and easy way to execute the validation test suite.

..  warning::

    As part of the WOPI validation test suite, the contents of the ``.wopitest`` file will be destroyed.


..  _automated validation:

Automated WOPI validation
-------------------------

The WOPI Validation Application exposes a second action, :wopi:action:`getinfo`. The :wopi:action:`getinfo` action is
designed to be used server-to-server. Instead of launching a :term:`host page`, the host can simply do the
following:

#. Issue a :http:method:`GET` request to the *getinfo* action URL provided in :ref:`WOPI discovery`. The
   :term:`WOPIsrc`, :term:`access token`, and :term:`access_token_ttl` should be provided just like with all other
   actions.

   ..  note::
       The :wopi:action:`getinfo` action only supports :http:method:`GET` requests, so the :term:`access token`, and
       :term:`access_token_ttl` values must be appended to the URL instead of being passed as :http:method:`POST`
       parameters.

#. Office Online will do some basic validation (e.g. confirm the file really has the ``.wopitest`` extension) and then
   return a JSON-formatted array of test URLs.

#. Hosts should then make a :http:method:`GET` request to each test URL. Office Online will run the specified
   test and return results in a simple JSON object. No changes to the URL are needed; the necessary parameters are
   included already on the URL returned from the validation application.

This is intended for automated use. For example, a host may wish to run this validation as part of rolling out new
versions of their WOPI host.
