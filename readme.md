# Coffee shop ordering API

[![Build status](https://badge.buildkite.com/6484318ee252264a9060c4bd0b59d30c2dfd2fd1d55986ae3e.svg)](https://buildkite.com/dius-3/mentoring-coffee-shop-api)

Spring Boot + Kotlin

## Getting Started

Check the [basic design](readme_design.md).

```bash
> cd app
> ./gradlew build                                             # to build and run tests
> java -jar build/libs/dius-mentor_boris_coffee-api-0.1.0.jar # to run the app (with colours!)
> ./gradlew bootRun                                           # to run the app (without colours)
```

## Consumer (pact-driver)

The app is a functional test runner:

```bash
> cd test-consumer
> ./gradlew run
```

The main purpose is to exercise all the provider endpoints, not be an interactive app.

### Contract verification workflow

There are two sides to this, see the [Pact docs](https://docs.pact.io/) for more detailed info.
This is the instructions to run Pact verification locally (without a broker).

- Consumer
    - In `/test-consumer`.
    - Run `./gradlew build` to compile and run tests (unit + Pact).
    - If you want to run just tests again, `./gradlew check` is your buddy.
    - Publish the contract (pactfile) for verification.
        - New (broker) way:
            - Make sure you have the specified `gradle` properties (see below).
            - Run `./gradlew pactPublish`. The end, hooray!
        - Old (local) way:
            - Uncomment the `hasPactsWith` line in the `build.gradle` before `build`.
            - Copy `/test-consumer/build/pacts/*.json` to `/app/build/pacts/`.
            - There's a script to do this now: `publish_local.sh`.
- Provider
    - In `/app`.
    - Run `./gradlew build` to compile and run tests (unit + functional).
    - Run `./gradlew pactVerify --no-daemon` to run Pact tests.
- Integration
    - Start the provider (`./gradlew bootRun` from `/app`). Or the `java` line above.
    - Run the consumer to exercise the real endpoints (`./gradlew run` from `/test-consumer`).

Adding credentials to your `gradle.properties`:

- Please never put credentials within a project directory on your filesystem.
    - Even if they're `.gitignore`d. It's too easy to screw up.
- Open `~/.gradle/gradle.properties`.
- Add these:
    - ```
      pactBrokerUsername=<user>
      pactBrokerPassword=<pass>
      pactBrokerAccount=<acc>
      ```
    - Obviously, hit me up for real credentials~*
