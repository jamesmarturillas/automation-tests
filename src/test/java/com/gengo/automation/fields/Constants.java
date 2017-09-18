package com.gengo.automation.fields;

/**
 * @class Constant values such as URL's, numbers, strings and etc. should be stored here.
 */
public class Constants {
    /**
     * @callable URL's
     */
    public static final String HOMEPAGE_URL = "https://gengo:Gengo2017!@staging.gengo.com/";
    public static final String ADMIN_QUALIFICATIONS_URL = "https://admin.staging.gengo.com/qualifications";
    public static final String ADMIN_SIGN_OUT_URL = "https://admin.staging.gengo.com/logout";

    /**
     *  Qualification settings
     *
     *  @callable Source
     */
    public static final String QUALIFICATION_SRC_EN = "English";

    /**
     * @callable Target
     */
    public static final String QUALIFICATION_TGT_JA = "Japanese";

    /**
     * @callable Rank
     */
    public static final String QUALIFICATION_RNK_STANDARD = "Standard";
    public static final String QUALIFICATION_RNK_PRO = "Pro (Business)";
    public static final String QUALIFICATION_RNK_PROOFREAD = "Proofread (Ultra)";
    public static final String QUALIFICATION_RNK_ST = "Senior Translator";

    // text variations
    public static final String QUALIFICATION_RNK_BUSINESS = "Business";

    /**
     * @callable User Actions
     */
    public static final String USER_ACTION_HIJACK = "hijackUser";
    public static final String USER_ACTION_ADD_PREFERRED = "addPreferred";
    public static final String USER_ACTION_BULK_ADD_PREFERRED = "bulkAddPreferred";
    public static final String USER_ACTION_CREDIT_REWARD_ADJUSTMENT = "creditRewardAdjustment";
    public static final String USER_ACTION_ASSOCIATE_ACCOUNT = "associateAccount";
    public static final String USER_ACTION_SUSPEND_USER = "suspendUser";
    public static final String USER_ACTION_COMMENT = "comment";
    public static final String USER_ACTION_EDIT_CUSTOM_PRICES = "editCustomPrices";
    public static final String USER_ACTION_EXPORT_SPEND = "exportSpend";
    public static final String USER_ACTION_EXPORT_TM_REPORT = "exportTmReport";

    /**
     * @callable Language pair from admin qualification settings.
     */
    public static final String AR_NL = "Arabic > Dutch";
    public static final String AR_EN = "Arabic > English";
    public static final String ZH_EN = "Chinese (Simplified) > English";
    public static final String ZH_EN_GB = "Chinese (Simplified) > English (British)";
    public static final String ZH_JA = "Chinese (Simplified) > Japanese";
    public static final String ZH_TW_EN = "Chinese (Traditional) > English";
    public static final String ZH_TW_JA = "Chinese (Traditional) > Japanese";
    public static final String DA_EN = "Danish > English";
    public static final String DA_EN_GB = "Danish > English (British)";
    public static final String NL_EN = "Dutch > English";
    public static final String NL_EN_GB = "Dutch > English (British)";
    public static final String EN_AR = "English > Arabic";
    public static final String EN_BG = "English > Bulgarian";
    public static final String EN_ZH = "English > Chinese (Simplified)";
    public static final String EN_ZH_TW = "English > Chinese (Traditional)";
    public static final String EN_CS = "English > Czech";
    public static final String EN_DA = "English > Danish";
    public static final String EN_NL = "English > Dutch";
    public static final String EN_EN_GB = "English > English (British)";
    public static final String EN_FI = "English > Finnish";
    public static final String EN_FR = "English > French";
    public static final String EN_FR_CA = "English > French (Canada)";
    public static final String EN_DE = "English > German";
    public static final String EN_EL = "English > Greek";
    public static final String EN_HE = "English > Hebrew";
    public static final String EN_HU = "English > Hungarian";
    public static final String EN_ID = "English > Indonesian";
    public static final String EN_IT = "English > Italian";
    public static final String EN_JA = "English > Japanese";
    public static final String EN_KO = "English > Korean";
    public static final String EN_MS = "English > Malay";
    public static final String EN_NO = "English > Norwegian";
    public static final String EN_PL = "English > Polish";
    public static final String EN_PT_BR = "English > Portuguese (Brazil)";
    public static final String EN_PT = "English > Portuguese (Europe)";
    public static final String EN_RO = "English > Romanian";
    public static final String EN_RU = "English > Russian";
    public static final String EN_SR = "English > Serbian";
    public static final String EN_SK = "English > Slovak";
    public static final String EN_ES_LA = "English > Spanish (Latin America)";
    public static final String EN_ES = "English > Spanish (Spain)";
    public static final String EN_SV = "English > Swedish";
    public static final String EN_TL = "English > Tagalog";
    public static final String EN_TH = "English > Thai";
    public static final String EN_TR = "English > Turkish";
    public static final String EN_UK = "English > Ukrainian";
    public static final String EN_VI = "English > Vietnamese";
    public static final String FR_CA_EN = "French (Canada) > English";
    public static final String FR_AR = "French > Arabic";
    public static final String FR_EN = "French > English";
    public static final String FR_EN_GB = "French > English (British)";
    public static final String FR_DE = "French > German";
    public static final String DE_ZH = "German > Chinese (Simplified)";
    public static final String DE_NL = "German > Dutch";
    public static final String DE_EN = "German > English";
    public static final String DE_EN_GB = "German > English (British)";
    public static final String DE_PL = "German > Polish";
    public static final String DE_RU = "German > Russian";
    public static final String DE_ES = "German > Spanish (Spain)";
    public static final String EL_ZH_HK = "Greek > Chinese (Hong Kong)";
    public static final String EL_DA = "Greek > Danish";
    public static final String ID_EN = "Indonesian > English";
    public static final String IT_EN = "Italian > English";
    public static final String IT_EN_GB = "Italian > English (British)";
    public static final String JA_ZH = "Japanese > Chinese (Simplified)";
    public static final String JA_ZH_TW = "Japanese > Chinese (Traditional)";
    public static final String JA_EN = "Japanese > English";
    public static final String JA_FR = "Japanese > French";
    public static final String JA_ID = "Japanese > Indonesian";
    public static final String JA_KO = "Japanese > Korean";
    public static final String JA_ES = "Japanese > Spanish (Spain)";
    public static final String JA_TH = "Japanese > Thai";
    public static final String JA_VI = "Japanese > Vietnamese";
    public static final String KO_EN = "Korean > English";
    public static final String KO_JA = "Korean > Japanese";
    public static final String NO_EN = "Norwegian > English";
    public static final String NO_EN_GB = "Norwegian > English (British)";
    public static final String PL_EN = "Polish > English";
    public static final String PL_EN_GB = "Polish > English (British)";
    public static final String PL_FR_CA = "Polish > French (Canada)";
    public static final String PL_DE = "Polish > German";
    public static final String PL_TL = "Polish > Tagalog";
    public static final String PT_BR_EN = "Portuguese (Brazil) > English";
    public static final String PT_EN = "Portuguese (Europe) > English";
    public static final String PT_EN_GB = "Portuguese (Europe) > English (British)";
    public static final String RU_EN = "Russian > English";
    public static final String RU_EN_GB = "Russian > English (British)";
    public static final String ES_LA_EN = "Spanish (Latin America) > English";
    public static final String ES_EN = "Spanish (Spain) > English";
    public static final String ES_EN_GB = "Spanish (Spain) > English (British)";
    public static final String ES_JA = "Spanish (Spain) > Japanese";
    public static final String SV_EN = "Swedish > English";
    public static final String SV_EN_GB = "Swedish > English (British)";
    public static final String SV_TL = "Swedish > Tagalog";
    public static final String TH_EN = "Thai > English";

    /**
     * @callable Currency units
     */
    public static final String CURRENCY_USD = "USD";
    public static final String CURRENCY_EUR = "EUR";
    public static final String CURRENCY_JPY = "JPY";
    public static final String CURRENCY_GBP = "GBP";

    /**
     * @callable Unit Prices
     * */

    /* USD (extracted double types only with EN > JA pair) */
    public static final String USD_STD_EN_JA_POINT08 = "0.08";
    public static final String USD_PRO_EN_JA_POINT14 = "0.14";

    /* USD (extracted double types only with EN > EN-GB pair) */
    public static final String USD_STD_EN_ENGB_POINT04 = "0.04";
    public static final String USD_PRO_EN_ENGB_POINT07 = "0.07";

    /* USD (extracted double types only with EN > TL pair) */
    public static final String USD_STD_EN_TL_POINT06 = "0.06";
    public static final String USD_PRO_EN_TL_POINT12 = "0.12";

    /* EUR (extracted double types only with EN > ES pair) */
    public static final String EUR_STD_EN_ES_POINT06 = "0.06";
    public static final String EUR_PRO_EN_ES_POINT12 = "0.12";

    /* EUR (extracted double types only with EN > TH pair) */
    public static final String EUR_STD_EN_TH_POINT06 = "0.06";
    public static final String EUR_PRO_EN_TH_POINT12 = "0.12";

    /* EUR (extracted double types only with EN > ZH pair) */
    public static final String EUR_STD_EN_ZH_POINT08 = "0.08";
    public static final String EUR_PRO_EN_ZH_POINT14 = "0.14";

    /* EUR (extracted double types only with JA > EN pair) */
    public static final String EUR_STD_JA_EN_POINT04 = "0.04";
    public static final String EUR_PRO_JA_EN_POINT08 = "0.08";

    /* EUR (extracted double types only with KO > EN pair) */
    public static final String EUR_STD_KO_EN_POINT04 = "0.04";
    public static final String EUR_PRO_KO_EN_POINT08 = "0.08";

    /* EUR (extracted double types only with ZH > EN pair) */
    public static final String EUR_STD_ZH_EN_POINT04 = "0.04";
    public static final String EUR_PRO_ZH_EN_POINT08 = "0.08";

    /* EUR (extracted double types only with EN > TL pair) */
    public static final String EUR_STD_EN_TL_POINT06 = "0.06";
    public static final String EUR_PRO_EN_TL_POINT12 = "0.12";

    /* EUR (extracted double types only with EN > TL pair) */
    public static final String EUR_STD_EN_ENGB_POINT04 = "0.04";
    public static final String EUR_PRO_EN_ENGB_POINT07 = "0.07";

    /* JPY */
    public static final String JPY5 = "¥5";
    public static final String JPY7 = "¥7";
    public static final String JPY9 = "¥9";
    public static final String JPY12 = "¥12";
    public static final String JPY15 = "¥15";
    public static final String JPY18 = "¥18";
    public static final String JPY17 = "¥17";

    /* GBP */
    public static final String GBP04 = "£0.04";
    public static final String GBP06 = "£0.06";
    public static final String GBP07 = "£0.07";
    public static final String GBP09 = "£0.09";
    public static final String GBP011 = "£0.11";
    public static final String GBP013 = "£0.13";
    public static final String GBP014 = "£0.14";

    /**
     *  @callable Rejection reasons
     */
    public static final String REJECTION_REASON_QUALITY = "Quality";
    public static final String REJECTION_REASON_INCOMPLETE = "Incomplete";
    public static final String REJECTION_REASON_OTHER = "Other";

    /**
     * @callable Job Status
     * */
    public static final String CANCELLED = "Cancelled";
    public static final String AVAILABLE = "Available";

    /**
     * @callable Job Status Filter Options
     * */
    public static final String FILTER_ANY = "Any";
    public static final String FILTER_ADDED = "Added (1)";
    public static final String FILTER_AVAILABLE = "Available (2)";
    public static final String FILTER_INCOMPLETE = "Incomplete (3)";
    public static final String FILTER_REVIEWABLE = "Reviewable (4)";
    public static final String FILTER_APPROVED = "Approved (5)";
    public static final String FILTER_REJECTED_CANCELLED = "Rejected and Cancelled (6)";
    public static final String FILTER_REJECTED_REOPENED = "Rejected and Reopened (7)";
    public static final String FILTER_DECLINED = "Declined (8)";
    public static final String FILTER_CANCELLED = "Cancelled (10)";
    public static final String FILTER_AUTO_APPROVED = "Auto Approved (11)";
    public static final String FILTER_ULTRA_PRO_COMPLETED = "Ultra Pro Completed (12)";
    public static final String FILTER_EXPIRED = "Expired (13)";
    public static final String FILTER_RETURNED = "Returned (14)";
    public static final String FILTER_CORRECTED = "Corrected (15)";
    public static final String FILTER_REVIEWABLE_HOLDING = "Reviewable Holding (16)";
    public static final String FILTER_GROUP_ORDER_CANCELLED = "Grouped Order Cancelled (17)";
    public static final String FILTER_RETURNED_FOR_EDITS = "Returned for Edits (18)";
    public static final String FILTER_HELD_FOR_RESOLUTION = "Held for Resolution (19)";
    public static final String FILTER_AVAILABLE_FOR_PROOFREAD = "Available for Proofread (20)";
    public static final String FILTER_FILE_UPLOAD = "File Uploaded (21)";
    public static final String FILTER_API_AUTO_APPROVED = "API Auto Approved (22)";
    public static final String FILTER_ARCHIVED = "Archived (23)";
    public static final String FILTER_REASSIGNED = "Reassigned (24)";
    public static final String FILTER_EDITED_BY_TRANSLATOR = "Edited by Translator (25)";
    public static final String FILTER_EDITED_BY_SENIOR_TRANSLATOR = "Edited by Senior Translator (26)";
    public static final String FILTER_QUEUEING_FAILED = "Queuing Failed (27)";
    public static final String FILTER_DUPLICATE = "Duplicate (28)";
    public static final String FILTER_HAS_ERRORS = "Has Errors (29)";

    /**
     * @callable Language Abbreviations
     * */
    public static final String LANG_AR = "ar";
    public static final String LANG_BG = "bg";
    public static final String LANG_ZH = "zh";
    public static final String LANG_ZHTW = "zh-tw";
    public static final String LANG_CS = "cs";
    public static final String LANG_DA = "da";
    public static final String LANG_NL = "nl";
    public static final String LANG_ENGB = "en-gb";
    public static final String LANG_FI = "fi";
    public static final String LANG_FR = "fr";
    public static final String LANG_FRCA = "fr-ca";
    public static final String LANG_DE = "de";
    public static final String LANG_EL = "el";
    public static final String LANG_HE = "he";
    public static final String LANG_HU = "hu";
    public static final String LANG_ID = "id";
    public static final String LANG_IT = "it";
    public static final String LANG_JA = "ja";
    public static final String LANG_KO = "ko";
    public static final String LANG_MS = "ms";
    public static final String LANG_NO = "no";
    public static final String LANG_PL = "pl";
    public static final String LANG_PTBR = "pt-br";
    public static final String LANG_PT = "pt";
    public static final String LANG_RU = "ru";
    public static final String LANG_RO = "ro";
    public static final String LANG_SR = "sr";
    public static final String LANG_SK = "sk";
    public static final String LANG_ESLA = "es-la";
    public static final String LANG_ES = "es";
    public static final String LANG_SV = "sv";
    public static final String LANG_TL = "tl";
    public static final String LANG_TH = "th";
    public static final String LANG_TR = "tr";
    public static final String LANG_UK = "uk";
    public static final String LANG_VI = "vi";
    public static final String LANG_EN = "en";

    /**
     * @callable Role
     * */
    public static final String ANY = "Any";
    public static final String CUSTOMER = "Customer";
    public static final String TRANSLATOR = "Translator";
    public static final String CONTRACTOR = "Contractor";
    public static final String EMPLOYEE = "Employee";
    public static final String ADMIN = "Admin";
    public static final String DELETED_USER = "Deleted User";

    /**
     * Homepage locales
     */
    public static final String HP_EN_LOCALE = "English";
    public static final String HP_ZH_LOCALE = "简体中文";
    public static final String HP_JA_LOCALE = "日本語";
    public static final String HP_ES_LOCALE = "Español";

    /**
     * Homepage SUPPORT locales
     */
    public static final String HP_EN_SUPPORT = "SUPPORT";
    public static final String HP_ZH_SUPPORT = "帮助中心";
    public static final String HP_JA_SUPPORT = "サポート";
    public static final String HP_ES_SUPPORT = "Asistencia";

    /**
     * SUPPORT page parts
     */
    public static final String SUPPORT_HEADER = "header";
    public static final String SUPPORT_BODY = "body";
    public static final String SUPPORT_FOOTER = "footer";

    /**
     * SUPPORT links
     */
    public static final String LNK_SUPPORT_EN_LOCALE = "https://support.gengo.com/hc/en-us/categories/204289607";
    public static final String LNK_SUPPORT_ZH_LOCALE = "https://support.gengo.com/hc/en-us/categories/204289607";
    public static final String LNK_SUPPORT_JA_LOCALE = "https://support.gengo.com/hc/ja/categories/204289607";
    public static final String LNK_SUPPORT_ES_LOCALE = "https://support.gengo.com/hc/en-us/categories/204289607";

    /**
     * `WorkbenchFilePage` final Strings.
     */
    public static final String WBFP_REASON_NOT_INTERESTED = "not interested";
    public static final String WBFP_REASON_NO_TIME = "do not have time";
    public static final String WBFP_REASON_SUBJECT_DONT_KNOW = "on a subject I don't know";
    public static final String WBFP_REASON_TOO_SPECIALIST = "too specialist";
    public static final String WBFP_REASON_CONTEXT_INCOMPLETE = "does not have enough context";
    public static final String WBFP_REASON_TOO_DEMANDING = "too demanding";
    public static final String WBFP_REASON_TECHNICAL_PROBLEM = "technical problems";
    public static final String WBFP_REASON_WRONG_LANGUAGE = "wrong language";
}
