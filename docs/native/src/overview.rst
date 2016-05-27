
..  _intro:

Integrating with Office for iOS
===============================
You can integrate with Office for iOS to enable your users to view and edit Excel, PowerPoint, and Word files directly on the iPad and iPhone.

If you deliver a iOS-based experience that allows your users to store Office files or includes Office files as a key part of your solution, you now have the opportunity to integrate Office for iOS into your experience. This integration works directly against files stored by you. Your users won't need a separate storage solution to view and edit Office files.

Editing & Viewing Office files
------------------------------
When you integrate with Office for iOS, your users can view and edit Excel, PowerPoint, and Word files directly on the iOS device. The level of editing support is determined by the type of user and whether they have an O365 subscription. Here is a table of the key points about edit support. Please note this only applies to devices smaller than 10.1".

..  note::
    * MSA Free: the user has a Microsoft account, but no O365 subscription
    * MSA Subscription: the user has a Microsoft account and an individual O365 subscription
    * Org subscription: the user belongs to an organization which has an O365 subscription
    * Premium editing: refers to certain Office for iOS edit functionality which requires an O365 subscription to use

================= ===============  ===============   ===============
ACCOUNT TYPE      Consumer         Enterprise        EDU
================= ===============  ===============   ===============
Not signed in     View only	       View only	       Free edit
MSA Free          Free Edit	       View only	       Free edit
MSA Subscription  Premium editing  Premium editing	 Premium editing
Org Subscription  Premium editing  Premium editing	 Premium editing
================= ===============  ===============   =============== 


Integration process
-------------------

You can use the Web Application Open Platform Interface (WOPI) protocol to integrate Office for iOS with your application. The WOPI protocol enables Office for iOS to access and change files that are stored in your service.

To integrate your application with Office for iOS, you need to do the following:
 #. Become a member of the Office 365 - Cloud Storage Partner Program. Currently integration with Office for iOS using WOPI is only available to cloud storage partners. You can learn more about the program, as well as how to apply, at http://dev.office.com/programs/officecloudstorage.
 #. Provide the required on-boarding information as described in the section titled :ref:`Onboarding information <onboarding>`.
 #. Obtain your ProviderId and app store URLs from Microsoft. The app store URLs are the URLs you should use to launch Office on a platform's app store. 
 #. Add your domain to the WOPI domain :ref:`allow list <allow list>`. This is required even if you do not integrate with Office Online so you can verify your WOPI integration with the :ref:`validator app <Validator app>`.
 #. Implement the WOPI protocol - a set of REST endpoints that expose information about the documents that you want to view or edit in Office for iOS. The set of WOPI operations that must be supported is described in the section titled :ref:`WOPI implementation requirements for Office for iOS integration <requirements>`.
 #. Implement required changes to your app, including all applicable promotion requirements, as well as :ref:`opening files from your app into Office <OpenFilesiOSOffice>`. Contact your Office for iOS integration contacts to receive directions to run Office for iOS in test mode, to enable testing. 
 #. Run the :ref:`validator app <Validator app>` and fix any issues until the validator reports a 100% pass rate. 
 #. Complete the manual testing outlined in the :ref:`Manual Validation section <Manual Validation>` to verify the integration works as expected. 
 #. Once all manual test cases have a 100% pass rate, send your :download:`Manual Validation document <../testing/PartnerTestCasesReport.docx>`to the Office for iOS integration contacts so they can schedule manual testing. In addition to the Manual Validation results, please also include directions to run your validator, which should be 100% PASSED or SKIPPED. We will need accounts and access to your production service to complete final testing. 
 #. Once the Office for iOS team completes their manual testing, they will work with you to schedule your launch date. 
	
Authentication
~~~~~~~~~~~~~~

Authentication is handled by passing Office for iOS an access token that you generate from a Bootstrapper URL. Assign this token a reasonable expiration date. Also, we recommend that tokens be valid for a single user against a single file to help mitigate the risk of token leaks.

Requirements
~~~~~~~~~~~~

* You need to ensure that files are represented by a unique ID. See the full list of :ref:`File ID requirements <Concepts>`.
* You should have a mechanism for identifying file versions. See the :ref: `Version requirements. <http://officeonline.readthedocs.io/projects/wopirest/en/latest/files/CheckFileInfo.html#term-version>`
* In order to integrate with Office for iOS, there are also a few promotional requirements which include:
   * Promoting Office for iOS integration somewhere within your app
   * Promoting Office for iOS integration in the context of editing & viewing Office documents
   * Using Office as the default app for opening Office documents within your app

Security Considerations
-----------------------

Office for iOS is designed to work for enterprises that have strict security requirements. To make sure your integration is as secure as possible, ensure that:
	* All traffic is SSL encrypted.
	* Server needs to support TRS 1.0+ 
	* OAuth 2.0 is supported
	
Interested?
-----------
If you're interested in integrating your solution with Office for iOS, take a moment to register at :ref:`Office 365 Cloud Storage Partner Program<http://dev.office.com/programs/officecloudstorage>. `
