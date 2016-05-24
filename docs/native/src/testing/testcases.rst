Partner test cases
======================

The following test cases must be passing at 100% to continue with Office for iOS integration. Please download the :file:'PartnerTestCasesReport.docx' Partner Test Cases document and send it to the Office for iOS integration contacts when your testing is complete.

See :download:`Report <../PartnerTestCasesReport.docx>`

-------------------------------------
Notes
-------------------------------------
* "Company app/application" refers to your app. 
* "Company service" refers to your service. 
* Where it refers to "Office", please substitute Word, Excel and PowerPoint app. The tests should be re-run against each Office application. 
* Please note the version of the Office app you tested against. This information can be found under Settings -> [App] -> Version



.. |Duplicate| image:: /images/PartnerTestCases_Duplicate.png  
    :alt: A screenshot that shows the document actions in Office for iOS. 
.. |ImageProps1| image:: /images/PartnerTestCases_Properties1.png 
    :alt: A screenshot that shows document properties in the Office outspace. 
.. |ImageProps2| image:: /images/PartnerTestCases_Properties2.png
    :alt: A screenshot that shows the document properties within the opened file. 

-------------------------------------
Open from Company App - First Install
-------------------------------------
 This test verifies the flow of using Office for the first time from the Company app. Repeat for each supported file type and each Office app.

#. Start with a fresh install of Company app. Ensure Office is not installed.
#. Boot up company app and login.
#. **RESULT: General promotion for Office should be shown the first time after upgrading the company app.**
#. Browse to a supported file type
#. **RESULT: "Open with Microsoft [app]" promotion, drawing attention to control and enabling open with Office once per first open for each supported file type. "Open with Microsoft should be top choice if multiple choices are available. If a list is not shown Office should be the default app for opening the file.**
#. Activate control to open in Office
#. **RESULT: User should be sent to app store page for the corresponding app.**
#. Install the Office app.
#. After installation, go back to the Company app and activate the control to open in Office again.
#. **RESULT: Office should start. User should be prompted for credentials to Company Service.**
#. Enter credentials for company service.
#. **RESULT: File should open in read only mode.**
#. Click sign in and sign in with a free Microsoft Account.
#. **RESULT: File should open in edit mode if user is a consumer and read-only mode if the user is a commercial user.**
#. Make changes [you will need to sign in with a subscription account for testing commercial user]
#. Click Back (<-)
#. Click "Open"
#. **RESULT: Confirm company service is shown as a place.**

+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| ID | Title                                    | Test Case                                                                                     | Pass/Fail?     | Notes                             |
+====+==========================================+===============================================================================================+================+===================================+
| 1  |  Open from Company App - first install   | This test verifies the flow of using Office for the first time from the Company app           |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | Repeat for each supported file type and each Office app                                       |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | #. Start with a fresh install of Company app. Ensure Office is not installed.                 |                |                                   |
|    |                                          | #. Boot up company app and login.                                                             |                |                                   |
|    |                                          | #. **RESULT: General promotion for Office should be shown the first time after                |                |                                   |
|    |                                          |    upgrading the company app.**                                                               |                |                                   |
|    |                                          | #. Browse to a supported file type                                                            |                |                                   |
|    |                                          | #. **RESULT: "Open with Microsoft [app]" promotion, drawing attention to control and          |                |                                   |
|    |                                          |    enabling open with Office once per first open for each supported file type. "Open with     |                |                                   |
|    |                                          |    Microsoft should be top choice if multiple choices are available. If a list is not shown   |                |                                   |
|    |                                          |    Office should be the default app for opening the file.**                                   |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | #. Activate control to open in Office                                                         |                |                                   |
|    |                                          | #. **RESULT: User should be sent to app store page for the corresponding app.**               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | #. Install the Office app.                                                                    |                |                                   |
|    |                                          | #. After installation, go back to the Company app and activate the control to open in Office  |                |                                   |
|    |                                          |    again.                                                                                     |                |                                   |
|    |                                          | #. **RESULT: Office should start. User should be prompted for credentials to Company          |                |                                   |
|    |                                          |    Service.**                                                                                 |                |                                   |
|    |                                          | #. Enter credentials for company service.                                                     |                |                                   |
|    |                                          | #. **RESULT: File should open in read only mode.**                                            |                |                                   |
|    |                                          | #. Click sign in and  sign in with a free Microsoft Account.                                  |                |                                   |
|    |                                          | #. **RESULT: File should open in edit mode if user is a consumer and read-only mode if        |                |                                   |
|    |                                          |    the user is a commercial user.**                                                           |                |                                   |
|    |                                          | #. Make changes [you will need to sign in with a subscription account for testing commercial  |                |                                   |
|    |                                          |    user]                                                                                      |                |                                   |
|    |                                          | #. Click Back (<-)                                                                            |                |                                   |
|    |                                          | #. Click "Open"                                                                               |                |                                   |
|    |                                          | #. **RESULT: Confirm company service is shown as a place.**                                   |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 2  | Open from Office - fresh install         | This test verifies the flow of using Company Service for the first time from Office.          |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | #. Launch a fresh install of Office.                                                          |                |                                   |
|    |                                          | #. Go through the First Run Experience.                                                       |                |                                   |
|    |                                          | #. Skip Sign In.                                                                              |                |                                   |
|    |                                          | #. Go to Open -> Add a Place                                                                  |                |                                   |
|    |                                          | #. **RESULT: Company service shows up. Verify the name and icon of your service.**            |                |                                   |
|    |                                          | #. Select your Company Service.                                                               |                |                                   |
|    |                                          | #. Enter credentials.                                                                         |                |                                   |
|    |                                          | #. **RESULT: Root folder should show.**                                                       |                |                                   |
|    |                                          | #. Browse around the folder structure in your service.                                        |                |                                   |
|    |                                          | #. **RESULT: Browse works as expected.**                                                      |                |                                   |
|    |                                          | #. Open a file from Browse.                                                                   |                |                                   |
|    |                                          | #. **RESULT: File should open in read-only mode.**                                            |                |                                   |
|    |                                          | #. Click sign in and  sign in with a free Microsoft Account.                                  |                |                                   |
|    |                                          | #. **RESULT: File should open in edit mode if user is a consumer and read-only mode if        |                |                                   |
|    |                                          |    the user is a commercial user.**                                                           |                |                                   |
|    |                                          | #. Make changes [you will need to sign in with a subscription account for testing commercial  |                |                                   |
|    |                                          |    user]                                                                                      |                |                                   |
|    |                                          | #. Click Back (<-)                                                                            |                |                                   |
|    |                                          | #. Click "Open"                                                                               |                |                                   |
|    |                                          | #. **RESULT: File should have the previously saved changes. Ensure changes are being saved on |                |                                   |
|    |                                          |    Company service.**                                                                         |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 3  | Open from Company App - repeat usage     | Repeat test #1 except with company service already added (i.e. from previous usage).          |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 4  | Open from Office - repeat usage          | Repeat test #2 except with company service already added (i.e. from previous usage).          |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 5  | Save As [duplicate]                      | Verify ability to duplicate to Company Service, both by adding a new place and using an       |                |                                   |
|    |                                          | existing place.                                                                               |                |                                   |
|    |                                          | |duplicate|                                                                                   |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 6  | Create New [name]                        | Verify ability to duplicate to Company Service, both by adding a new place and using an       |                |                                   |
|    |                                          | existing place.                                                                               |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 7  | Verify licensing                         | Verify editing a file for a commercial user requires O365 subscription or else it opens read  |                |                                   |
|    |                                          | only.                                                                                         |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | Please go to Settings -> [Microsoft App] -> Reset Word -> Delete Sign-In Credentials and      |                |                                   |
|    |                                          | restarting Office before doing this test.                                                     |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 8  | OAuth login page                         | Verify there is a link to the company's privacy statement on the company's login page when the|                |                                   |
|    |                                          | user adds the company service as a place.                                                     |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | Verify login page fits in window for various iPad and iPhone sizes.                           |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 9  | Verify file properties                   | Verify file properties from Recent and from opened file. When opening the properties from the |                |                                   |
|    |                                          | Recent tab or the Open tab, the fields Author, Created, Modified By and Company will be empty.|                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | |ImageProps1|                                                                                 |                |                                   |
|    |                                          |  Properties View from Recent                                                                  |                |                                   |
|    |                                          | |ImageProps2|                                                                                 |                |                                   |
|    |                                          |  Properties View from within document                                                         |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 10 | Test changing passwords                  | This test verifies the flow of using Company Service after the user changed passwords.        |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | This test changes based on how the Company Service handles authentication and refresh/access  |                |                                   |
|    |                                          | tokens. If you invalidate the access and refresh token after the user changes password, run   |                |                                   |
|    |                                          | this test. You can adapt this test to ensure the Office app is handling refresh and access    |                |                                   |
|    |                                          | tokens correctly.                                                                             |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | #. Launch a fresh install of Office.                                                          |                |                                   |
|    |                                          | #. Go through the First Run Experience.                                                       |                |                                   |
|    |                                          | #. Skip Sign In.                                                                              |                |                                   |
|    |                                          | #. Go to Open -> Add a Place                                                                  |                |                                   |
|    |                                          | #. Select your Company Service.                                                               |                |                                   |
|    |                                          | #. Enter credentials.                                                                         |                |                                   |
|    |                                          | #. Browse around the folder structure in your service.                                        |                |                                   |
|    |                                          | #. Open a file from Browse.                                                                   |                |                                   |
|    |                                          | #. Click sign in and  sign in with a free Microsoft Account.                                  |                |                                   |
|    |                                          | #. Make changes (you will need to sign in with a subscription account for testing commercial  |                |                                   |
|    |                                          |    user)                                                                                      |                |                                   |
|    |                                          | #. Click Back                                                                                 |                |                                   |
|    |                                          | #. On the Company Service app, change the password of the user.                               |                |                                   |
|    |                                          | #. Open the Office app and browse to the Company Service and Open a file.                     |                |                                   |
|    |                                          | #. **RESULT: You should be prompted to enter credentials again.**                             |                |                                   |
|    |                                          |                                                                                               |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
