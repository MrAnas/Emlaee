package com.app.emlaee.utils;


public class Constants {

    //    ******************** HTTP REQUEST PARAMETERS FOR API ***************************
    public static final String BASE_URL = "http://quiz.freejobsnews.com/quiz_project/webservice/";
    public static final String REGISTRATION_URL = BASE_URL+"register_android.php";
    public static final String LOGIN_URL = BASE_URL+"login.php";
    public static final String CATEGORIES_URL = BASE_URL+"category.php";
    public static final String CATEGORIES_TEST_URL = BASE_URL+"category_test.php"; //category_id
    public static final String TESTS_QUESTIONS_URL = BASE_URL+"test_question.php"; //quiz_id
    public static final String SAVE_RESULT_URL = BASE_URL+"saveResult.php"; //total_question,correct,wrong,not_attempt,userid,testid

    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";

    //    ******************** SHARED PREFERENCES PARAMETERS *****************************
    public static final String SHARED_PREFERENCES_NAME = "ArabicQuiz_Pref";
    public static final String KEY_PRE_USER_EMAIL = "key_user_email";
    public static final String KEY_PRE_USER_ID = "key_user_id";
    public static final String KEY_PRE_USER_NAME = "key_user_name";
    public static final String KEY_TEST_ID = "key_user_name";



    //    ************************* INTENT KEYS ******************************
    public static final String PHONE_NUMBER_INTENT = "phone_number";
    public static final String PLAYER_ID_INTENT = "player_ic";
    public static final String IS_EXIST_PHONE_NUMBER_INTENT = "is_exist_phone_number";

    /******************FRAGMENT CONSTANTS***************************/
    public static final String LEVEL_FRAGMENT = "level_fragment";
    public static final String PERFORMANCE_FRAGMENT = "performance_fragment";
    public static final String CATEGORIES_FRAGMENT = "performance_fragment";
    public static final String SETTINGS_FRAGMENT = "settings_fragment";
    public static final String ABOUTUS_FRAGMENT = "aboutus_fragment";
}
