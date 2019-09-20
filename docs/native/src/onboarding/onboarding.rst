
..  _onboarding:

Onboarding information
======================

The following information must be supplied to Microsoft to enable end-to-end testing and release of
|Office iOS| integration.

+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| Property       | Data Type   | Sample Value                               | Description                                      |
+================+=============+============================================+==================================================+
| Localized      | String      | Contoso                                    | The name to display for this storage provider    |
| Provider       |             |                                            |                                                  |
| Name           |             |                                            |                                                  |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| Icon           | String[]    | \https://contoso.com/images/logo16.png     | The path to the provider hosted icons, one for   |
| Locations      |             | \https://contoso.com/images/logo32.png     | each of the following dimensions:                |
|                |             | \https://contoso.com/images/logo48.png     |                                                  |
|                |             | \https://contoso.com/images/logo64.png     | * 16x16                                          |
|                |             | \https://contoso.com/images/logo80.png     | * 32x32                                          |
|                |             | \https://contoso.com/images/logo96.png     | * 48x48                                          |
|                |             |                                            | * 64x64                                          |
|                |             |                                            | * 80x80                                          |
|                |             |                                            | * 96x96                                          |
|                |             |                                            |                                                  |
|                |             |                                            | Requirements:                                    |
|                |             |                                            |                                                  |
|                |             |                                            | * must be PNG                                    |
|                |             |                                            | * icons must have at least a 1px white border    |
|                |             |                                            |   to avoid bleed                                 |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| BootstrapperUri| String      | \https://contoso.com/wopibootstrapper      | The full path to the Bootstrapper endpoint. Must |
|                |             |                                            | end in wopibootstrapper.                         |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| English        | String      | Free online storage.                       | Used to show more information about the service. |
| Description    |             | Store, access and share thousands          |                                                  |
|                |             | of documents.                              | Please specify which part of the string should   |
|                |             |                                            | not be localized. E.g. your product or brand name|
|                |             |                                            | if you include it in your description            |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| Client ID      | String      | s6BhdRkqt3                                 | OAuth2 Client ID (for Office)                    |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| Client Secret  | String      | 7FjfpZBr1K3tDRbnfVdmIw98                   | OAuth2 Client Secret (for Office).               |
|                |             |                                            |                                                  |
|                |             |                                            | Please do not provide this in email. This needs  |
|                |             |                                            | to be transferred securely.                      |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| Client App     | String      | Office                                     | Please set this as the app name.                 |
| Name           |             |                                            |                                                  |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| RedirectUri    | String      | \https://localhost                         | The redirect URI used to return an authorization |
|                |             |                                            | code. This URI is used as a known stop-URL and   |
|                |             |                                            | is not loaded by |Office iOS|.                   |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| ProviderId     | String      | TP_CONTOSO                                 | Supplied by Microsoft                            |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| TrustedDomain  | String      | contoso.com, qa-contoso.com                | Known domains for bootstrapper, authorization    |
|                |             |                                            | and token issuance endpoints.                    |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| Scope          | String      | userprofile, editdocs                      | Set of comma-delimited scopes that are to be     |
| (optional)     |             |                                            | requested during authentication with the storage |
|                |             |                                            | provider.                                        |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| SupportRefresh | Bool        | True                                       | Denotes whether you will issue a refresh token   |
|                |             |                                            | that can be used to obtain a new access token.   |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+
| SiteUrl        | String      | http://www.contoso.com                     | Fully qualified address to your website.         |
|                |             |                                            |                                                  |
+----------------+-------------+--------------------------------------------+--------------------------------------------------+

Additional information required
-------------------------------
* 3 test accounts on each environment you want us to onboard.

  * If your service has a commercial vs. consumer offering, please provide 3 of each.

* Website interface of each environment, if one exists.
* Whether each environment can be reached outside your network (if it's Internet accessible so we can use it)
* How many apps do you have on each platform? (E.g. on iOS, do you have a "for enterprise app" vs. a "for consumer app", or do you just have one?)
* If you have already integrated with |wac|, please provide directions to access the validator.

Security Questions
------------------
* What is the expiry for access and refresh token for each environment?
* Do you actually check the redirect URI sent during OAuth2 flow against the one registered above, such that the authorization code would only ever be sent to the redirect URI?

