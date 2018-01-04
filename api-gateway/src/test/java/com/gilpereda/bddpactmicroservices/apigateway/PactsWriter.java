package com.gilpereda.bddpactmicroservices.apigateway;

import au.com.dius.pact.model.PactSpecVersion;

public interface PactsWriter {

    void writePacts(String pactDirectory, PactSpecVersion pactSpecVersion);

}
