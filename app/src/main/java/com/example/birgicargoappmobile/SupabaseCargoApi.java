package com.example.birgicargoappmobile;

import com.example.birgicargoappmobile.model.Cargo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SupabaseCargoApi {
    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Content-Type: application/json"
    })
    @GET("gruz")
    Call<List<Cargo>> getAllCargo();

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Content-Type: application/json"
    })
    @GET("gruz")
    Call<List<Cargo>> getCargoByCustomer(@Query("заказчик_id") String customerIdEq);

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Content-Type: application/json"
    })
    @POST("gruz")
    Call<Void> addCargo(@Body Cargo cargo);

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rZHdsdGRvYXl1aHVpa3p5Y29kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyOTA5NDIsImV4cCI6MjA3ODg2Njk0Mn0.P_h0_6hKlBHYEIGr3smGDza-5tSrvZIxz6yAGh05YaY",
            "Content-Type: application/json"
    })
    @DELETE("gruz")
    Call<Void> deleteCargoById(@Query("id") String idEq);
}
