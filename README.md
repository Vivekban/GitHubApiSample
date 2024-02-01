# GitHubApiSample with Clean Architecture & Compose

An Android app which communicates with the public Github API in order to display information about a specific user.

## Screenshots

| Mode  | Home                                               | Home - With User                                             | Repo Details                                              |
|-------|----------------------------------------------------|--------------------------------------------------------------|-----------------------------------------------------------|
| Light | <img src="screenshots/light/home.jpg" width="250"> | <img src="screenshots/light/home_with_user.jpg" width="250"> | <img src="screenshots/light/repo_detail.jpg" width="250"> |

## Features

- **User Lookup**: Input a GitHub user's ID to see their avatar and name.
- **Repository List**: Explore the user's public repositories through a scrollable list, showcasing
  repository names and brief descriptions.
- **Detailed Repository View**: Dive deeper into a specific repository by selecting it, revealing
  comprehensive details on a dedicated detail screen.

### Additional Features

- **Pagination**: Seamlessly loads large amounts of data, ensuring a smoother user experience while
  navigating through repositories.
- **Network Monitor**: Stay informed about your internet connection status, with the app providing
  clear feedback on whether it's connected or not.
- **Animated Interaction**: Enjoy a visually engaging experience with animations that present user
  and repository information in an intuitive and lively manner.

## Highlights

- Optimal for Interviews: The ideal sample app for take-home assignments during interviews,
  showcasing industry-relevant practices.
- Industry Standard Architecture: Built on Clean Architecture principles with MVVM and Repository
  pattern, leveraging Jetpack Compose for a modern and efficient UI.
- Reliability Matters: Over 25 meticulously crafted test cases covering Repositories, ViewModels,
  and Composables to ensure robust and reliable performance.
- Optimized Performance: Employed tools like Leak Canary, Strict Mode, Compose Compiler, and Jank
  Stat to fine-tune the app for optimal performance.
- Secure by Design: Prioritized security through sensible permission usage and implementation of
  network configurations.
- Crystal Clear Documentation: Achieving 100% documentation coverage for enhanced readability and
  ease of understanding. Every component and aspect of the app is well-documented, making it
  accessible for contributors and users alike.

## Tech Stack

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android
  development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For concurrency and
  asynchronous tasks
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) -
  A asynchronous data stream that sequentially emits values and completes normally or with an
  exception.
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - A live data
  replacement.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) -
  - [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern toolkit for building
    native UI
  - [Material 3](https://m3.material.io/) - Modern design guide native UI theme.
  - [Paging3](https://kotlinlang.org/) - Load and display small chunks of data at a time.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores
    UI-related data that isn't destroyed on UI changes.
  - [SavedStateHandle](https://developer.android.com/reference/androidx/lifecycle/SavedStateHandle) -
    A handle to saved state passed down to androidx.lifecycle.ViewModel.
  - [Navigation Components](https://developer.android.com/guide/navigation/navigation-getting-started) -
    Navigate to different pages more easily.
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
  - [Hilt](https://dagger.dev/hilt) - An easier way to incorporate Dagger DI into the Android
    application.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Coil](https://coil-kt.github.io/coil/compose/) - An image loading library for Android backed by
  Kotlin Coroutines.
- [Mockk](https://mockk.io/) - For Mocking and Unit Testing

# Architecture Patterns

### 1. Clean Architecture

Clean architecture is a software design pattern that helps to improve the maintainability,
testability, and scalability of Android apps.
It is based on the idea of separating the different concerns of an app into different layers, such
as the presentation layer, the business logic layer, and the data layer. This makes it easier to
make changes to the app without affecting other parts of the codebase.

These days lot of hybrid approach for an app development is coming like KMM, Flutter having clean
architecture in place
let us migrate from one framework to other easily and also simultaneously develop in multiple
frameworks.

### 2. MVVM

The Model-View-ViewModel (MVVM) pattern is a design pattern that separates the user interface (View)
from the underlying data and business logic (Model). It introduces an intermediary component called
the ViewModel, which is responsible for handling the presentation logic and maintaining the state of
the UI.

Benefits of MVVM over MVP

|              | MVVM                                                                                                                             | MVP                                                                                                                                              |
|--------------|----------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| Data Binding | MVVM often provide robust data binding mechanisms, allowing automatic synchronization of data between the ViewModel and the View | Data binding is not inherent in the pattern, and developers often need to write explicit code to update the UI when the underlying data changes. |
| Testability  | The ViewModel, responsible for presentation logic, can be tested independently of the UI                                         | the Presenter is closely tied to the View. Testing the Presenter often involves mocking or creating test doubles for the View interfaces,        |
| Lifecycle    | provide lifecycle awareness, simplifying the management of UI-related data and reducing the risk of memory leaks                 | it may require additional effort to manage the lifecycle effectively                                                                             |

### 3. Repository

The Repository Pattern is a design pattern commonly used in software development to abstract and
centralize data access logic. It provides a clean and organized way to manage the interaction
between the application's business logic and the data sources (such as databases, web services, or
APIs).

- Abstracts and centralizes data access logic, decoupling business logic from storage details.
- Separates data access, making code modular. Changes are confined to the repository, easing
  maintenance.
- Enhances testability by separating concerns. Mocking repositories in tests isolates business
  logic.
- Facilitates easy switching between data sources (e.g., local database to remote API).

### 4. Paging

For pagination, Paging3 library is used which has the following features:

- In-memory caching for your paged data. This helps ensure that your app uses system resources
  efficiently while working with paged data.
- Built-in request deduplication, which helps ensure that your app uses network bandwidth and system
  resources efficiently.
- Support for Kotlin coroutines and flows as well as LiveData and RxJava.
- Built-in support for error handling, including refresh and retry capabilities.

### 5. Feature First Folder Structure

It, is an architectural pattern in Android development where code related to a specific feature is
organized into its own module or package. Here are some benefits of using a feature-first folder
structure in Android:

- Modularity: Promotes modularity by organizing code related to a feature in a separate module or
  package.
- Code Isolation: Minimizes dependencies, isolating feature-specific code.
- Parallel Development: Facilitates concurrent development of different features.
- Clear Ownership: Assigns clear ownership and responsibilities for each feature.
- Independent Testing: Supports focused unit and UI testing of individual features.
- Dynamic Delivery: Enables on-demand delivery and updates of features independently.
- Readability and Maintainability: Improves code readability and maintainability by aligning with
  feature architecture.
- Ease of Onboarding: Simplifies onboarding of new developers with a clear feature structure.

# Performance

### StrictMode

StrictMode is a developer tool that helps you identify and fix violations of good development
practices.

- Network Operations on Main Thread: StrictMode detects network operations (e.g., HTTP requests) on
  the main thread.
- StrictMode detects disk I/O operations (e.g., file read/write) on the main thread.
- Cleartext Network Traffic (HTTP): StrictMode detects cleartext network traffic, which can pose a
  security risk.

### Leak Canary

- LeakCanary is a powerful tool for detecting memory leaks in Android applications. LeakCanary will
  automatically detect and notify you of any memory leaks.
- Find out more details [here](https://square.github.io/leakcanary/)

### Compose compiler metrics

Run the following command to get and analyse compose compiler metrics:

```bash
./gradlew assembleRelease -PcomposeCompilerMetrics=true -PcomposeCompilerReports=true
```

For more details
read [here](https://developer.android.com/jetpack/compose/performance/stability/diagnose)

The reports files will be added to [app/build/compose-reports](app/build/compose_compiler).

For more information on Compose compiler metrics,
see [this blog post](https://medium.com/androiddevelopers/jetpack-compose-stability-explained-79c10db270c8).

# Security

- **Secure Communication**: Used HTTPS for all network communications to encrypt data in transit.
- **Network Security Configuration**: Implemented a network security configuration which restricts
  outgoing network calls, permitting communication exclusively with the github.com domain. This
  ensures a more controlled and secure network environment for the application.
- **Dynamic Base URL**: Elevated security by making the base URL a configurable build parameter.
  This dynamic approach enhances flexibility and allows for secure handling of different
  environments, reducing the risk associated with hardcoding sensitive information.
- **Code Obfuscation**: Utilized code obfuscation tools (e.g., ProGuard) to obfuscate and shrink the
  size of your code. This makes reverse engineering more challenging for potential attackers.

# Testing

Test-Driven Development (TDD): It is a software development approach where tests are written before
the actual code is developed. The process follows a cycle: write a test, run the test (which
initially fails since the code isn't implemented yet), implement the code to pass the test, and then
refactor the code if needed.

This is used for data and domain layer

Feature-Driven Development (FDD): It is an iterative and incremental software development
methodology. It focuses on building features or functionality in short cycles, typically two weeks.
FDD is driven by client-valued features, and the development process is organized around these
features.

This is used for presentation layer

Testing an Android application typically involves a combination of different testing approaches to
ensure the reliability, functionality, and performance of the app.

| Testing Approach          | Objective                                                            | Tools                                   | Status  |
|---------------------------|----------------------------------------------------------------------|-----------------------------------------|---------|
| **Unit Testing**          | Verify individual units (methods/functions) in isolation.            | JUnit, Mockito, Robolectric             | Done    |
| **Integration Testing**   | Validate interaction between different components/modules.           | AndroidJUnitRunner, Espresso            | Partial |
| **Functional Testing**    | Ensure app features work as intended from the user's perspective.    | Espresso, UI Automator                  | Partial |
| **UI Testing**            | Verify correctness of the app's user interface.                      | Espresso, UI Automator                  | Partial |
| **Performance Testing**   | Assess app performance, responsiveness, and resource usage.          | Android Profiler, third-party tools     | Pending |
| **Security Testing**      | Identify and address vulnerabilities that could compromise security. | Static analysis tools, dynamic tools    | Done    |
| **Accessibility Testing** | Ensure app accessibility for users with disabilities.                | Android Accessibility Scanner, TalkBack | Pending |



## Installation Instruction

For development, the latest version of Android Studio is required. The latest version can be
downloaded from [here](https://developer.android.com/studio/).

- Start Android Studio
- Run Application

## Todo

- [ ] Performance
    - [ ] Baseline Profile
    - [x] Enable compose compiler matrix
    - [x] Leak Canary
- [ ] Code Quality
    - [ ] Increase test coverage
    - [ ] Implement Git Hooks
    - [ ] Fix Lint Error
  - [x] Complete Documentation
  - [x] Implement code formatter

## Additional Resources

- [Android App Architecture](https://developer.android.com/topic/architecture)
- [Clean Architecture Uncle Bob](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

## Contribution Guidelines

We appreciate and welcome contributions from the community! To ensure a smooth and collaborative
development process, please follow these guidelines when contributing to the GitHub Explorer app:

1. **Fork the Repository**: Fork the repository to your GitHub account and clone it to your local
   machine.

2. **Create a Branch**: Create a new branch for your feature or bug fix.
   ```bash
   git checkout -b feature/your-feature
3. **Commit Guidelines**: Please follow a clear and concise commit message format. Ensure that your
   commits are atomic and focus on a single task.
4. **Pull Request (PR)**:Open a pull request against the main branch. Provide a detailed description
   of your changes in the PR.
5. **Code Review**: Be open to feedback and make necessary changes based on the code review.
6. **Testing**: If applicable, ensure that your changes include appropriate tests and that existing
   tests pass.
7. **Documentation**: Update the README or any relevant documentation if your changes impact user or
   developer instructions.

Be Respectful: Respect the opinions and efforts of others. Keep discussions constructive and
inclusive.

## Unlicensed

This **GitHubApiSample** app is released under the Unlicense.

```
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or distribute this software, either in source code form or as a compiled binary, for any purpose, commercial or non-commercial, and by any means.

In jurisdictions that recognize copyright laws, the author or authors of this software dedicate any and all copyright interest in the software to the public domain. We make this dedication for the benefit of the public at large and to the detriment of our heirs and successors. We intend this dedication to be an overt act of relinquishment in perpetuity of all present and future rights to this software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org/>
```