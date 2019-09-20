
..  _intro:

Integrating with |Office iOS Android|
=====================================

You can integrate with |Office iOS Android| to enable your users to view and edit Excel, PowerPoint, and Word files directly
on their mobile devices.

If you offer both iOS and Android experience, then we recommend you integrate both the experiences simultaneously.
However, You can choose to onboard either of the experience first depending on your priority. Please note that once you
have onboarded to WOPI APIs for any of the one experience i.e. Android or iOS experience, integrating with the other
experience is very minimal work.

.. figure:: /images/scenario_edit.png
   :alt: A screenshot that shows editing a file in |Office iOS|.
   :align: center

   Editing a file in |Office iOS|

.. figure:: /images/scenario_edit_android.png
   :alt: A screenshot that shows editing a file in |Office Android|.
   :align: center

   Editing a file in |Office Android|

If you deliver an iOS and/or Android experience that allows your users to store Office files or includes Office files as a key
part of your solution, you now have the opportunity to integrate |Office iOS Android| into your experience. This
integration works directly against files stored by you. Your users won't need a separate storage solution to view
and edit Office files.


Editing and viewing Office files
--------------------------------
When you integrate with |Office iOS Android|, your users can view and edit Excel, PowerPoint, and Word files directly on the
mobile device. The level of editing support is determined by the type of user and whether they have an Office 365
subscription.


Integration process
-------------------

You can use the Web Application Open Platform Interface (WOPI) protocol to integrate |Office iOS Android| with your
application. The WOPI protocol enables |Office iOS Android| to access and change files that are stored in your service.

To integrate your application with |Office iOS Android|, you need to do the following:

#.  Become a member of the |cspp|. You can learn more about the program, as well
    as how to apply, at `|cspp|. <http://dev.office.com/programs/officecloudstorage>`_
#.  Provide the required on-boarding information as described in the section titled :ref:`onboarding`.
#.  Obtain your ProviderId and app store URLs from Microsoft. The app store URLs are the URLs you should use to
    launch Office on a platform's app store.
#.  Add your domain to the WOPI domain :ref:`allow list <allow list>`. This is required even if you do not integrate
    with |wac| so you can verify your WOPI integration with the :ref:`validator app <Validator app>`.
#.  Implement the WOPI protocol. The set of WOPI operations that must be supported is described in the section titled
    :ref:`requirements`.
#.  Implement required changes to your app. Contact your |Office iOS Android| integration contacts to receive directions to
    run |Office iOS Android| in test mode, to enable testing.
#.  Run the :ref:`validator app <Validator app>` and fix any issues until the validator reports a 100% pass rate.
#.  Complete the manual testing outlined in the :ref:`Manual Validation section <Manual Validation>` to verify the
    integration works as expected.
#.  Once all manual test cases have a 100% pass rate, send your
    :download:`Manual Validation report </testing/ManualTestPass.docx>` and directions to run your validator.
#.  Once the |Office iOS Android| team completes their manual testing, they will work with you to schedule your launch date.


Authentication
~~~~~~~~~~~~~~

Authentication is handled by passing |Office iOS Android| an access token that you generate from a Bootstrapper URL. Assign
this token a reasonable expiration date. Also, we recommend that tokens be valid for a single user against a single
file to help mitigate the risk of token leaks.


Requirements
~~~~~~~~~~~~

* You need to ensure that files are represented by a unique ID. See the full list of
  :term:`File ID requirements <File ID>`.
* You should have a mechanism for identifying file versions. See the :term:`Version requirements <Version>`.
* In order to integrate with |Office iOS Android|, there are also a few promotional requirements which include:

  * Promoting |Office iOS Android| integration somewhere within your app
  * Promoting |Office iOS Android| integration in the context of editing and viewing Office documents
  * Using Office as the default app for opening Office documents within your app

Security Considerations
~~~~~~~~~~~~~~~~~~~~~~~

|Office iOS Android| is designed to work for enterprises that have strict security requirements. To make sure your
integration is as secure as possible, ensure that:

* All traffic is SSL encrypted
* Server needs to support TLS 1.0+
* OAuth 2.0 is supported

Interested?
-----------
If you're interested in integrating your solution with |Office iOS Android|, take a moment to register at
`Office 365 Cloud Storage Partner Program. <http://dev.office.com/programs/officecloudstorage>`_
