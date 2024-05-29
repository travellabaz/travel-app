package az.travellab.ms_travel_application.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
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
              
              🪪 Pasportunuzu, vizanızı (əgər tələb olunursa) və digər lazım olan səyahət sənədlərinizi yanınızda saxlayın.
              
              
              🧳 Bağaj: Bağaj limitinizi təsdiqləyin və buna uyğun qablaşdırma edin. Çantalarınıza əlaqə məlumatlarınızı yazmağı unutmayın.
              
              
              Əgər hər hansı bir sualınız varsa və ya əlavə köməyə ehtiyacınız varsa, xahiş edirik bizimlə əlaqə saxlayın:\s
              
              Sizə xoş bir səyahət arzulayırıq!  🎫
              
              Hörmətlə,
              
              %s
              Tur menecer
              Travellab Group MMC
              %s
              travellab.az
            """),
    REMINDER_TRIP_DAY("""
            Hörmətli %s ☺️
              
              Ümid edirik ki, işləriniz qaydasındadır. Bu günki uçuşunuz haqqında sizə xatırlatma edir və Travellab komandası olaraq uğurlar arzu edirik  ✈️
              
              Uçuş Məlumatları:
              
              ⏰ Uçuş Vaxtı: %s
              
              Xahiş edirik, qeydiyyat və təhlükəsizlik prosedurları üçün uçuş vaxtından ən azı [tövsiyə edilən saatlar] saat əvvəl hava limanında olun.
              
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
              travellab.az
            """),
    REMINDER_RETURN_DAY("""
            Hörmətli %s,
                        
            Ümid edirik ki, işlıriniz qaydasındır. Sabah planlaşdırılmış uçuşunuz haqqında sizə xatırlatma edir və Travellab komandası olaraq uğurlar arzu edirik.
                        
            Uçuş Məlumatları:
                        
            Uçuş Vaxtı: %s
            Xahiş edirik, qeydiyyat və təhlükəsizlik prosedurları üçün uçuş vaxtından ən azı 3 saat əvvəl hava limanında olun.
                        
            Sizin rahatlığınız üçün əlavə məsləhətlər:
                        
            Səyahət Sənədləri: Pasportunuzu, vizanızı (əgər tələb olunursa) və digər lazım olan səyahət sənədlərinizi yanınızda saxlayın.
            Bağaj: Bağaj limitinizi təsdiqləyin və buna uyğun qablaşdırma edin. Çantalarınıza əlaqə məlumatlarınızı yazmağı unutmayın.
            Sağlamlıq və Təhlükəsizlik: Maska taxmaq və sosial məsafəni qorumaq kimi sağlamlıq və təhlükəsizlik qaydalarına əməl edin.
            Əgər hər hansı bir sualınız varsa və ya əlavə köməyə ehtiyacınız varsa, xahiş edirik bizimlə əlaqə saxlayın: %s.
                        
            Sizə xoş bir səyahət arzulayırıq!
                        
            Hörmətlə,
                        
            %s
            Tour Manager
            Travellab MMC
            %s
            """),
    INITIAL_PAYMENT_DAY("""
            Hörmətli %s,
                        
            Ümid edirik ki, işlıriniz qaydasındır. Sabah planlaşdırılmış uçuşunuz haqqında sizə xatırlatma edir və Travellab komandası olaraq uğurlar arzu edirik.
                        
            Uçuş Məlumatları:
                        
            Uçuş Vaxtı: %s
            Xahiş edirik, qeydiyyat və təhlükəsizlik prosedurları üçün uçuş vaxtından ən azı 3 saat əvvəl hava limanında olun.
                        
            Sizin rahatlığınız üçün əlavə məsləhətlər:
                        
            Səyahət Sənədləri: Pasportunuzu, vizanızı (əgər tələb olunursa) və digər lazım olan səyahət sənədlərinizi yanınızda saxlayın.
            Bağaj: Bağaj limitinizi təsdiqləyin və buna uyğun qablaşdırma edin. Çantalarınıza əlaqə məlumatlarınızı yazmağı unutmayın.
            Sağlamlıq və Təhlükəsizlik: Maska taxmaq və sosial məsafəni qorumaq kimi sağlamlıq və təhlükəsizlik qaydalarına əməl edin.
            Əgər hər hansı bir sualınız varsa və ya əlavə köməyə ehtiyacınız varsa, xahiş edirik bizimlə əlaqə saxlayın: %s.
                        
            Sizə xoş bir səyahət arzulayırıq!
                        
            Hörmətlə,
                        
            %s
            Tour Manager
            Travellab MMC
            %s
            """),
    PAYMENT_DAY("""
            Hörmətli %s,
                        
            Ümid edirik ki, işlıriniz qaydasındır. Sabah planlaşdırılmış uçuşunuz haqqında sizə xatırlatma edir və Travellab komandası olaraq uğurlar arzu edirik.
                        
            Uçuş Məlumatları:
                        
            Uçuş Vaxtı: %s
            Xahiş edirik, qeydiyyat və təhlükəsizlik prosedurları üçün uçuş vaxtından ən azı 3 saat əvvəl hava limanında olun.
                        
            Sizin rahatlığınız üçün əlavə məsləhətlər:
                        
            Səyahət Sənədləri: Pasportunuzu, vizanızı (əgər tələb olunursa) və digər lazım olan səyahət sənədlərinizi yanınızda saxlayın.
            Bağaj: Bağaj limitinizi təsdiqləyin və buna uyğun qablaşdırma edin. Çantalarınıza əlaqə məlumatlarınızı yazmağı unutmayın.
            Sağlamlıq və Təhlükəsizlik: Maska taxmaq və sosial məsafəni qorumaq kimi sağlamlıq və təhlükəsizlik qaydalarına əməl edin.
            Əgər hər hansı bir sualınız varsa və ya əlavə köməyə ehtiyacınız varsa, xahiş edirik bizimlə əlaqə saxlayın: %s.
                        
            Sizə xoş bir səyahət arzulayırıq!
                        
            Hörmətlə,
                        
            %s
            Tour Manager
            Travellab MMC
            %s
            """),
    BIRTH_DAY("""
            Hörmətli %s,
                        
            Ümid edirik ki, işlıriniz qaydasındır. Sabah planlaşdırılmış uçuşunuz haqqında sizə xatırlatma edir və Travellab komandası olaraq uğurlar arzu edirik.
                        
            Uçuş Məlumatları:
                        
            Uçuş Vaxtı: %s
            Xahiş edirik, qeydiyyat və təhlükəsizlik prosedurları üçün uçuş vaxtından ən azı 3 saat əvvəl hava limanında olun.
                        
            Sizin rahatlığınız üçün əlavə məsləhətlər:
                        
            Səyahət Sənədləri: Pasportunuzu, vizanızı (əgər tələb olunursa) və digər lazım olan səyahət sənədlərinizi yanınızda saxlayın.
            Bağaj: Bağaj limitinizi təsdiqləyin və buna uyğun qablaşdırma edin. Çantalarınıza əlaqə məlumatlarınızı yazmağı unutmayın.
            Sağlamlıq və Təhlükəsizlik: Maska taxmaq və sosial məsafəni qorumaq kimi sağlamlıq və təhlükəsizlik qaydalarına əməl edin.
            Əgər hər hansı bir sualınız varsa və ya əlavə köməyə ehtiyacınız varsa, xahiş edirik bizimlə əlaqə saxlayın: %s.
                        
            Sizə xoş bir səyahət arzulayırıq!
                        
            Hörmətlə,
                        
            %s
            Tour Manager
            Travellab MMC
            %s
            """);

    String message;
}
