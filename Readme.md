# READ ME

Following it the relevant information regarding the API framework usage an well as the documentation of the APIs.

Framework relies on the Rest Assure and TestNG libraries imported via Maven.

## API swagger files
[Documentation](https://collaborate.crealogix.com/confluence/display/FOERDERBANK/Overview+open+api+spec+of+the+basis+module)

## Various

### PartnerId

PartnerId can be setup on the Admin Center by searching the user under Finances and editing the contract

### Running tests on different environment
In order to change the environment on which the tests are to be run, only the environment property of the
config.properties file needs to be updated to point to a correct environment file
(e.g. fbk-r3-dev). Additional environment files can be added with data being setup previously.

### Useful commands
response.prettyPrint();

#TO DO
Create task tests
Start with create task api call (try on r4 dev)