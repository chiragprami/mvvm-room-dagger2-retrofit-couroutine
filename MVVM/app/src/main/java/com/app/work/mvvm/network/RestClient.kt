import com.app.work.mvvm.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestClient {
    fun getClient(apiType: APIType = APIType.AUTHCODE, authorization: String = ""): Retrofit {

        val client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            var request = chain.request()
            request = when (apiType) {
                APIType.AUTHCODE -> {
                    request.newBuilder()
                        .build()
                } //  .addHeader(KEY.device, BuildConfig.device)

                APIType.NOAUTH -> {
                    request.newBuilder()
                        .build()
                }
            }
            val response = chain.proceed(request)
            if (response.code() == 401) {
                return@Interceptor response
            }
            response
        })
            .addNetworkInterceptor(StethoInterceptor())
            .connectTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }


enum class APIType {
    AUTHCODE,
    NOAUTH
}
}
