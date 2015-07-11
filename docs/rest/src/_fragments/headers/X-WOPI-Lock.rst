A **string** value identifying the current lock on the file. This header must always be included when
responding to the request with :http:statuscode:`409`. It should not be included when responding to the
request with :http:statuscode:`200`.
