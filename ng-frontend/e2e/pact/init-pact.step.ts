import { defineSupportCode } from 'cucumber';
import * as Provider from 'pact';
import * as path from 'path';
import { API_BACKEND_PORT } from '../../src/environments/environment.e2e';

export let apiBackend: Provider.PactProvider;

defineSupportCode(({ AfterAll, BeforeAll, After }) => {

  apiBackend = Provider({
    consumer: 'ng-frontend',
    provider: 'api-gateway',
    spec: 2,
    port: API_BACKEND_PORT,
    cors: true,
    dir: path.resolve(__dirname, '../../pacts')
  });

  BeforeAll((callback) => {
    apiBackend.setup().then(
      () => {
        console.log('apiBackend mock started');
        callback();
      },
      (error) => {
        console.error('apiBackend mock could not be started', error);
        callback();
      });
  });

  AfterAll(() => apiBackend.finalize());

  After(() => apiBackend.verify());

});
