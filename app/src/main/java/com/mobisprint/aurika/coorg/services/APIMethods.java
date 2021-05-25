package com.mobisprint.aurika.coorg.services;

import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.dining.Dining;
import com.mobisprint.aurika.coorg.pojo.experiences.Experiences;
import com.mobisprint.aurika.coorg.pojo.home.Home;
import com.mobisprint.aurika.coorg.pojo.location.SelectLocation;
import com.mobisprint.aurika.coorg.pojo.login.Login;
import com.mobisprint.aurika.coorg.pojo.navigation.Navigation;
import com.mobisprint.aurika.coorg.pojo.petservices.PetServices;
import com.mobisprint.aurika.coorg.pojo.recreational.Recreational;
import com.mobisprint.aurika.coorg.pojo.sightseeing.SightSeeing;
import com.mobisprint.aurika.coorg.pojo.spa.Spa;
import com.mobisprint.aurika.coorg.pojo.winedine.WineAndDine;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIMethods {

    //Coorg Login

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/exp-locations/")
     Call<SelectLocation> getLocation();

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @POST("aurika/guest/signup/")
    Call<Login> registration(@Body Map map);

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @POST("aurika/guest/verifyotp/")
    Call<Login> authenticateOtp(@Body Map map);

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @POST("aurika/guest/setmpin/")
    Call<Login> createMpin(@Body Map map);

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @POST("aurika/guest/loginwithmpin/")
    Call<Login> loginWithMpin(@Body Map map);

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @POST("aurika/guest/signin/")
    Call<Login> forgotMpin(@Body Map map);


    //Coorg Services

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-services/")
    Call<CoorgServicesPojo> services();


    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-amenities/")
    Call<CoorgServicesPojo> amenities();


    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-directory-service/")
    Call<CoorgServicesPojo> directoryOfServices();


    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-housekeeping-category/")
    Call<CoorgServicesPojo> houseKeeping();


    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-sleepwell/")
    Call<CoorgServicesPojo> sleepWell();


    /*@Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-minibar/")
    Call<CoorgServicesPojo> miniBar();*/

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-laundry-category/")
    Call<CoorgServicesPojo> laundry();



    // Home Screen

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-dashbord-module-view/")
    Call<Home> homeScreen();



    // Side Navigation bar

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-mobile-routes-category/")
    Call<Navigation> navigationBar();



    //Recretional facilities

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-recreation/")
    Call<Recreational> recreationalFacilities();


    //Pet friendly

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-pet-services/")
    Call<PetServices> petFriendly();



    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-k9-amenities/")
    Call<PetServices> k9Amenities();


    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-k9-facilities/")
    Call<PetServices> k9Facilities();


    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-k9-menu/")
    Call<PetServices> k9Menu();

    //Wine And Dine

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-wine-dine/")
    Call<WineAndDine> wineAndDineMenu();


    //SightSeeing

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-sightseeing/")
    Call<SightSeeing> sightSeeing();


    //Spa Menu

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-spa/")
    Call<Spa> spaMenu();


    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-spa-menu/")
    Call<Spa> spaSubMenu();


    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-spa-menu-list/")
    Call<Spa> spaTherapyList();


    //Exclusive Experiences


    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-special-experience/")
    Call<Experiences> experiencesMenu();


    //In-Room Dining

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-dining-category/")
    Call<Dining> diningMenu();

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-coorg"})
    @GET("aurika/guest/guest-dining-subcategory/")
    Call<Dining> diningMenuList(@Query("category_id") Integer category_id);

}
