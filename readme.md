# GithubUserFinder in Jetpack Compose
##### The project that implemented for instant searching the github users, implemented in Jetpack Compose

## How it works
![Alt text](screenshots/githubfinder.gif)

## Architecture
- MVVM
#### Tools & libraries/Technologies

- kotlin
- Jetpack Compose
- Navigation Component
- Hilt
- kotlin-Coroutines / Flow
- Junit
- Retrofit
- LeakCanary
- Github Actions

#### Packages
- di: Dependency injection package of the application
- data: Represents Models, Network, Mappers, Repository classes/interfaces
- utils: Utils, helper methods of the application
- ui: View layer of MVVM architecture
#### Key Classes And Functions

- `RepositoryHelper.kt`: Interface to manage behavior of RepositoryImpl class
- `RepositoryImpl.kt`: Implementation of RepositoryHelper interface which is responsible for feeding viewModel with Data
- `MainViewModel.kt`: The Main ViewModel of Application that includes search, user details business logic

    * `fun registerSearchFlow(queryFlow: StateFlow<String>)`: Implementation of Instant Search that Consumes the `StateFlow` and checkes if executing the Search api for a query is Needed.
    - `getQueryNextPage.kt`: Executes when User scrolls down to bottom of the list and attempt to get a next page from API

- `SearchBar.kt`: SearchBar Composables lives here :)
  * `fun SearchBar(
    query: MutableState<String>,
    onQueryChanged: (String) -> Unit)`: The searchbar composable which aligns to top of the screen and notifies others when a search query changes.

- `ExtensionFunctions.kt`: The place that common extension functions live


Cheers!
