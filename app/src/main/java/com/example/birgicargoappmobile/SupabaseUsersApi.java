package com.example.birgicargoappmobile;

import com.example.birgicargoappmobile.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.POST;
import java.util.List;

public interface SupabaseUsersApi {

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Content-Type: application/json"
    })
    @GET("users")
    Call<List<User>> getUserByLogin(@Query("login") String loginEq);

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Content-Type: application/json"
    })
    @GET("users")
    Call<List<User>> getUserById(@Query("id") String userId);

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Content-Type: application/json"
    })
    @POST("users")
    Call<Void> createUser(@Body User user);
}
