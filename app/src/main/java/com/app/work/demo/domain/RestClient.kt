import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import com.app.work.demo.MyApplication
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.*
import com.google.gson.internal.bind.TypeAdapters
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import okhttp3.*
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.Serializable
import java.util.concurrent.TimeUnit


object RestClient {
    fun getClient(apiType: APIType = APIType.AUTHCODE, authorization: String = ""): Retrofit {

        val onlineInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
        }

        val offlineInterceptor = Interceptor { chain ->
            var request = chain.request()
            if (!isConnectingToInternet()) {
                val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            chain.proceed(request)
        }
        val cacheSize = 10 * 1024 * 1024 // 10 MB

        val cache = Cache(MyApplication.appContext?.getCacheDir(), cacheSize.toLong())
        val okHttpClient =
            OkHttpClient.Builder() // .addInterceptor(provideHttpLoggingInterceptor()) // For HTTP request & Response data logging
                .addInterceptor(offlineInterceptor)
                .addNetworkInterceptor(onlineInterceptor)
                .cache(cache)
                .build()

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
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://randomuser.me")
            .build()
    }

    enum class APIType {
        AUTHCODE,
        NOAUTH
    }
}

fun returnBodyType(str: String): RequestBody {
    return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str)
}



data class GenReques<T>(
    var liveData: MutableLiveData<T> = MutableLiveData<T>(),
    var errorData: MutableLiveData<String> = MutableLiveData<String>()
) : Serializable

fun <T> JsonObject.getResult(serviceClass: Class<T>): T {
    return myGson().fromJson(this.asJsonObject, serviceClass)
}

data class ErrorData(val statusCode: Number?, val message: String?)

private val UNRELIABLE_INTEGER: TypeAdapter<Number> = object : TypeAdapter<Number>() {
    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Number? {
        val jsonToken = `in`.peek()
        when (jsonToken) {
            TypeAdapters.NUMBER, TypeAdapters.STRING, TypeAdapters.DOUBLE, JsonToken.STRING, JsonToken.NUMBER -> {
                val s = `in`.nextString()
                try {
                    return Integer.parseInt(s)
                } catch (ignored: NumberFormatException) {
                }

                try {
                    return java.lang.Double.parseDouble(s).toInt()
                } catch (ignored: NumberFormatException) {
                }

                return null
            }
            JSONObject.NULL -> {
                `in`.nextNull()
                return null
            }
            TypeAdapters.BOOLEAN -> {
                `in`.nextBoolean()
                return null
            }
            else -> throw JsonSyntaxException("Expecting number, got: $jsonToken")
        }
    }

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Number) {
        out.value(value)
    }
}

private val UNRELIABLE_INTEGER_FACTORY =
    TypeAdapters.newFactory(Int::class.javaPrimitiveType, Int::class.java, UNRELIABLE_INTEGER)

fun myGson(): Gson {
    return GsonBuilder().registerTypeAdapterFactory(UNRELIABLE_INTEGER_FACTORY).create()!!
}
fun isConnectingToInternet(): Boolean {
    val cm = MyApplication.appContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null &&
            activeNetwork.isConnectedOrConnecting
}