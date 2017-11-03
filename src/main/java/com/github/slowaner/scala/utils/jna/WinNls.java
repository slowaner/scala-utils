package com.github.slowaner.scala.utils.jna;

import com.sun.jna.IntegerType;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WTypes;
import com.sun.jna.platform.win32.WinDef;

import java.util.List;

/**
 * Ported from WinNls.h.
 *
 * @author slowaner
 */
public interface WinNls extends WinDef {
    ////////////////////////////////////////////////////////////////////////////
//
//  Constants
//
//  Define all constants for the NLS component here.
//
////////////////////////////////////////////////////////////////////////////

    //
//  String Length Maximums.
//
    int MAX_LEADBYTES = 12;          // 5 ranges, 2 bytes ea., 0 term.
    int MAX_DEFAULTCHAR = 2;           // single or double byte

    //
//  Surrogate pairs
//
//  Conversion examples:
//
//  A) The first character in the Surrogate range (D800, DC00) as UTF-32:
//
//  1.  D800: binary 1101100000000000  (lower ten bits: 0000000000)
//  2.  DC00: binary 1101110000000000  (lower ten bits: 0000000000)
//  3.  Concatenate 0000000000+0000000000 = 0x0000
//  4.  Add 0x10000
//
//  Result: U+10000. This is correct, since the first character in the Supplementary character
//  range immediately follows the last code point in the 16-bit UTF-16 range (U+FFFF)
//
//  B) A UTF-32 code point such as U+2040A (this a CJK character in CJK Extension B), and wish
//  to convert it in UTF-16:
//
//  1.  Subtract 0x10000 - Result: 0x1040A
//  2.  Split into two ten-bit pieces: 0001000001 0000001010
//  3.  Add 1101100000000000 (0xD800) to the high 10 bits piece (0001000001) - Result: 1101100001000001 (0xD841)
//  4.  Add 1101110000000000 (0xDC00) to the low 10 bits piece (0000001010) - Result: 1101110000001010 (0xDC0A)
//
//  RESULT: The surrogate pair: U+D841, U+DC0A
//
//  Special Unicode code point values, for use with UTF-16 surrogate pairs.
//
    int HIGH_SURROGATE_START = 0xd800;
    int HIGH_SURROGATE_END = 0xdbff;
    int LOW_SURROGATE_START = 0xdc00;
    int LOW_SURROGATE_END = 0xdfff;


    //
//  MBCS and Unicode Translation Flags.
//
    int MB_PRECOMPOSED = 0x00000001;  // use precomposed chars
    int MB_COMPOSITE = 0x00000002;  // use composite chars
    int MB_USEGLYPHCHARS = 0x00000004;  // use glyph chars, not ctrl chars
    int MB_ERR_INVALID_CHARS = 0x00000008;  // error for invalid chars

    int WC_COMPOSITECHECK = 0x00000200;  // convert composite to precomposed
    int WC_DISCARDNS = 0x00000010;  // discard non-spacing chars
    int WC_SEPCHARS = 0x00000020;  // generate separate chars
    int WC_DEFAULTCHAR = 0x00000040;  // replace w/ default char
    //#if (WINVER >= 0x0600)
    int WC_ERR_INVALID_CHARS = 0x00000080;  // error for invalid chars
    //#endif

    //#if(WINVER >= 0x0500)
    int WC_NO_BEST_FIT_CHARS = 0x00000400;  // do not use best fit chars
    //#endif /* WINVER >= 0x0500 */


    //
//  Character Type Flags.
//
    int CT_CTYPE1 = 0x00000001;  // ctype 1 information
    int CT_CTYPE2 = 0x00000002;  // ctype 2 information
    int CT_CTYPE3 = 0x00000004;  // ctype 3 information

    //
//  CType 1 Flag Bits.
//
    int C1_UPPER = 0x0001;      // upper case
    int C1_LOWER = 0x0002;      // lower case
    int C1_DIGIT = 0x0004;      // decimal digits
    int C1_SPACE = 0x0008;      // spacing characters
    int C1_PUNCT = 0x0010;      // punctuation characters
    int C1_CNTRL = 0x0020;      // control characters
    int C1_BLANK = 0x0040;      // blank characters
    int C1_XDIGIT = 0x0080;      // other digits
    int C1_ALPHA = 0x0100;      // any linguistic character
    int C1_DEFINED = 0x0200;      // defined character

    //
//  CType 2 Flag Bits.
//
    int C2_LEFTTORIGHT = 0x0001;      // left to right
    int C2_RIGHTTOLEFT = 0x0002;      // right to left

    int C2_EUROPENUMBER = 0x0003;      // European number, digit
    int C2_EUROPESEPARATOR = 0x0004;      // European numeric separator
    int C2_EUROPETERMINATOR = 0x0005;      // European numeric terminator
    int C2_ARABICNUMBER = 0x0006;      // Arabic number
    int C2_COMMONSEPARATOR = 0x0007;      // common numeric separator

    int C2_BLOCKSEPARATOR = 0x0008;      // block separator
    int C2_SEGMENTSEPARATOR = 0x0009;      // segment separator
    int C2_WHITESPACE = 0x000A;      // white space
    int C2_OTHERNEUTRAL = 0x000B;      // other neutrals

    int C2_NOTAPPLICABLE = 0x0000;      // no implicit directionality

    //
//  CType 3 Flag Bits.
//
    int C3_NONSPACING = 0x0001;      // nonspacing character
    int C3_DIACRITIC = 0x0002;      // diacritic mark
    int C3_VOWELMARK = 0x0004;      // vowel mark
    int C3_SYMBOL = 0x0008;      // symbols

    int C3_KATAKANA = 0x0010;      // katakana character
    int C3_HIRAGANA = 0x0020;      // hiragana character
    int C3_HALFWIDTH = 0x0040;      // half width character
    int C3_FULLWIDTH = 0x0080;      // full width character
    int C3_IDEOGRAPH = 0x0100;      // ideographic character
    int C3_KASHIDA = 0x0200;      // Arabic kashida character
    int C3_LEXICAL = 0x0400;      // lexical character
    int C3_HIGHSURROGATE = 0x0800;      // high surrogate code unit
    int C3_LOWSURROGATE = 0x1000;      // low surrogate code unit

    int C3_ALPHA = 0x8000;      // any linguistic char (C1_ALPHA)

    int C3_NOTAPPLICABLE = 0x0000;      // ctype 3 is not applicable


    //
//  String Flags.
//
    int NORM_IGNORECASE = 0x00000001;  // ignore case
    int NORM_IGNORENONSPACE = 0x00000002;  // ignore nonspacing chars
    int NORM_IGNORESYMBOLS = 0x00000004;  // ignore symbols

    int LINGUISTIC_IGNORECASE = 0x00000010;  // linguistically appropriate 'ignore case'
    int LINGUISTIC_IGNOREDIACRITIC = 0x00000020;  // linguistically appropriate 'ignore nonspace'

    int NORM_IGNOREKANATYPE = 0x00010000;  // ignore kanatype
    int NORM_IGNOREWIDTH = 0x00020000;  // ignore width
    int NORM_LINGUISTIC_CASING = 0x08000000;  // use linguistic rules for casing


    //
//  Locale Independent Mapping Flags.
//
    int MAP_FOLDCZONE = 0x00000010;  // fold compatibility zone chars
    int MAP_PRECOMPOSED = 0x00000020;  // convert to precomposed chars
    int MAP_COMPOSITE = 0x00000040;  // convert to composite chars
    int MAP_FOLDDIGITS = 0x00000080;  // all digits to ASCII 0-9

    //#if(WINVER >= 0x0500)
    int MAP_EXPAND_LIGATURES = 0x00002000;  // expand all ligatures
    //#endif /* WINVER >= 0x0500 */

    //
//  Locale Dependent Mapping Flags.
//
    int LCMAP_LOWERCASE = 0x00000100;  // lower case letters
    int LCMAP_UPPERCASE = 0x00000200;  // UPPER CASE LETTERS
    //#if (WINVER >= _WIN32_WINNT_WIN7)
    int LCMAP_TITLECASE = 0x00000300;  // Title Case Letters
    //#endif // (WINVER >= _WIN32_WINNT_WIN7)

    int LCMAP_SORTKEY = 0x00000400;  // WC sort key (normalize)
    int LCMAP_BYTEREV = 0x00000800;  // byte reversal

    int LCMAP_HIRAGANA = 0x00100000;  // map katakana to hiragana
    int LCMAP_KATAKANA = 0x00200000;  // map hiragana to katakana
    int LCMAP_HALFWIDTH = 0x00400000;  // map double byte to single byte
    int LCMAP_FULLWIDTH = 0x00800000;  // map single byte to double byte

    int LCMAP_LINGUISTIC_CASING = 0x01000000;  // use linguistic rules for casing

    int LCMAP_SIMPLIFIED_CHINESE = 0x02000000;  // map traditional chinese to simplified chinese
    int LCMAP_TRADITIONAL_CHINESE = 0x04000000;  // map simplified chinese to traditional chinese


    //#if (WINVER >= _WIN32_WINNT_WIN8)
    int LCMAP_SORTHANDLE = 0x20000000;
    int LCMAP_HASH = 0x00040000;
    //#endif // (WINVER >= _WIN32_WINNT_WIN7)

    //
//  Search Flags
//
    int FIND_STARTSWITH = 0x00100000;  // see if value is at the beginning of source
    int FIND_ENDSWITH = 0x00200000;  // see if value is at the end of source
    int FIND_FROMSTART = 0x00400000;  // look for value in source, starting at the beginning
    int FIND_FROMEND = 0x00800000;  // look for value in source, starting at the end


    //
//  Language Group Enumeration Flags.
//
    int LGRPID_INSTALLED = 0x00000001;  // installed language group ids
    int LGRPID_SUPPORTED = 0x00000002;  // supported language group ids


    //
//  Locale Enumeration Flags.
//
    int LCID_INSTALLED = 0x00000001;  // installed locale ids
    int LCID_SUPPORTED = 0x00000002;  // supported locale ids
    int LCID_ALTERNATE_SORTS = 0x00000004;  // alternate sort locale ids


    //#if (WINVER >= _WIN32_WINNT_VISTA)
//
//  Named based enumeration flags.
//
    int LOCALE_ALL = 0;                     // enumerate all named based locales
    int LOCALE_WINDOWS = 0x00000001;            // shipped locales and/or replacements for them
    int LOCALE_SUPPLEMENTAL = 0x00000002;            // supplemental locales only
    int LOCALE_ALTERNATE_SORTS = 0x00000004;            // alternate sort locales
    int LOCALE_REPLACEMENT = 0x00000008;            // locales that replace shipped locales (callback flag only)
    //#endif // (WINVER >= _WIN32_WINNT_VISTA)
//#if (WINVER >= _WIN32_WINNT_WIN7)
    int LOCALE_NEUTRALDATA = 0x00000010;            // Locales that are "neutral" (language only, region data is default)
    int LOCALE_SPECIFICDATA = 0x00000020;            // Locales that contain language and region data
    //#endif // (WINVER >= _WIN32_WINNT_WIN7)


    //
//  Code Page Enumeration Flags.
//
    int CP_INSTALLED = 0x00000001;  // installed code page ids
    int CP_SUPPORTED = 0x00000002;  // supported code page ids


    //
//  Sorting Flags.
//
//    WORD Sort:    culturally correct sort
//                  hyphen and apostrophe are special cased
//                  example: "coop" and "co-op" will sort together in a list
//
//                        co_op     <-------  underscore (symbol)
//                        coat
//                        comb
//                        coop
//                        co-op     <-------  hyphen (punctuation)
//                        cork
//                        went
//                        were
//                        we're     <-------  apostrophe (punctuation)
//
//
//    STRING Sort:  hyphen and apostrophe will sort with all other symbols
//
//                        co-op     <-------  hyphen (punctuation)
//                        co_op     <-------  underscore (symbol)
//                        coat
//                        comb
//                        coop
//                        cork
//                        we're     <-------  apostrophe (punctuation)
//                        went
//                        were
//
    int SORT_STRINGSORT = 0x00001000;  // use string sort method

    //  Sort digits as numbers (ie: 2 comes before 10)
    //#if (WINVER >= _WIN32_WINNT_WIN7)
    int SORT_DIGITSASNUMBERS = 0x00000008;  // use digits as numbers sort method
    //#endif // (WINVER >= _WIN32_WINNT_WIN7)


    //
//  Compare String Return Values.
//
    int CSTR_LESS_THAN = 1;           // string 1 less than string 2
    int CSTR_EQUAL = 2;           // string 1 equal to string 2
    int CSTR_GREATER_THAN = 3;           // string 1 greater than string 2


    //
//  Code Page Default Values.
//
    int CP_ACP = 0;           // default to ANSI code page
    int CP_OEMCP = 1;           // default to OEM  code page
    int CP_MACCP = 2;           // default to MAC  code page
    int CP_THREAD_ACP = 3;           // current thread's ANSI code page
    int CP_SYMBOL = 42;          // SYMBOL translations

    int CP_UTF7 = 65000;       // UTF-7 translation
    int CP_UTF8 = 65001;       // UTF-8 translation

    //
//  Country/Region Codes.
//
    int CTRY_DEFAULT = 0;

    int CTRY_ALBANIA = 355;         // Albania
    int CTRY_ALGERIA = 213;         // Algeria
    int CTRY_ARGENTINA = 54;         // Argentina
    int CTRY_ARMENIA = 374;         // Armenia
    int CTRY_AUSTRALIA = 61;         // Australia
    int CTRY_AUSTRIA = 43;         // Austria
    int CTRY_AZERBAIJAN = 994;         // Azerbaijan
    int CTRY_BAHRAIN = 973;         // Bahrain
    int CTRY_BELARUS = 375;         // Belarus
    int CTRY_BELGIUM = 32;         // Belgium
    int CTRY_BELIZE = 501;         // Belize
    int CTRY_BOLIVIA = 591;         // Bolivia
    int CTRY_BRAZIL = 55;         // Brazil
    int CTRY_BRUNEI_DARUSSALAM = 673;         // Brunei Darussalam
    int CTRY_BULGARIA = 359;         // Bulgaria
    int CTRY_CANADA = 2;         // Canada
    int CTRY_CARIBBEAN = 1;         // Caribbean
    int CTRY_CHILE = 56;         // Chile
    int CTRY_COLOMBIA = 57;         // Colombia
    int CTRY_COSTA_RICA = 506;         // Costa Rica
    int CTRY_CROATIA = 385;         // Croatia
    int CTRY_CZECH = 420;         // Czech Republic
    int CTRY_DENMARK = 45;         // Denmark
    int CTRY_DOMINICAN_REPUBLIC = 1;         // Dominican Republic
    int CTRY_ECUADOR = 593;         // Ecuador
    int CTRY_EGYPT = 20;         // Egypt
    int CTRY_EL_SALVADOR = 503;         // El Salvador
    int CTRY_ESTONIA = 372;         // Estonia
    int CTRY_FAEROE_ISLANDS = 298;         // Faeroe Islands
    int CTRY_FINLAND = 358;         // Finland
    int CTRY_FRANCE = 33;         // France
    int CTRY_GEORGIA = 995;         // Georgia
    int CTRY_GERMANY = 49;         // Germany
    int CTRY_GREECE = 30;         // Greece
    int CTRY_GUATEMALA = 502;         // Guatemala
    int CTRY_HONDURAS = 504;         // Honduras
    int CTRY_HONG_KONG = 852;         // Hong Kong S.A.R., P.R.C.
    int CTRY_HUNGARY = 36;         // Hungary
    int CTRY_ICELAND = 354;         // Iceland
    int CTRY_INDIA = 91;         // India
    int CTRY_INDONESIA = 62;         // Indonesia
    int CTRY_IRAN = 981;         // Iran
    int CTRY_IRAQ = 964;         // Iraq
    int CTRY_IRELAND = 353;         // Ireland
    int CTRY_ISRAEL = 972;         // Israel
    int CTRY_ITALY = 39;         // Italy
    int CTRY_JAMAICA = 1;         // Jamaica
    int CTRY_JAPAN = 81;         // Japan
    int CTRY_JORDAN = 962;         // Jordan
    int CTRY_KAZAKSTAN = 7;         // Kazakstan
    int CTRY_KENYA = 254;         // Kenya
    int CTRY_KUWAIT = 965;         // Kuwait
    int CTRY_KYRGYZSTAN = 996;         // Kyrgyzstan
    int CTRY_LATVIA = 371;         // Latvia
    int CTRY_LEBANON = 961;         // Lebanon
    int CTRY_LIBYA = 218;         // Libya
    int CTRY_LIECHTENSTEIN = 41;         // Liechtenstein
    int CTRY_LITHUANIA = 370;         // Lithuania
    int CTRY_LUXEMBOURG = 352;         // Luxembourg
    int CTRY_MACAU = 853;         // Macao SAR, PRC
    int CTRY_MACEDONIA = 389;         // Former Yugoslav Republic of Macedonia
    int CTRY_MALAYSIA = 60;         // Malaysia
    int CTRY_MALDIVES = 960;         // Maldives
    int CTRY_MEXICO = 52;         // Mexico
    int CTRY_MONACO = 33;         // Principality of Monaco
    int CTRY_MONGOLIA = 976;         // Mongolia
    int CTRY_MOROCCO = 212;         // Morocco
    int CTRY_NETHERLANDS = 31;         // Netherlands
    int CTRY_NEW_ZEALAND = 64;         // New Zealand
    int CTRY_NICARAGUA = 505;         // Nicaragua
    int CTRY_NORWAY = 47;         // Norway
    int CTRY_OMAN = 968;         // Oman
    int CTRY_PAKISTAN = 92;         // Islamic Republic of Pakistan
    int CTRY_PANAMA = 507;         // Panama
    int CTRY_PARAGUAY = 595;         // Paraguay
    int CTRY_PERU = 51;         // Peru
    int CTRY_PHILIPPINES = 63;         // Republic of the Philippines
    int CTRY_POLAND = 48;         // Poland
    int CTRY_PORTUGAL = 351;         // Portugal
    int CTRY_PRCHINA = 86;         // People's Republic of China
    int CTRY_PUERTO_RICO = 1;         // Puerto Rico
    int CTRY_QATAR = 974;         // Qatar
    int CTRY_ROMANIA = 40;         // Romania
    int CTRY_RUSSIA = 7;         // Russia
    int CTRY_SAUDI_ARABIA = 966;         // Saudi Arabia
    int CTRY_SERBIA = 381;         // Serbia
    int CTRY_SINGAPORE = 65;         // Singapore
    int CTRY_SLOVAK = 421;         // Slovak Republic
    int CTRY_SLOVENIA = 386;         // Slovenia
    int CTRY_SOUTH_AFRICA = 27;         // South Africa
    int CTRY_SOUTH_KOREA = 82;         // Korea
    int CTRY_SPAIN = 34;         // Spain
    int CTRY_SWEDEN = 46;         // Sweden
    int CTRY_SWITZERLAND = 41;         // Switzerland
    int CTRY_SYRIA = 963;         // Syria
    int CTRY_TAIWAN = 886;         // Taiwan
    int CTRY_TATARSTAN = 7;         // Tatarstan
    int CTRY_THAILAND = 66;         // Thailand
    int CTRY_TRINIDAD_Y_TOBAGO = 1;         // Trinidad y Tobago
    int CTRY_TUNISIA = 216;         // Tunisia
    int CTRY_TURKEY = 90;         // Turkey
    int CTRY_UAE = 971;         // U.A.E.
    int CTRY_UKRAINE = 380;         // Ukraine
    int CTRY_UNITED_KINGDOM = 44;         // United Kingdom
    int CTRY_UNITED_STATES = 1;         // United States
    int CTRY_URUGUAY = 598;         // Uruguay
    int CTRY_UZBEKISTAN = 7;         // Uzbekistan
    int CTRY_VENEZUELA = 58;         // Venezuela
    int CTRY_VIET_NAM = 84;         // Viet Nam
    int CTRY_YEMEN = 967;         // Yemen
    int CTRY_ZIMBABWE = 263;         // Zimbabwe


//
//  Locale Types.
//
//  These types are used for the GetLocaleInfo NLS API routine.
//  Some of these types are also used for the SetLocaleInfo NLS API routine.
//

    //
//  The following LCTypes may be used in combination with any other LCTypes.
//
//    LOCALE_NOUSEROVERRIDE is also used in GetTimeFormat and
//    GetDateFormat.
//
//    LOCALE_USE_CP_ACP is used in many of the A (Ansi) apis that need
//    to do string translation.
//
//    LOCALE_RETURN_NUMBER will return the result from GetLocaleInfo as a
//    number instead of a string.  This flag is only valid for the LCTypes
//    beginning with LOCALE_I.
//
    int LOCALE_NOUSEROVERRIDE = 0x80000000;   // do not use user overrides
    int LOCALE_USE_CP_ACP = 0x40000000;   // use the system ACP

    //#if(WINVER >= 0x0400)
    int LOCALE_RETURN_NUMBER = 0x20000000;   // return number instead of string
    //#endif /* WINVER >= 0x0400 */

    //#if (WINVER >= _WIN32_WINNT_WIN7)
    int LOCALE_RETURN_GENITIVE_NAMES = 0x10000000;   //Flag to return the Genitive forms of month names
    int LOCALE_ALLOW_NEUTRAL_NAMES = 0x08000000;   //Flag to allow returning neutral names/lcids for name conversion
    //#endif //(WINVER >= _WIN32_WINNT_WIN7)


//
//  The following LCTypes are mutually exclusive in that they may NOT
//  be used in combination with each other.
//

    //
// These are the various forms of the name of the locale:
//
    int LOCALE_SLOCALIZEDDISPLAYNAME = 0x00000002;   // localized name of locale, eg "German (Germany)" in UI language
    //#if (WINVER >= _WIN32_WINNT_WIN7)
    int LOCALE_SENGLISHDISPLAYNAME = 0x00000072;   // Display name (language + country/region usually) in English, eg "German (Germany)"
    int LOCALE_SNATIVEDISPLAYNAME = 0x00000073;   // Display name in native locale language, eg "Deutsch (Deutschland)
    //#endif //(WINVER >= _WIN32_WINNT_WIN7)

    //#if (WINVER >= _WIN32_WINNT_VISTA)
    int LOCALE_SLOCALIZEDLANGUAGENAME = 0x0000006f;   // Language Display Name for a language, eg "German" in UI language
    //#endif //(WINVER >= _WIN32_WINNT_VISTA)
    int LOCALE_SENGLISHLANGUAGENAME = 0x00001001;   // English name of language, eg "German"
    int LOCALE_SNATIVELANGUAGENAME = 0x00000004;   // native name of language, eg "Deutsch"

    int LOCALE_SLOCALIZEDCOUNTRYNAME = 0x00000006;   // localized name of country/region, eg "Germany" in UI language
    int LOCALE_SENGLISHCOUNTRYNAME = 0x00001002;   // English name of country/region, eg "Germany"
    int LOCALE_SNATIVECOUNTRYNAME = 0x00000008;   // native name of country/region, eg "Deutschland"

    //
// Legacy labels for the locale name values
//
    int LOCALE_SLANGUAGE = 0x00000002;   // localized name of locale, eg "German (Germany)" in UI language
    //#if (WINVER >= _WIN32_WINNT_VISTA)
    int LOCALE_SLANGDISPLAYNAME = 0x0000006f;   // Language Display Name for a language, eg "German" in UI language
    //#endif //(WINVER >= _WIN32_WINNT_VISTA)
    int LOCALE_SENGLANGUAGE = 0x00001001;   // English name of language, eg "German"
    int LOCALE_SNATIVELANGNAME = 0x00000004;   // native name of language, eg "Deutsch"
    int LOCALE_SCOUNTRY = 0x00000006;   // localized name of country/region, eg "Germany" in UI language
    int LOCALE_SENGCOUNTRY = 0x00001002;   // English name of country/region, eg "Germany"
    int LOCALE_SNATIVECTRYNAME = 0x00000008;   // native name of country/region, eg "Deutschland"

    // Additional LCTypes
    int LOCALE_ILANGUAGE = 0x00000001;   // language id, LOCALE_SNAME preferred

    int LOCALE_SABBREVLANGNAME = 0x00000003;   // arbitrary abbreviated language name, LOCALE_SISO639LANGNAME preferred

    int LOCALE_IDIALINGCODE = 0x00000005;   // country/region dialing code, example: en-US and en-CA return 1.
    int LOCALE_ICOUNTRY = 0x00000005;   // Deprecated synonym for LOCALE_IDIALINGCODE, use LOCALE_SISO3166CTRYNAME to query for a region identifier.
    int LOCALE_SABBREVCTRYNAME = 0x00000007;   // arbitrary abbreviated country/region name, LOCALE_SISO3166CTRYNAME preferred
    int LOCALE_IGEOID = 0x0000005B;   // geographical location id, eg "244"

    int LOCALE_IDEFAULTLANGUAGE = 0x00000009;   // default language id, deprecated
    int LOCALE_IDEFAULTCOUNTRY = 0x0000000A;   // default country/region code, deprecated
    int LOCALE_IDEFAULTCODEPAGE = 0x0000000B;   // default oem code page (use of Unicode is recommended instead)
    int LOCALE_IDEFAULTANSICODEPAGE = 0x00001004;   // default ansi code page (use of Unicode is recommended instead)
    int LOCALE_IDEFAULTMACCODEPAGE = 0x00001011;   // default mac code page (use of Unicode is recommended instead)

    int LOCALE_SLIST = 0x0000000C;   // list item separator, eg "," for "1,2,3,4"
    int LOCALE_IMEASURE = 0x0000000D;   // 0 = metric, 1 = US measurement system

    int LOCALE_SDECIMAL = 0x0000000E;   // decimal separator, eg "." for 1,234.00
    int LOCALE_STHOUSAND = 0x0000000F;   // thousand separator, eg "," for 1,234.00
    int LOCALE_SGROUPING = 0x00000010;   // digit grouping, eg "3;0" for 1,000,000
    int LOCALE_IDIGITS = 0x00000011;   // number of fractional digits eg 2 for 1.00
    int LOCALE_ILZERO = 0x00000012;   // leading zeros for decimal, 0 for .97, 1 for 0.97
    int LOCALE_INEGNUMBER = 0x00001010;   // negative number mode, 0-4, see documentation
    int LOCALE_SNATIVEDIGITS = 0x00000013;   // native digits for 0-9, eg "0123456789"

    int LOCALE_SCURRENCY = 0x00000014;   // local monetary symbol, eg "$"
    int LOCALE_SINTLSYMBOL = 0x00000015;   // intl monetary symbol, eg "USD"
    int LOCALE_SMONDECIMALSEP = 0x00000016;   // monetary decimal separator, eg "." for $1,234.00
    int LOCALE_SMONTHOUSANDSEP = 0x00000017;   // monetary thousand separator, eg "," for $1,234.00
    int LOCALE_SMONGROUPING = 0x00000018;   // monetary grouping, eg "3;0" for $1,000,000.00
    int LOCALE_ICURRDIGITS = 0x00000019;   // # local monetary digits, eg 2 for $1.00
    int LOCALE_IINTLCURRDIGITS = 0x0000001A;   // # intl monetary digits, eg 2 for $1.00
    int LOCALE_ICURRENCY = 0x0000001B;   // positive currency mode, 0-3, see documenation
    int LOCALE_INEGCURR = 0x0000001C;   // negative currency mode, 0-15, see documentation

    int LOCALE_SDATE = 0x0000001D;   // date separator (derived from LOCALE_SSHORTDATE, use that instead)
    int LOCALE_STIME = 0x0000001E;   // time separator (derived from LOCALE_STIMEFORMAT, use that instead)
    int LOCALE_SSHORTDATE = 0x0000001F;   // short date format string, eg "MM/dd/yyyy"
    int LOCALE_SLONGDATE = 0x00000020;   // long date format string, eg "dddd, MMMM dd, yyyy"
    int LOCALE_STIMEFORMAT = 0x00001003;   // time format string, eg "HH:mm:ss"
    int LOCALE_IDATE = 0x00000021;   // short date format ordering (derived from LOCALE_SSHORTDATE, use that instead)
    int LOCALE_ILDATE = 0x00000022;   // long date format ordering (derived from LOCALE_SLONGDATE, use that instead)
    int LOCALE_ITIME = 0x00000023;   // time format specifier (derived from LOCALE_STIMEFORMAT, use that instead)
    int LOCALE_ITIMEMARKPOSN = 0x00001005;   // time marker position (derived from LOCALE_STIMEFORMAT, use that instead)
    int LOCALE_ICENTURY = 0x00000024;   // century format specifier (short date, LOCALE_SSHORTDATE is preferred)
    int LOCALE_ITLZERO = 0x00000025;   // leading zeros in time field (derived from LOCALE_STIMEFORMAT, use that instead)
    int LOCALE_IDAYLZERO = 0x00000026;   // leading zeros in day field (short date, LOCALE_SSHORTDATE is preferred)
    int LOCALE_IMONLZERO = 0x00000027;   // leading zeros in month field (short date, LOCALE_SSHORTDATE is preferred)
    int LOCALE_SAM = 0x00000028;   // AM designator, eg "AM"
    int LOCALE_S1159 = LOCALE_SAM;
    int LOCALE_SPM = 0x00000029;   // PM designator, eg "PM"
    int LOCALE_S2359 = LOCALE_SPM;

    int LOCALE_ICALENDARTYPE = 0x00001009;   // type of calendar specifier, eg CAL_GREGORIAN
    int LOCALE_IOPTIONALCALENDAR = 0x0000100B;   // additional calendar types specifier, eg CAL_GREGORIAN_US
    int LOCALE_IFIRSTDAYOFWEEK = 0x0000100C;   // first day of week specifier, 0-6, 0=Monday, 6=Sunday
    int LOCALE_IFIRSTWEEKOFYEAR = 0x0000100D;   // first week of year specifier, 0-2, see documentation

    int LOCALE_SDAYNAME1 = 0x0000002A;   // long name for Monday
    int LOCALE_SDAYNAME2 = 0x0000002B;   // long name for Tuesday
    int LOCALE_SDAYNAME3 = 0x0000002C;   // long name for Wednesday
    int LOCALE_SDAYNAME4 = 0x0000002D;   // long name for Thursday
    int LOCALE_SDAYNAME5 = 0x0000002E;   // long name for Friday
    int LOCALE_SDAYNAME6 = 0x0000002F;   // long name for Saturday
    int LOCALE_SDAYNAME7 = 0x00000030;   // long name for Sunday
    int LOCALE_SABBREVDAYNAME1 = 0x00000031;   // abbreviated name for Monday
    int LOCALE_SABBREVDAYNAME2 = 0x00000032;   // abbreviated name for Tuesday
    int LOCALE_SABBREVDAYNAME3 = 0x00000033;   // abbreviated name for Wednesday
    int LOCALE_SABBREVDAYNAME4 = 0x00000034;   // abbreviated name for Thursday
    int LOCALE_SABBREVDAYNAME5 = 0x00000035;   // abbreviated name for Friday
    int LOCALE_SABBREVDAYNAME6 = 0x00000036;   // abbreviated name for Saturday
    int LOCALE_SABBREVDAYNAME7 = 0x00000037;   // abbreviated name for Sunday
    int LOCALE_SMONTHNAME1 = 0x00000038;   // long name for January
    int LOCALE_SMONTHNAME2 = 0x00000039;   // long name for February
    int LOCALE_SMONTHNAME3 = 0x0000003A;   // long name for March
    int LOCALE_SMONTHNAME4 = 0x0000003B;   // long name for April
    int LOCALE_SMONTHNAME5 = 0x0000003C;   // long name for May
    int LOCALE_SMONTHNAME6 = 0x0000003D;   // long name for June
    int LOCALE_SMONTHNAME7 = 0x0000003E;   // long name for July
    int LOCALE_SMONTHNAME8 = 0x0000003F;   // long name for August
    int LOCALE_SMONTHNAME9 = 0x00000040;   // long name for September
    int LOCALE_SMONTHNAME10 = 0x00000041;   // long name for October
    int LOCALE_SMONTHNAME11 = 0x00000042;   // long name for November
    int LOCALE_SMONTHNAME12 = 0x00000043;   // long name for December
    int LOCALE_SMONTHNAME13 = 0x0000100E;   // long name for 13th month (if exists)
    int LOCALE_SABBREVMONTHNAME1 = 0x00000044;   // abbreviated name for January
    int LOCALE_SABBREVMONTHNAME2 = 0x00000045;   // abbreviated name for February
    int LOCALE_SABBREVMONTHNAME3 = 0x00000046;   // abbreviated name for March
    int LOCALE_SABBREVMONTHNAME4 = 0x00000047;   // abbreviated name for April
    int LOCALE_SABBREVMONTHNAME5 = 0x00000048;   // abbreviated name for May
    int LOCALE_SABBREVMONTHNAME6 = 0x00000049;   // abbreviated name for June
    int LOCALE_SABBREVMONTHNAME7 = 0x0000004A;   // abbreviated name for July
    int LOCALE_SABBREVMONTHNAME8 = 0x0000004B;   // abbreviated name for August
    int LOCALE_SABBREVMONTHNAME9 = 0x0000004C;   // abbreviated name for September
    int LOCALE_SABBREVMONTHNAME10 = 0x0000004D;   // abbreviated name for October
    int LOCALE_SABBREVMONTHNAME11 = 0x0000004E;   // abbreviated name for November
    int LOCALE_SABBREVMONTHNAME12 = 0x0000004F;   // abbreviated name for December
    int LOCALE_SABBREVMONTHNAME13 = 0x0000100F;   // abbreviated name for 13th month (if exists)

    int LOCALE_SPOSITIVESIGN = 0x00000050;   // positive sign, eg ""
    int LOCALE_SNEGATIVESIGN = 0x00000051;   // negative sign, eg "-"
    int LOCALE_IPOSSIGNPOSN = 0x00000052;   // positive sign position (derived from INEGCURR)
    int LOCALE_INEGSIGNPOSN = 0x00000053;   // negative sign position (derived from INEGCURR)
    int LOCALE_IPOSSYMPRECEDES = 0x00000054;   // mon sym precedes pos amt (derived from ICURRENCY)
    int LOCALE_IPOSSEPBYSPACE = 0x00000055;   // mon sym sep by space from pos amt (derived from ICURRENCY)
    int LOCALE_INEGSYMPRECEDES = 0x00000056;   // mon sym precedes neg amt (derived from INEGCURR)
    int LOCALE_INEGSEPBYSPACE = 0x00000057;   // mon sym sep by space from neg amt (derived from INEGCURR)

    //#if(WINVER >= 0x0400)
    int LOCALE_FONTSIGNATURE = 0x00000058;   // font signature
    int LOCALE_SISO639LANGNAME = 0x00000059;   // ISO abbreviated language name, eg "en"
    int LOCALE_SISO3166CTRYNAME = 0x0000005A;   // ISO abbreviated country/region name, eg "US"
    //#endif /* WINVER >= 0x0400 */

    //#if(WINVER >= 0x0500)
    int LOCALE_IDEFAULTEBCDICCODEPAGE = 0x00001012;   // default ebcdic code page (use of Unicode is recommended instead)
    int LOCALE_IPAPERSIZE = 0x0000100A;   // 1 = letter, 5 = legal, 8 = a3, 9 = a4
    int LOCALE_SENGCURRNAME = 0x00001007;   // english name of currency, eg "Euro"
    int LOCALE_SNATIVECURRNAME = 0x00001008;   // native name of currency, eg "euro"
    int LOCALE_SYEARMONTH = 0x00001006;   // year month format string, eg "MM/yyyy"
    int LOCALE_SSORTNAME = 0x00001013;   // sort name, usually "", eg "Dictionary" in UI Language
    int LOCALE_IDIGITSUBSTITUTION = 0x00001014;   // 0 = context, 1 = none, 2 = national

    //#endif /* WINVER >= 0x0500 */

    //#if (WINVER >= 0x0600)
    int LOCALE_SNAME = 0x0000005c;   // locale name (ie: en-us)
    int LOCALE_SDURATION = 0x0000005d;   // time duration format, eg "hh:mm:ss"
    int LOCALE_SKEYBOARDSTOINSTALL = 0x0000005e;   // Used internally, see GetKeyboardLayoutName() function
    int LOCALE_SSHORTESTDAYNAME1 = 0x00000060;   // Shortest day name for Monday
    int LOCALE_SSHORTESTDAYNAME2 = 0x00000061;   // Shortest day name for Tuesday
    int LOCALE_SSHORTESTDAYNAME3 = 0x00000062;   // Shortest day name for Wednesday
    int LOCALE_SSHORTESTDAYNAME4 = 0x00000063;   // Shortest day name for Thursday
    int LOCALE_SSHORTESTDAYNAME5 = 0x00000064;   // Shortest day name for Friday
    int LOCALE_SSHORTESTDAYNAME6 = 0x00000065;   // Shortest day name for Saturday
    int LOCALE_SSHORTESTDAYNAME7 = 0x00000066;   // Shortest day name for Sunday
    int LOCALE_SISO639LANGNAME2 = 0x00000067;   // 3 character ISO abbreviated language name, eg "eng"
    int LOCALE_SISO3166CTRYNAME2 = 0x00000068;   // 3 character ISO country/region name, eg "USA"
    int LOCALE_SNAN = 0x00000069;   // Not a Number, eg "NaN"
    int LOCALE_SPOSINFINITY = 0x0000006a;   // + Infinity, eg "infinity"
    int LOCALE_SNEGINFINITY = 0x0000006b;   // - Infinity, eg "-infinity"
    int LOCALE_SSCRIPTS = 0x0000006c;   // Typical scripts in the locale: ; delimited script codes, eg "Latn;"
    int LOCALE_SPARENT = 0x0000006d;   // Fallback name for resources, eg "en" for "en-US"
    int LOCALE_SCONSOLEFALLBACKNAME = 0x0000006e;   // Fallback name for within the console for Unicode Only locales, eg "en" for bn-IN
    //#endif //(WINVER >= 0x0600)

    //#if (WINVER >= _WIN32_WINNT_WIN7)
    int LOCALE_IREADINGLAYOUT = 0x00000070;   // Returns one of the following 4 reading layout values:
    // 0 - Left to right (eg en-US)
    // 1 - Right to left (eg arabic locales)
    // 2 - Vertical top to bottom with columns to the left and also left to right (ja-JP locales)
    // 3 - Vertical top to bottom with columns proceeding to the right
    int LOCALE_INEUTRAL = 0x00000071;   // Returns 0 for specific cultures, 1 for neutral cultures.
    int LOCALE_INEGATIVEPERCENT = 0x00000074;   // Returns 0-11 for the negative percent format
    int LOCALE_IPOSITIVEPERCENT = 0x00000075;   // Returns 0-3 for the positive percent formatIPOSITIVEPERCENT
    int LOCALE_SPERCENT = 0x00000076;   // Returns the percent symbol
    int LOCALE_SPERMILLE = 0x00000077;   // Returns the permille (U+2030) symbol
    int LOCALE_SMONTHDAY = 0x00000078;   // Returns the preferred month/day format
    int LOCALE_SSHORTTIME = 0x00000079;   // Returns the preferred short time format (ie: no seconds, just h:mm)
    int LOCALE_SOPENTYPELANGUAGETAG = 0x0000007a;   // Open type language tag, eg: "latn" or "dflt"
    int LOCALE_SSORTLOCALE = 0x0000007b;   // Name of locale to use for sorting/collation/casing behavior.
    //#endif //(WINVER >= _WIN32_WINNT_WIN7)

    //#if (WINVER >= _WIN32_WINNT_WIN8)
    int LOCALE_SRELATIVELONGDATE = 0x0000007c;   // Long date without year, day of week, month, date, eg: for lock screen
    //#endif

    //#if (WINVER >= _WIN32_WINNT_WIN10)
    int LOCALE_SSHORTESTAM = 0x0000007e;   // Shortest AM designator, eg "A"
    int LOCALE_SSHORTESTPM = 0x0000007f;   // Shortest PM designator, eg "P"
    //#endif

    //
//  Time Flags for GetTimeFormat.
//
    int TIME_NOMINUTESORSECONDS = 0x00000001;  // do not use minutes or seconds
    int TIME_NOSECONDS = 0x00000002;  // do not use seconds
    int TIME_NOTIMEMARKER = 0x00000004;  // do not use time marker
    int TIME_FORCE24HOURFORMAT = 0x00000008;  // always use 24 hour format


    //
//  Date Flags for GetDateFormat.
//
    int DATE_SHORTDATE = 0x00000001;  // use short date picture
    int DATE_LONGDATE = 0x00000002;  // use long date picture
    int DATE_USE_ALT_CALENDAR = 0x00000004;  // use alternate calendar (if any)

    //#if(WINVER >= 0x0500)
    int DATE_YEARMONTH = 0x00000008;  // use year month picture
    int DATE_LTRREADING = 0x00000010;  // add marks for left to right reading order layout
    int DATE_RTLREADING = 0x00000020;  // add marks for right to left reading order layout
    //#endif /* WINVER >= 0x0500 */

    //#if (WINVER >= _WIN32_WINNT_WIN7)
    int DATE_AUTOLAYOUT = 0x00000040;  // add appropriate marks for left-to-right or right-to-left reading order layout
    //#endif //(WINVER >= _WIN32_WINNT_WIN7)

    //#if (WINVER >= _WIN32_WINNT_WINTHRESHOLD)
    int DATE_MONTHDAY = 0x00000080;  // include month day pictures
    //#endif //(WINVER >= _WIN32_WINNT_WINTHRESHOLD)

//
//  Calendar Types.
//
//  These types are used for the EnumCalendarInfo and GetCalendarInfo
//  NLS API routines.
//  Some of these types are also used for the SetCalendarInfo NLS API
//  routine.
//

    //
//  The following CalTypes may be used in combination with any other CalTypes.
//
//    CAL_NOUSEROVERRIDE
//
//    CAL_USE_CP_ACP is used in the A (Ansi) apis that need to do string
//    translation.
//
//    CAL_RETURN_NUMBER will return the result from GetCalendarInfo as a
//    number instead of a string.  This flag is only valid for the CalTypes
//    beginning with CAL_I.
//
//#if(WINVER >= 0x0500)
    int CAL_NOUSEROVERRIDE = LOCALE_NOUSEROVERRIDE;         // do not use user overrides
    int CAL_USE_CP_ACP = LOCALE_USE_CP_ACP;             // use the system ACP
    int CAL_RETURN_NUMBER = LOCALE_RETURN_NUMBER;          // return number instead of string
//#endif /* WINVER >= 0x0500 */

    //#if (WINVER >= _WIN32_WINNT_WIN7)
    int CAL_RETURN_GENITIVE_NAMES = LOCALE_RETURN_GENITIVE_NAMES;  // return genitive forms of month names
//#endif // winver >= windows 7

    //
//  The following CalTypes are mutually exclusive in that they may NOT
//  be used in combination with each other.
//
    int CAL_ICALINTVALUE = 0x00000001;  // calendar type
    int CAL_SCALNAME = 0x00000002;  // native name of calendar
    int CAL_IYEAROFFSETRANGE = 0x00000003;  // starting years of eras
    int CAL_SERASTRING = 0x00000004;  // era name for IYearOffsetRanges, eg A.D.
    int CAL_SSHORTDATE = 0x00000005;  // short date format string
    int CAL_SLONGDATE = 0x00000006;  // long date format string
    int CAL_SDAYNAME1 = 0x00000007;  // native name for Monday
    int CAL_SDAYNAME2 = 0x00000008;  // native name for Tuesday
    int CAL_SDAYNAME3 = 0x00000009;  // native name for Wednesday
    int CAL_SDAYNAME4 = 0x0000000a;  // native name for Thursday
    int CAL_SDAYNAME5 = 0x0000000b;  // native name for Friday
    int CAL_SDAYNAME6 = 0x0000000c;  // native name for Saturday
    int CAL_SDAYNAME7 = 0x0000000d;  // native name for Sunday
    int CAL_SABBREVDAYNAME1 = 0x0000000e;  // abbreviated name for Mon
    int CAL_SABBREVDAYNAME2 = 0x0000000f;  // abbreviated name for Tue
    int CAL_SABBREVDAYNAME3 = 0x00000010;  // abbreviated name for Wed
    int CAL_SABBREVDAYNAME4 = 0x00000011;  // abbreviated name for Thu
    int CAL_SABBREVDAYNAME5 = 0x00000012;  // abbreviated name for Fri
    int CAL_SABBREVDAYNAME6 = 0x00000013;  // abbreviated name for Sat
    int CAL_SABBREVDAYNAME7 = 0x00000014;  // abbreviated name for Sun
    // Note that in the hebrew calendar the leap month name is always returned as the 7th month
    int CAL_SMONTHNAME1 = 0x00000015;  // native name for January
    int CAL_SMONTHNAME2 = 0x00000016;  // native name for February
    int CAL_SMONTHNAME3 = 0x00000017;  // native name for March
    int CAL_SMONTHNAME4 = 0x00000018;  // native name for April
    int CAL_SMONTHNAME5 = 0x00000019;  // native name for May
    int CAL_SMONTHNAME6 = 0x0000001a;  // native name for June
    int CAL_SMONTHNAME7 = 0x0000001b;  // native name for July
    int CAL_SMONTHNAME8 = 0x0000001c;  // native name for August
    int CAL_SMONTHNAME9 = 0x0000001d;  // native name for September
    int CAL_SMONTHNAME10 = 0x0000001e;  // native name for October
    int CAL_SMONTHNAME11 = 0x0000001f;  // native name for November
    int CAL_SMONTHNAME12 = 0x00000020;  // native name for December
    int CAL_SMONTHNAME13 = 0x00000021;  // native name for 13th month (if any)
    int CAL_SABBREVMONTHNAME1 = 0x00000022;  // abbreviated name for Jan
    int CAL_SABBREVMONTHNAME2 = 0x00000023;  // abbreviated name for Feb
    int CAL_SABBREVMONTHNAME3 = 0x00000024;  // abbreviated name for Mar
    int CAL_SABBREVMONTHNAME4 = 0x00000025;  // abbreviated name for Apr
    int CAL_SABBREVMONTHNAME5 = 0x00000026;  // abbreviated name for May
    int CAL_SABBREVMONTHNAME6 = 0x00000027;  // abbreviated name for Jun
    int CAL_SABBREVMONTHNAME7 = 0x00000028;  // abbreviated name for July
    int CAL_SABBREVMONTHNAME8 = 0x00000029;  // abbreviated name for Aug
    int CAL_SABBREVMONTHNAME9 = 0x0000002a;  // abbreviated name for Sep
    int CAL_SABBREVMONTHNAME10 = 0x0000002b;  // abbreviated name for Oct
    int CAL_SABBREVMONTHNAME11 = 0x0000002c;  // abbreviated name for Nov
    int CAL_SABBREVMONTHNAME12 = 0x0000002d;  // abbreviated name for Dec
    int CAL_SABBREVMONTHNAME13 = 0x0000002e;  // abbreviated name for 13th month (if any)

    //#if(WINVER >= 0x0500)
    int CAL_SYEARMONTH = 0x0000002f;  // year month format string
    int CAL_ITWODIGITYEARMAX = 0x00000030;  // two digit year max
    //#endif /* WINVER >= 0x0500 */

    //#if (WINVER >= 0x0600)
    int CAL_SSHORTESTDAYNAME1 = 0x00000031;  // Shortest day name for Mo
    int CAL_SSHORTESTDAYNAME2 = 0x00000032;  // Shortest day name for Tu
    int CAL_SSHORTESTDAYNAME3 = 0x00000033;  // Shortest day name for We
    int CAL_SSHORTESTDAYNAME4 = 0x00000034;  // Shortest day name for Th
    int CAL_SSHORTESTDAYNAME5 = 0x00000035;  // Shortest day name for Fr
    int CAL_SSHORTESTDAYNAME6 = 0x00000036;  // Shortest day name for Sa
    int CAL_SSHORTESTDAYNAME7 = 0x00000037;  // Shortest day name for Su
    //#endif //(WINVER >= 0x0600)

    //#if (WINVER >= _WIN32_WINNT_WIN7)
    int CAL_SMONTHDAY = 0x00000038;  // Month/day format
    int CAL_SABBREVERASTRING = 0x00000039;  // Abbreviated era string (eg: AD)
    //#endif // winver >= windows 7

    //#if (WINVER >= _WIN32_WINNT_WIN8)
    int CAL_SRELATIVELONGDATE = 0x0000003a;   // Long date without year, day of week, month, date, eg: for lock screen
    //#endif

    //#if (NTDDI_VERSION >= NTDDI_WIN10_RS2)
    int CAL_SENGLISHERANAME = 0x0000003b;   // Japanese calendar only: return the English era names for .Net compatibility
    int CAL_SENGLISHABBREVERANAME = 0x0000003c;   // Japanese calendar only: return the English Abbreviated era names for .Net compatibility
    //#endif

    //
//  Calendar Enumeration Value.
//
    int ENUM_ALL_CALENDARS = 0xffffffff;  // enumerate all calendars

    //
//  Calendar ID Values.
//
    int CAL_GREGORIAN = 1;      // Gregorian (localized) calendar
    int CAL_GREGORIAN_US = 2;      // Gregorian (U.S.) calendar
    int CAL_JAPAN = 3;      // Japanese Emperor Era calendar
    int CAL_TAIWAN = 4;      // Taiwan calendar
    int CAL_KOREA = 5;      // Korean Tangun Era calendar
    int CAL_HIJRI = 6;      // Hijri (Arabic Lunar) calendar
    int CAL_THAI = 7;      // Thai calendar
    int CAL_HEBREW = 8;      // Hebrew (Lunar) calendar
    int CAL_GREGORIAN_ME_FRENCH = 9;      // Gregorian Middle East French calendar
    int CAL_GREGORIAN_ARABIC = 10;     // Gregorian Arabic calendar
    int CAL_GREGORIAN_XLIT_ENGLISH = 11;     // Gregorian Transliterated English calendar
    int CAL_GREGORIAN_XLIT_FRENCH = 12;     // Gregorian Transliterated French calendar
    int CAL_PERSIAN = 22;     // Persian (Solar Hijri) calendar
    int CAL_UMALQURA = 23;     // UmAlQura Hijri (Arabic Lunar) calendar

    //
//  Language Group ID Values (Deprecated)
//
    int LGRPID_WESTERN_EUROPE = 0x0001;   // Western Europe & U.S.
    int LGRPID_CENTRAL_EUROPE = 0x0002;   // Central Europe
    int LGRPID_BALTIC = 0x0003;   // Baltic
    int LGRPID_GREEK = 0x0004;   // Greek
    int LGRPID_CYRILLIC = 0x0005;   // Cyrillic
    int LGRPID_TURKIC = 0x0006;   // Turkic
    int LGRPID_TURKISH = 0x0006;   // Turkish
    int LGRPID_JAPANESE = 0x0007;   // Japanese
    int LGRPID_KOREAN = 0x0008;   // Korean
    int LGRPID_TRADITIONAL_CHINESE = 0x0009;   // Traditional Chinese
    int LGRPID_SIMPLIFIED_CHINESE = 0x000a;   // Simplified Chinese
    int LGRPID_THAI = 0x000b;   // Thai
    int LGRPID_HEBREW = 0x000c;   // Hebrew
    int LGRPID_ARABIC = 0x000d;   // Arabic
    int LGRPID_VIETNAMESE = 0x000e;   // Vietnamese
    int LGRPID_INDIC = 0x000f;   // Indic
    int LGRPID_GEORGIAN = 0x0010;   // Georgian
    int LGRPID_ARMENIAN = 0x0011;   // Armenian


    //#if (WINVER >= 0x0600)
//
//  MUI function flag values
//
    int MUI_LANGUAGE_ID = 0x4;      // Use traditional language ID convention
    int MUI_LANGUAGE_NAME = 0x8;      // Use ISO language (culture) name convention
    int MUI_MERGE_SYSTEM_FALLBACK = 0x10;     // GetThreadPreferredUILanguages merges in parent and base languages
    int MUI_MERGE_USER_FALLBACK = 0x20;     // GetThreadPreferredUILanguages merges in user preferred languages
    int MUI_UI_FALLBACK = MUI_MERGE_SYSTEM_FALLBACK | MUI_MERGE_USER_FALLBACK;
    int MUI_THREAD_LANGUAGES = 0x40;     // GetThreadPreferredUILanguages merges in user preferred languages
    int MUI_CONSOLE_FILTER = 0x100;    // SetThreadPreferredUILanguages takes on console specific behavior
    int MUI_COMPLEX_SCRIPT_FILTER = 0x200;    // SetThreadPreferredUILanguages takes on complex script specific behavior
    int MUI_RESET_FILTERS = 0x001;    // Reset MUI_CONSOLE_FILTER and MUI_COMPLEX_SCRIPT_FILTER
    int MUI_USER_PREFERRED_UI_LANGUAGES = 0x10;     // GetFileMUIPath returns the MUI files for the languages in the fallback list
    int MUI_USE_INSTALLED_LANGUAGES = 0x20;     // GetFileMUIPath returns all the MUI files installed in the machine
    int MUI_USE_SEARCH_ALL_LANGUAGES = 0x40;     // GetFileMUIPath returns all the MUI files irrespective of whether language is installed
    int MUI_LANG_NEUTRAL_PE_FILE = 0x100;    // GetFileMUIPath returns target file with .mui extension
    int MUI_NON_LANG_NEUTRAL_FILE = 0x200;    // GetFileMUIPath returns target file with same name as source
    int MUI_MACHINE_LANGUAGE_SETTINGS = 0x400;
    int MUI_FILETYPE_NOT_LANGUAGE_NEUTRAL = 0x001;   // GetFileMUIInfo found a non-split resource file
    int MUI_FILETYPE_LANGUAGE_NEUTRAL_MAIN = 0x002;   // GetFileMUIInfo found a LN main module resource file
    int MUI_FILETYPE_LANGUAGE_NEUTRAL_MUI = 0x004;   // GetFileMUIInfo found a LN MUI module resource file
    int MUI_QUERY_TYPE = 0x001;   // GetFileMUIInfo will look for the type of the resource file
    int MUI_QUERY_CHECKSUM = 0x002;   // GetFileMUIInfo will look for the checksum of the resource file
    int MUI_QUERY_LANGUAGE_NAME = 0x004;   // GetFileMUIInfo will look for the culture of the resource file
    int MUI_QUERY_RESOURCE_TYPES = 0x008;   // GetFileMUIInfo will look for the resource types of the resource file
    int MUI_FILEINFO_VERSION = 0x001;   // Version of FILEMUIINFO structure used with GetFileMUIInfo

    int MUI_FULL_LANGUAGE = 0x01;
    int MUI_PARTIAL_LANGUAGE = 0x02;
    int MUI_LIP_LANGUAGE = 0x04;
    int MUI_LANGUAGE_INSTALLED = 0x20;
    int MUI_LANGUAGE_LICENSED = 0x40;

//
// MUI_CALLBACK_FLAG defines are duplicated in rtlmui.h
//

    //public static final int MUI_CALLBACK_ALL_FLAGS                        =MUI_CALLBACK_FLAG_UPGRADED_INSTALLATION; // OR all other flags when defined.

//
// MUI_CALLBACK_ flags are duplicated in rtlmui.h
//

//#endif

    public static class WCHAR extends IntegerType implements Comparable<WCHAR> {

        /** The Constant SIZE. */
        public static final int SIZE = Native.WCHAR_SIZE;

        /**
         * Instantiates a new wchar.
         */
        public WCHAR() {
            this(0);
        }

        public WCHAR(char ch) {
            this(ch & 0xFF);
        }

        /**
         * Instantiates a new wchar.
         *
         * @param value the value
         */
        public WCHAR(long value) {
            super(SIZE, value, true);
        }

        @Override
        public int compareTo(WCHAR other) {
            return compare(this, other);
        }
    }

    class CPINFOEXW extends Structure {
        /**
         * Contains information about a system appbar message.
         */

        public static class ByReference extends CPINFOEXW implements Structure.ByReference {
        }

        public static final List<String> FIELDS = createFieldsOrder("MaxCharSize", "DefaultChar", "LeadByte", "UnicodeDefaultChar", "CodePage", "CodePageName");

        public WinDef.UINT MaxCharSize;
        public WinDef.BYTE[] DefaultChar = new WinDef.BYTE[MAX_DEFAULTCHAR];
        public WinDef.BYTE[] LeadByte = new WinDef.BYTE[MAX_LEADBYTES];
        public WCHAR UnicodeDefaultChar;
        public WinDef.UINT CodePage;
        public WTypes.LPWSTR CodePageName;
//        public char[] CodePageName = new char[MAX_PATH];

        public CPINFOEXW() {
            super();
        }

        public CPINFOEXW(Pointer p) {
            super(p);
            read();
        }

        @Override
        protected List<String> getFieldOrder() {
            return FIELDS;
        }

    }

    class CPINFOEXA extends Structure {
        /**
         * Contains information about a system appbar message.
         */

        public static class ByReference extends CPINFOEXA implements Structure.ByReference {
        }

        public static final List<String> FIELDS = createFieldsOrder("MaxCharSize", "DefaultChar", "LeadByte", "UnicodeDefaultChar", "CodePage", "CodePageName");

        public WinDef.UINT MaxCharSize;
        public WinDef.BYTE[] DefaultChar = new WinDef.BYTE[MAX_DEFAULTCHAR];
        public WinDef.BYTE[] LeadByte = new WinDef.BYTE[MAX_LEADBYTES];
        public WCHAR UnicodeDefaultChar;
        public WinDef.UINT CodePage;
        public WTypes.LPSTR CodePageName;

        public CPINFOEXA() {
            super();
        }

        public CPINFOEXA(Pointer p) {
            super(p);
        }

        @Override
        protected List<String> getFieldOrder() {
            return FIELDS;
        }

    }

    class CPINFO extends Structure {
        /**
         * Contains information about a system appbar message.
         */

        public static class ByReference extends CPINFO implements Structure.ByReference {
        }

        public static final List<String> FIELDS = createFieldsOrder("MaxCharSize", "DefaultChar", "LeadByte");

        public WinDef.UINT MaxCharSize;
        public WinDef.BYTE[] DefaultChar = new WinDef.BYTE[MAX_DEFAULTCHAR];
        public WinDef.BYTE[] LeadByte = new WinDef.BYTE[MAX_LEADBYTES];

        public CPINFO() {
            super();
        }

        public CPINFO(Pointer p) {
            super(p);
        }

        @Override
        protected List<String> getFieldOrder() {
            return FIELDS;
        }

    }

    WinDef.BOOL GetCPInfoExA(
            WinDef.UINT CodePage,
            WinDef.DWORD Flags,
            CPINFOEXA lpCPInfoEx
    );

    WinDef.BOOL GetCPInfoExW(
            WinDef.UINT CodePage,
            WinDef.DWORD Flags,
            CPINFOEXW lpCPInfoEx
    );

    WinDef.BOOL GetCPInfo(
            WinDef.UINT CodePage,
            CPINFO lpCPInfo
    );
}
