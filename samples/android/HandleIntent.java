    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
         super.onCreate(savedInstanceState); 
         // Get the intent that started this activity
         Intent intent = getIntent();
         // Get the bundle of Extras, it will have data set by caller App
         Bundle extras = intent.getExtras();
    }

    protected void returnResult()
    {
        // create intent to deliver result data
        Intent result = new Intent();
        resut.putExtra("ResonponseUrlQueryParams", "code=abcdefg&tk=http://contoso.com&sc=xyz");
        setResult(Activity.RESULT_OK, result);
        finish();
    }