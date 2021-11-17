Demonstrating the bug:

Steps:
* run with `mvn clean quarkus:dev`
* run the below command to see the issue (it takes a moment to timeout)
```bash
curl --header "Content-Type: application/json" \
  --data '{}' \
  http://localhost:8080/test
```
* comment out the lambda handler, the imports for the lambda handler, and the amazon-lambda dependency in the pom
* uncomment the Resource
* run the same command 
```bash
curl --header "Content-Type: application/json" \
        --data '{}' \
        http://localhost:8080/test
```
To see it 'succeed' (won't actually succeed since no oidc details are set up, but demonstrates the issue)
