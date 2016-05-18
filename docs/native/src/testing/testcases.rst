Partner test cases
======================

..  spelling::

    BhdRkqt
    Bool
    editdocs
    FjfpZBr
    https
    localhost
    png
    px
    qa
    tDRbnfVdmIw
    userprofile
    wopibootstrapper

The following test cases must be passing at 100% to continue with Office for iOS integration.

Notes
-------------------------------
* "Company app/application" refers to your app
* "Company service" refers to your service
* Where it refers to "Office", please substitute Word, Excel and PowerPoint app. The tests should be re-run against each Office application.
* Please note the version of the Office app you tested against. This information can be found under Settings -> [App] -> Version

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
|    |                                          | #. Click Back                                                                                 |                |                                   |
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
|    |                                          | #. Click Back                                                                                 |                |                                   |
|    |                                          | #. Click "Open"                                                                               |                |                                   |
|    |                                          | #. **RESULT: File should have the previously saved changes. Ensure changes are being saved on |                |                                   |
|    |                                          |    Company service.**                                                                         |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 3  | Open from Company App - repeat usage     | Repeat test #1 except with company service already added (i.e. from previous usage).          |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+


+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 4  | Open from Office - repeat usage          | Repeat test #2 except with company service already added (i.e. from previous usage).          |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 5  | Save As [duplicate]                      |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 6  | Create New [name]                        |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 7  | Verify licensing                         |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 8  | OAuth login page                         |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 9  | Verify file properties                   |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
| 10 | Test changing passwords                  |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
