package com.mobisprint.aurika.helper;

public class RouteName {

    public static final String COORG_PATH = "com.mobisprint.aurika.coorg.";
    public static final String UDAIPUR_PATH = "com.mobisprint.aurika.udaipur.";



    //select loction
    public static final String auirka_coorg = "/aurika-coorg/";
    public static final String aurika_udaipur = "/aurika-udaipur/";


    //Coorg Services
    public static final String guest_minibar = "guest-minibar";
    public static final String guest_sleepwell= "guest-sleepwell";
    public static final String guest_amenities = "guest-amenities";
    public static final String guest_housekeeping_category = "guest-housekeeping-category";
    public static final String guest_directory_service = "guest-directory-service";
    public static final String guest_laundry_category = "guest-laundry-category";
    public static final String other_assistance = "other-assistance";

    //Coorg Home Screen
    public  static final String assa_abloy_door_unlock = "assa-abloy-door-unlock";
    public  static final String services = "services";
    public  static final String pet_friendly = "pet-friendly";
    public  static final String wine_and_dine = "wine-and-dine";
    public  static final String in_room_dining = "in-room-dining";
    public  static final String experiences = "experiences";
    public  static final String spa = "spa";
    public  static final String recreation = "recreation";
    public  static final String sightseeing = "sightseeing";




    //Coorg PetServices


    public static final String guest_k9_amenities = "guest-k9-amenities";
    public static final String guest_k9_facilities = "guest-k9-facilities";
    public static final String guest_k9_menu = "guest-k9-menu";



    //Coorg profile routes

    public static final String myprofile = "myprofile";
    public static final String my_stay = "my-stay";

    public static String getLocationRoutes(String route){
        switch(route){

            case auirka_coorg:
                return COORG_PATH+"activity.UserAuthenticationActivity";
            case aurika_udaipur:
                return UDAIPUR_PATH+"activity.HomeScreenActivity";

            default: return "";

        }
    }

    public static String getCoorgServiceRoutes(String route){
        switch (route){
            case guest_laundry_category:
                return COORG_PATH+"fragments.services.LaundryService";
            case guest_amenities:
                return COORG_PATH+"fragments.services.Amenities";
            case guest_directory_service:
                return  COORG_PATH+"fragments.services.DirectoryOfServicesFragment";
            case guest_housekeeping_category:
                return  COORG_PATH+"fragments.services.HouseKeeping";
            case guest_sleepwell:
                return COORG_PATH+"fragments.services.CoorgSleepWellFragment";
            case guest_minibar:
                return COORG_PATH+"fragments.services.MiniBar";
            case other_assistance:
                return COORG_PATH+"fragments.services.OtherAssistance";
            default:return "";

        }
    }


    public  static String getHomeScreenRoutes(String route){
        switch (route){
            case services:
                return COORG_PATH+"fragments.services.CoorgServices";
            case recreation:
                return COORG_PATH+"fragments.recreationalfacilities.RecreationalFacilities";
            case pet_friendly:
                return COORG_PATH+"fragments.petservices.CoorgPetFriendly";
            case wine_and_dine:
                return COORG_PATH+"fragments.winedine.WineAndDineFragment";
            case sightseeing:
                return COORG_PATH+"fragments.sightseeing.CoorgSightSeeing";
            case spa:
                return COORG_PATH+"fragments.spa.SpaFragment";
            case experiences:
                return COORG_PATH+"fragments.experiences.ExclusiveExperiences";
            case in_room_dining:
                return COORG_PATH+"fragments.dining.CoorgInRoomDiningFragment";
            case myprofile:
                return "";
            default:return "";

        }
    }

    public static String getPetServiceRoutes(String route){
        switch (route){
            case guest_k9_amenities:
                return COORG_PATH+"fragments.petservices.K9Amenities";
            case guest_k9_facilities:
                return COORG_PATH+"fragments.petservices.K9Facilities";
            case guest_k9_menu:
                return COORG_PATH+"fragments.petservices.K9Menu";
            default:return "";
        }
    }


}
