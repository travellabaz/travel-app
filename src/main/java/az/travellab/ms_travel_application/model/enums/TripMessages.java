package az.travellab.ms_travel_application.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor
public enum TripMessages {
    REMINDER_TRIP_ONE_DAY_BEFORE("""
            Hörmətli %s ☺️
                        
            Ümid edirik ki, işlıriniz qaydasındır. Sabah planlaşdırılmış uçuşunuz haqqında sizə xatırlatma edir və Travellab komandası olaraq uğurlar arzu edirik  ✈️
                        
            Uçuş Məlumatları 📜:
                        
                        
            ⏰ Uçuş Vaxtı: %s
            ⏰ Qayıtmaq Vaxtı: %s
                        
            Xahiş edirik, qeydiyyat və təhlükəsizlik prosedurları üçün uçuş vaxtından ən azı 3 saat əvvəl hava limanında olun.
                        
            Sizin rahatlığınız üçün əlavə məsləhətlər 🗒️:
                        
            Səyahət Sənədləri:\s
                        
            🪪 Pasportunuzu və digər lazım olan səyahət sənədlərinizi yanınızda saxlayın.
                        
                        
            🧳 Bağaj: Bağaj limitinizi təsdiqləyin və buna uyğun qablaşdırma edin. Çantalarınıza əlaqə məlumatlarınızı yazmağı unutmayın.
                        
                        
            Əgər hər hansı bir sualınız varsa və ya əlavə köməyə ehtiyacınız varsa, xahiş edirik bizimlə əlaqə saxlayın:\s
                        
            Sizə xoş bir səyahət arzulayırıq!  🎫
                        
            Hörmətlə,
                        
            %s
            Tur menecer
            Travellab Group MMC
            %s
            """),
    REMINDER_TRIP_DAY("""
            Hörmətli %s ☺️
                        
            Ümid edirik ki, işləriniz qaydasındadır. Bu günki uçuşunuz haqqında sizə xatırlatma edir və Travellab komandası olaraq uğurlar arzu edirik  ✈️
                        
            Uçuş Məlumatları:
                        
            ⏰ Uçuş Vaxtı: %s
            ⏰ Qayıtmaq Vaxtı: %s
                        
            Xahiş edirik, qeydiyyat və təhlükəsizlik prosedurları üçün uçuş vaxtından ən azı 3 saat əvvəl hava limanında olun.
                        
            Sizin rahatlığınız üçün əlavə məsləhətlər:
                        
            Səyahət Sənədləri:\s
                        
            🪪 Pasportunuzu və digər lazım olan səyahət sənədlərinizi yanınızda saxlayın.
                        
                        
            🧳 Bağaj: Bağaj limitinizi təsdiqləyin və buna uyğun qablaşdırma edin. Çantalarınıza əlaqə məlumatlarınızı yazmağı unutmayın.
                        
                        
            Əgər hər hansı bir sualınız varsa və ya əlavə köməyə ehtiyacınız varsa, xahiş edirik bizimlə əlaqə saxlayın.
                        
            Sizə xoş bir səyahət arzulayırıq! 🎫
                        
            Hörmətlə,
                        
            %s
            Tur menecer
            Travellab Group MMC
            %s
            """),
    REMINDER_RETURN_DAY("""
            Hörmətli %s ☺️
                        
            Ümid edirik ki, səyahətinizdən zövq aldınız. Sizə qayıdış uçuşunuz haqqında xatırlatma etmək və Travellab komandası olaraq sizə xoş bir geri dönüş arzulamaq istəyirik ✈️
                        
            Qayıdış Uçuş Məlumatları:
                        
            ⏰Uçuş Vaxtı: %s
                        
            Zəhmət olmasa, qeydiyyat və təhlükəsizlik prosedurları üçün uçuş vaxtından ən azı 3 saat əvvəl hava limanında olun.
                        
            Qayıdışınızın rahat keçməsi üçün bəzi tövsiyələr:
                        
            🪪 Səyahət Sənədləri: Pasportunuzu və digər lazım olan sənədlərinizi hazır saxlayın.
            🧳 Bağaj: Bağaj limitinizi yoxlayın və çantalarınızı təhlükəsiz və rahat şəkildə qablaşdırın.
                        
                        
            Əgər hər hansı bir sualınız varsa və ya əlavə köməyə ehtiyacınız varsa, bizimlə əlaqə saxlayın.
            Sizə xoş bir qayıdış arzulayırıq! 🎫
                        
            Hörmətlə,
                        
            %s
            Tur menecer
            Travellab Group MMC
            %s
            """),
    INITIAL_PAYMENT_DAY("""
            Hörmətli %s ☺️
                        
            Salam! Sizə bu mesajı göndəririk ki, ilkin ödəniş vaxtının çatdığını xatırladaq. Xahiş edirik, ümumi məbləğin 30 faizini ən qısa zamanda ödəyəsiniz. ✅
                        
            Sizin etibarınız bizim üçün çox vacibdir və sizinlə işləməkdən məmnuniyyət hissi duyuruq 😇
                        
            Əgər əlavə suallarınız və ya tələbləriniz varsa, zəhmət olmasa bizimlə əlaqə saxlayın. Sizə kömək etməkdən məmnun olarıq. ❓
                        
            Sizə uğurlar və xoş günlər arzulayırıq! 🎫
                        
            Hörmətlə,
                        
            %s
            Tur menecer
            Travellab Group MMC
            %s
            """),
    PAYMENT_DAY("""
            Sevgili %s ☺️
                        
            Öncəliklə, bizə olan etibarınız üçün minnətdarıq 😇
                        
            Xatırlatmaq istərdik ki, ümumi məbləğin 70 faizini təşkil edən qalıq ödənişiniz hələ tam ödənilməyib. Xahiş edirik, bu ödənişi ən qısa zamanda həyata keçirəsiniz. ✅
                        
            Lütfən, əlavə suallarınız olarsa, bizə çəkinmədən müraciət edin. Sizə kömək etməyimiz üçün buradayıq.
                        
            Sizə xoş günlər arzulayırıq 🎫
                        
            Hörmətlə,
                        
            %s
            Tur menecer
            Travellab Group MMC
            %s
            """),
    BIRTH_DAY("""
            Sevgili %s ☺️
            
            Ad gününüz mübarək olsun 🥳
                        
            Yeni yaşınız sizə səyahət etdiyiniz yerlərdəki qədər maraqlı və macəra dolu anlar gətirsin.\s
                        
            Həyatınızda yeni kəşflər və sevinclər bol olsun!
                        
            Xoş arzularla,
            Travellab Group MMC
            """);

   private final String message;
}
