package kz.astralombard.base.data

/**
 * Created by wokrey@gmail.com on 7/13/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
abstract class BaseRepository {

    protected suspend fun <T : Any> makeApiRequest(call: suspend () -> T): Response<T> = try {
        Response.Success(call.invoke())
    }catch (ex: Exception){
        Response.Error(ex)
    }
}