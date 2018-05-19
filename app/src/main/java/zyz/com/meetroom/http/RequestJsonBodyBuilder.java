package zyz.com.meetroom.http;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestJsonBodyBuilder<T> {
    private final MediaType type = MediaType.parse("application/json;charset=utf-8");
    private JsonObject o;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public RequestJsonBodyBuilder() {
    }

    public RequestBody build() {
        return RequestBody.create(type, o.toString());
    }

    public RequestJsonBodyBuilder addParam(String name, Object value) {
        if (o == null) {
            o = new JsonObject();
        }

        o.addProperty(name, value.toString());
        return this;
    }

    public RequestBody fromPojo(T bean) {
        GsonBuilder builder = new GsonBuilder();

    // 不转换没有 @Expose 注解的字段

//            builder.excludeFieldsWithoutExposeAnnotation();

    //对Date类型进行注册事件

            builder.registerTypeAdapter(Date.class, new UtilDateSerializer());

            Gson gson = builder.create();
        Log.i("json", "fromPojo: "+gson.toJson(bean));
        return RequestBody.create(type, gson.toJson(bean));
    }

    class UtilDateSerializer implements JsonSerializer<Date> {

        @Override

        public JsonElement serialize(Date src, Type typeOfSrc,

                                     JsonSerializationContext context) {

            //拼凑UTC时间类型
            DateTime dateTime = new DateTime(src.getTime());
            dateTime = dateTime.plusHours(8);
            Log.i("json", "serialize: "+src.getTime()+","+dateTime.getMillis());
            return new JsonPrimitive(format.format(new Date(dateTime.getMillis())));
        }

    }
}
