exports.config = {
  allScriptsTimeout: 11000,
  specs: [
    './e2e/features/**/*.feature'
  ],
  capabilities: {
    'browserName': 'chrome',
    'chromeOptions': {
      'args': [ "--headless", "--disable-gpu", "--window-size=800,600" ]
    }
  },
  directConnect: true,
  baseUrl: 'http://localhost:4200/',
  framework: 'custom',
  frameworkPath: require.resolve('protractor-cucumber-framework'),
  cucumberOpts: {
    require: ['./e2e/**/*.ts'],
    tags: [ "~@ignore" ],
    format: ['progress'],
    strict: true,
    dryRun: false,
    compiler: ['ts:ts-node']
  },
  onPrepare: () => {
    browser.manage().window().maximize();
  },
  beforeLaunch: () => {
    require('ts-node').register({
      project: 'e2e/tsconfig.e2e.json'
    });
  }
};
