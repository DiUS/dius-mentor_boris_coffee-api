# Coffee shop ordering API

Spring Boot + Kotlin

## Getting Started

Check the [basic design](https://docs.google.com/a/dius.com.au/document/d/1s9jNOQtfjHfah_io6iCLmjexcaFs4Fr_KsK553N8TZs/edit?usp=sharing).

I have `gradle` installed via [SDK Man](http://sdkman.io/), could add a `gradlew` instead maybe?

```bash
> cd app
> gradle build                                                # to build and run tests
> java -jar build/libs/dius-mentor_boris_coffee-api-0.1.0.jar # to run the app (with colours!)
> gradle bootRun                                              # to run the app (without colours)
```

## Consumer (pact-driver)

The app is a functional test runner:

```bash
> cd test-consumer
> gradle run
```

The main purpose is to exercise all the provider endpoints, not be an interactive app.

### Contract verification workflow

There are two sides to this, see the [Pact docs](https://docs.pact.io/) for more detailed info.
This is the instructions to run Pact verification locally (without a broker).

- Consumer
    - In `/test-consumer`.
    - Run `gradle build` to compile and run tests (unit + Pact).
    - If you want to run just tests again, `gradle check` is your buddy.
    - Publish the contract (pactfile) for verification (copy `/test-consumer/build/pacts/*.json` to `/app/build/pacts/`)
- Provider
    - In `/app`.
    - Run `gradle build` to compile and run tests (unit + functional).
    - Run `gradle pactVerify --no-daemon` to run Pact tests.
- Integration
    - Start the provider (`gradle bootRun` from `/app`).
    - Run the consumer to exercise the real endpoints (`gradle run` from `/test-consumer`).
