* request get token command example
```
curl -v -X POST -H "Content-Type: application/x-www-form-urlencoded" -H "Accept: application/json" -H "Authorization: Basic aHRtbDU6cGFzc3dvcmQ=" -d 'username=jlong&password=spring&grant_type=password&scope=openid&client_id=html5&client_secret=password' http://localhost:8080/uaa/oauth/token
``` 

* request get resource example
```
curl -H "Authorization: bearer d2f47647-62a3-4921-9836-39f07acdc190" http://som-url.com/foo/bar
```