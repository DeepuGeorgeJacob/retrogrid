### What is Retrogrid

Retrogrid is a library built on top of retrofit library for the easy intergation of your application.

#### Steps to integrate

1. Download and add `retrogriod-network` library into your root project.
2. Edit your `setting.gradle` file and include `retrogriod-network` module like below.
   `include ':retrogriod-network'`
3. Add library dependency in your application level `build.gradle` file.
```gradle
dependencies {

    implementation project(':retrogriod-network')
}
```
<br>

### Great you are done with integration.

<br/>
### How to use the libary. <br>

There are two ways(property file and annotation) to use this this library. Easy way is by creating annotation

#### By Annotation
1. Create your repository like below

```kotlin
@RetrofitServiceConfiguration(baseUrl = "http://example.com/v1/")
interface RetrogridService {
    @GET(value = "method_url")
    @ErrorResponseMap(errorClass = NewsErrorResponse::class)
    suspend fun fetchNewsOverview():RetrogridResponse<NewsOverview>
}
```

`@RetrofitServiceConfiguration` is the annotation that you can add your base url. <br>
`@ErrorResponseMap` Which you can mention your class which hold error response. Error Response will be different for different project your back end team define the error structure.<br>
Eg:- 
```kotlin
data class NewsErrorResponse(
    private val status: String,
    private val code: String,
    private val message: String
)
```
Make sure your method to get the data is `suspended` function. 
<br><br> Eg:-

```kotlin
suspend fun fetchNewsOverview():RetrogridResponse<NewsOverview>
```
<br>

`RetrogridResponse` is the class which containing your response object or available error objects if available. You should pass your success response class as typed argument. Here `NewsOverview` is my response data class.

Eg:->
```kotlin
data class NewsOverview(
    val status: String,
    val totalResults: Int,
    val articles:List<String>
)
```

2. Create your repository like this.

```kotlin
class NewsRepository(
    private val service: RetrogridService = RetroGridNetworkClient.buildService(RetrogridService::class.java)
) {
    suspend fun getNewsOverView() = service.fetchNewsOverview()
}
```

<br> Congratulations you are done with 90%

3. Call the api from your viewmodel.

```kotlin
class RetrogridViewModel(private val repository: NewsRepository = NewsRepository()) : ViewModel() {

    init {
        viewModelScope.launch {
            when (val result = repository.getNewsOverView()) {
                is RetrogridResponse.Success -> println("SUCCESS ${result.responseBody}")
                is RetrogridResponse.Failure -> {
                    println((result.errorResponseBody as NewsErrorResponse).toString())
                }
                is RetrogridResponse.NetworkError -> println("Network error : "+result.errorMessage)
                is RetrogridResponse.UnknownError -> println("Unknown error : "+result.errorMessage)
            }
        }

    }
}
```

<br>

Notice how you should handle success and error responses.

### You did it!! Contratulations :)

#### Additional configuration (Global base_url using properties file)

If you have to add static base url, Please follow the below steps.
1. You should create a file called `retrofit.properties` in the src/main/resources directory of the app module.
2. In the app module's build.gradle file, add the following lines to make sure the properties file is included in the build:

```gradle
android {
    // ...
    sourceSets {
        main {
            resources {
                srcDirs = ['src/main/resources']
            }
        }
    }
}
```

3. Add the property value with key `api.base.url` in your `retrofit.properties` file. Example:-
`api.base.url=https://example.org/v2/`

4. Now you can use service without `@RetrofitServiceConfiguration` annotation. 

~~@RetrofitServiceConfiguration(baseUrl = "https://example.com/v1/")~~
```kotlin

interface RetrogridService {
    @GET(value = "method_url")
    @ErrorResponseMap(errorClass = NewsErrorResponse::class)
    suspend fun fetchNewsOverview():RetrogridResponse<NewsOverview>
}
```


<br><b>Note : If you configure with both annotation and `retrofit.properties` file, then annotation will override base url value. Which mean if you want to change the base url for any specific services you should use `@RetrofitServiceConfiguration` annotation.


### Happy coding :) Contact the author by this [link](https://www.linkedin.com/in/deepu-george-jacob-76753358/)


