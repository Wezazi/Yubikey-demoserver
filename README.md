Modification

I wanted a locally hosted version of demo.yubico.com and found this. My fork is only focused on the webauthn-server-demo section of the original repo. My changes allows you to register and log in to the demo page without using a security key pin, only yubikey and touch.

Download and Install Instructions <br>
1.Have Java and a yubikey<br>
2.git clone this repo<br>
3."cd .\java-webauthn-server\webauthn-server-demo"<br>
4."..\gradlew run" (make sure to not run burp suite or anything else on port 8080)<br>
5.Once the terminal says 95% execution, it's finished, just open http://localhost:8080<br>

Example on how to use<br>
1.Username: Zuck<br>
2.Create Account with non-discoverable credential<br>
3.log out<br>
4.Authenticate with username<br>

To Delete account<br>
1.Be logged in<br>
2.Scroll down to the request/response sections and find:<br>
"credential": {<br>
      "id":<br>
}<br>
3.Copy the ID and paste it to the CredentialID field in the form<br>
4.Press Deregister<br>
