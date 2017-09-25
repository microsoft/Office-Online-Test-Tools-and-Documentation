    protected void LaunchIntent()
    {
        Intent authIntent = new Intent();
        authIntent.setComponent(new ComponentName("com.android.thirdparty.packagename", " com.android.thirdparty.packagename.AuthActivityName"));
        authIntent.putExtra(AuthorizeUrlQueryParams, client_id=abcdefg&response_type=code&scope=wopi&rs=enUS&build=16.1.1234&platform =android&app=word);
        authIntent.putExtra(UserId, User123);
        startActivityForResult(authIntent, uniqueRequestCode)
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent result) 
    {
        // Check which request we're responding to
        if (requestCode == uniqueRequestCode) 
        {
             // Make sure the auth request was successful
             if (resultCode == RESULT_OK) 
             {
               //successfully authed
               // Here we will assume that result will contain ResonponseUrlQueryParams
               // which will look like this code=abcdefg&tk=http://contoso.com&sc=xyz 
              }
              else if (resultCode == RESULT_CANCELED)
              {
              //auth request cancel
              // Here we will assume that result will contain ResonponseUrlQueryParams
              // But this time we will not get tk and session context but may get error and error_description.
              }
         }
         else
         {
             //failed
             // Here we will assume that result will contain ResonponseUrlQueryParams
             // But this time we will not get tk and session context but may get error and error_description.
         } 
     }
