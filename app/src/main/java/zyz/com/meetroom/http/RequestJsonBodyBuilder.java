package zyz.com.meetroom.http;

import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestJsonBodyBuilder {
    final MediaType type = MediaType.parse("application/json;charset=utf-8");
    private JsonObject o;
    public RequestJsonBodyBuilder(){
        o = new JsonObject();
    }
    public RequestBody build() {
        return RequestBody.create(type,o.toString());
    }
    public RequestJsonBodyBuilder addParam(String name, Object value) {
        o.addProperty(name,value.toString());
        return this;
    }
}
