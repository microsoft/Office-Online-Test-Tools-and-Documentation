What are WOPI host preinstall add-ins, what are tips to use them?
==================================================================================================

When a document is opened or created from a WOPI host, the WOPI host can provide a list of Office Web add-ins to
be used with the document. The list of add-ins are provided via as Form field values as part of POST request sent
to |wac| server. In order to enable this feature, WOPI host developers need to register with |wac| server for this 
feature.

Currently, we only support App Command Add-in.

..  tips::

    1. For the Form field name and value format of host_install_addins, here is code snippet:
    
    <body>

    <form id="office_form" name="office_form" target="office_frame" action="<OFFICE_ACTION_URL>" method="post">
        <input name="access_token" value="<ACCESS_TOKEN_VALUE>" type="hidden" />
        <input name="access_token_ttl" value="<ACCESS_TOKEN_TTL_VALUE>" type="hidden" />
        <input name="host_install_addins" value="<JSON_OF_ADD-INS_LIST>" type="hidden" />
    </form>
    ...

    Where <JSON_OF_ADD-INS_LIST> is a list of add-ins reference data in the following JSON string format:
    "{ 
        { addinId: "WA123456781", type: "TaskPaneApp"}, 
        { addinId: "WA123456782", type: "TaskPaneApp"}, 
        { addinId: "WA123456783", type: "TaskPaneApp"}, 
        { addinId: "WA123456784", type: "TaskPaneApp"} 
    }" 

    2. A quick trial with Fiddler, 

    To understand the data flow and quickly see the action of this feature, or to troubleshoot your own 
    implementation, you can use Fiddler to mimic the data payload of add-ins sent by your WOPI host page before you implement it in your page. 

    Here are the steps: 

        1. Pick an App Command Add-in, write down its add-in Id
            For example, browse to https://appsource.microsoft.com/en-us/product/office/WA104380121?tab=Overview, write down WA104380121.
        2. Write down a JSON string like this: [{"addinId":"WA104380121","type":"TaskPaneApp"}]. The addinId is the add-in ID from step 1. 
            The type is always TaskPaneApp for App Command.  
        3. Use html encode tool to escape the string from step 2. Like this: %5B%7B%22addinId%22%3A%22WA104380121%22%2C%22type%22%3A%22TaskPaneApp%22%7D%5D.
        4. In fiddler, add the following section to FiddlerScript, in function OnBeforeRequest. The high-lighted part is the escaped string from step 3.  
        if (oSession.PathAndQuery.StartsWith("/we/wordeditorframe.aspx?"))  
        {  
            oSession.utilSetRequestBody(oSession.GetRequestBodyAsString() + "&host_install_addins=%5B%7B%22addinId%22%3A%22WA104380121%22%2C%22type%22%3A%22TaskPaneApp%22%7D%5D");   

        }  

        To test with Excel Online ( not in FF yet as of 2/23/21), add the following condition:  
        if (oSession.PathAndQuery.StartsWith("/x/_layouts/xlviewerinternal.aspx?"))  

    3. To troubleshoot any data format or encoding issue, you can compare what are sending with the payload that Fiddler mimics above by inspecting them 
    in Fiddler request panel. You can also check the console.log outputs in browser debugger console window. 