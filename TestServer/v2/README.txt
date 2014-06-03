1. generate key pairs
	openssl req -x509 -nodes -newkey rsa:1024 -keyout testkey.pem -out testcert.pem

2. examine key pairs
	openssl rsa -in testkey.pem -text -noout
	openssl x509 -in testcert.pem -text -noout

3. change format
	openssl rsa -in testkey.pem -out testkey.pem

4. start up a test server
	openssl s_server -key testkey.pem -cert testcert.pem -WWW -cipher RC4-SHA -accept 9443
