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
|    |                                          | #. RESULT: General promotion for Office should be shown  after first upgrade of Company app   |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | #. Browse to a supported file type                                                            |                |                                   |
|    |                                          | #. **RESULT:** "Open with Microsoft [app]" promotion, drawing attention to control and        |                |                                   |
|    |                                          |    enabling open with Office once per first open for each supported file type. "Open with     |                |                                   |
|    |                                          |    Microsoft should be top choice if multiple choices are available. If a list is not shown   |                |                                   |
|    |                                          |    Office should be the default app for opening the file.                                     |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | #. Activate control to open in Office                                                         |                |                                   |
|    |                                          | #. *RESULT:* User should be sent to app store page for the corresponding app.                 |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          | #. Install the Office app.                                                                    |                |                                   |
|    |                                          | #. After installation, go back to the Company app and activate the control to open in Office  |                |                                   |
|    |                                          |    again.                                                                                     |                |                                   |
|    |                                          | #. *RESULT:* Office should start. User should be prompted for credentials to Company          |                |                                   |
|    |                                          |    Service.                                                                                   |                |                                   |
|    |                                          | #. Enter credentials for company service.                                                     |                |                                   |
|    |                                          | #. *RESULT:* File should open in read only mode                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
|    |                                          |                                                                                               |                |                                   |
+----+------------------------------------------+-----------------------------------------------------------------------------------------------+----------------+-----------------------------------+
