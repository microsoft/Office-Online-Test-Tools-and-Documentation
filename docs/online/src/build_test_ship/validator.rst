
..  _validator:

WOPI Validation application
===========================

..  admonition:: Status

    :html:`<div id="validator_issues"></div>`

..  tip::
    The WOPI validator is also available as an open-source tool. See
    https://github.com/Microsoft/wopi-validator-core for more information.

To assist hosts in verifying their WOPI implementation, Office Online provides a WOPI Validation application that
executes a test suite against a host's WOPI implementation. The test suite verifies a variety of things, including that
the semantics for all of the WOPI operations (:ref:`CheckFileInfo`, :ref:`GetFile`, :ref:`PutFile`, etc.) are correct
and that request/response headers are set properly. New tests are added regularly.

The WOPI Validation application is an Office Online application similar to Word Online or PowerPoint Online.
It uses the ``.wopitest`` file extension. The WOPI Validation application is included in the :ref:`discovery`
XML just like all other Office Online applications.

..  important::

    WOPI hosts should use the :ref:`test environment` to ensure that they are running the latest version of
    the WOPI validation application.

..  code-block:: xml

    <app name="WopiTest" checkLicense="true">
        <action name="view" urlsrc="https://onenote.officeapps-df.live.com/hosting/WopiTestFrame.aspx" ext="wopitest"/>
        <action name="getinfo" urlsrc="https://onenote.officeapps-df.live.com/hosting/GetWopiTestInfo.ashx" ext="wopitest"/>
    </app>

The validation application provides two :ref:`WOPI Actions`, :wopi:action:`view` and :wopi:action:`getinfo`, which
can be used to trigger the test suite.

..  warning::

    The test suite will test operations like :ref:`PutFile`, so the contents of the ``.wopitest`` file will be
    destroyed.

    In addition, some tests create new files or containers using the :ref:`PutRelativeFile`, :ref:`CreateChildFile`,
    and :ref:`CreateChildContainer` operations. While the validation application attempts to clean up these files, if
    there are errors in the WOPI implementation, these clean up actions may fail, leaving behind these test files.

    If that should happen, you must clean up these test files manually. If you don't, subsequent test runs may fail.


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
#. The WOPI validation application will load and display a number of test groups. Each test group can be expanded to
   reveal the individual tests that it contains. You can run tests individually, by test group, or run all tests
   using the :guilabel:`Run All` button.

..  figure:: /images/validator.png
    :alt: An image showing the WOPI validation application user interface.

    WOPI validation application UI

Tests can either pass, fail, or be skipped. Before executing any tests, Office Online will do some basic validation
(e.g. confirm the file really has the ``.wopitest`` file extension) and check any applicable pre-requisites. Any test
whose pre-requisites are not met will simply be skipped. For example, the tests in the :guilabel:`EditFlows` test
group require the :term:`SupportsUpdate` property to be set to ``true``. If it is not, the tests in that group will
all be skipped.

..  figure:: /images/validator_used.png
    :alt: An image showing the WOPI validation application after the entire test suite has been run.

    Tests can pass, fail, or be skipped

Once a test has been run, you can click on it to see the each request that was issued by the test and the response
data. If the test failed or was skipped, the reason will be displayed just under the test name. You can click on the
specific request that failed and see more information about what the test was expecting. If you are implementing
:ref:`proof key validation <proof keys>`, you can use the :guilabel:`Current Proof Key Data` and
:guilabel:`Old Proof Key Data` buttons to see the intermediate data on how the request was signed, which is extremely
useful when debugging a proof key validation implementation.

..  figure:: /images/validator_error.png
    :alt: An image showing WOPI validation results for a particular test.

    Example WOPI validation results

..  tip::

    For ease of testing, we strongly recommend that hosts support the ``.wopitest`` file extension just like all other
    file extensions supported by Office Online and included in :ref:`WOPI discovery`. This is especially important
    while testing, since it provides any user a quick and easy way to execute the validation test suite.


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


Automated WOPI validation using a command-line tool
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

There are two options to automate WOPI validation. The first is to use the open-source validation tool at
https://github.com/Microsoft/wopi-validator-core. This tool runs the same test suite as the hosted validator, but
does not rely on any |wac| server infrastructure. This make it ideal for use in automated testing or for testing
servers that are not connected to the internet.

The second option is to use the Python-based command-line tool at
https://github.com/Microsoft/wopi-validator-cli-python instead of launching a :term:`host page`. This tool uses
the :wopi:action:`getinfo` action URL provided in :ref:`WOPI discovery` to execute the :ref:`validator`, so it is
simply an alternative way of executing the test cases in the hosted validator.
